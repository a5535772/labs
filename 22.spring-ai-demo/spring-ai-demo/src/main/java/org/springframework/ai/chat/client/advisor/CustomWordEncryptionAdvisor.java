package org.springframework.ai.chat.client.advisor;

import com.leo.labs.springaidemo.demos.statics.KeyWordEncryptionUtil;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomWordEncryptionAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {
    public static final String decryptText="decrypt_text";
    private final int order;
    private final boolean protectFromBlocking = true;
    private final KeyWordEncryptionUtil keyWordChangeUtil = new KeyWordEncryptionUtil();

    public CustomWordEncryptionAdvisor() {
        this.order = 0;
    }

    public CustomWordEncryptionAdvisor(int order) {
        this.order = order;
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        AdvisedRequest otherRequest = this.before(advisedRequest);
        AdvisedResponse advisedResponse = chain.nextAroundCall(otherRequest);

        AtomicReference<List<AssistantMessage>> contextList = new AtomicReference<>(new ArrayList<>());
        contextList.get().add(advisedResponse.response().getResult().getOutput());

        return this.after(advisedResponse, contextList);
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        Flux<AdvisedResponse> advisedResponses = this.doNextWithProtectFromBlockingBefore(advisedRequest, chain, this::before);
        // 创建一个可变的列表来存储所有 context
        AtomicReference<List<AssistantMessage>> contextList = new AtomicReference<>(new ArrayList<>());

        // 使用 doOnNext 收集每个 AdvisedResponse 的 context
        Flux<AdvisedResponse> processedFlux = advisedResponses
                .doOnNext(ar -> {
                    // 收集每个 response 的 context
                    contextList.get().add(ar.response().getResult().getOutput());
                }).map((ar) -> {
                    if (this.onFinishReason().test(ar)) {
                        ar = this.after(ar,contextList);
                    }
                    return ar;
                });
        return processedFlux;
    }

    private AdvisedRequest before(AdvisedRequest request) {
        String changedUserText = this.keyWordChangeUtil.doBeforeWordChange(request.userText());
        List<Message> newMessages = request.messages().stream().map(message -> {
            if (MessageType.USER.equals(message.getMessageType())) {
                return new UserMessage(this.keyWordChangeUtil.doBeforeWordChange(message.getText()), ((UserMessage) message).getMedia(), ((UserMessage) message).getMetadata());
            }
            if (MessageType.SYSTEM.equals(message.getMessageType())) {
                return new SystemMessage(this.keyWordChangeUtil.doBeforeWordChange(message.getText()));
            }
            return message;
        }).toList();
        return AdvisedRequest.from(request).userText(changedUserText).messages(newMessages).build();
    }

    private AdvisedResponse after(AdvisedResponse advisedResponse, AtomicReference<List<AssistantMessage>> history) {
        if (history != null) {
            var a=history.get();
            //使用stream进行循环，并将 每个 AssistantMessage 的 text 进行拼接
            String concatenatedText = a.stream().map(AssistantMessage::getText).collect(Collectors.joining());
            //构建返回参数
            ChatResponse.Builder chatResponseBuilder = ChatResponse.builder().from(advisedResponse.response());
            chatResponseBuilder.metadata("decrypt_text", keyWordChangeUtil.doAfterWordChange(concatenatedText));
            return new AdvisedResponse(chatResponseBuilder.build(), advisedResponse.adviseContext());
        }
        return advisedResponse;
    }

    protected Flux<AdvisedResponse> doNextWithProtectFromBlockingBefore(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain, Function<AdvisedRequest, AdvisedRequest> beforeAdvise) {
        return this.protectFromBlocking ? Mono.just(advisedRequest).publishOn(Schedulers.boundedElastic()).map(beforeAdvise).flatMapMany((request) -> {
            return chain.nextAroundStream(request);
        }) : chain.nextAroundStream((AdvisedRequest) beforeAdvise.apply(advisedRequest));
    }

    private Predicate<AdvisedResponse> onFinishReason() {
        return (advisedResponse) -> {
            return advisedResponse.response().getResults().stream().filter((result) -> {
                return result != null && result.getMetadata() != null && StringUtils.hasText(result.getMetadata().getFinishReason());
            }).findFirst().isPresent();
        };
    }


    public String getName() {
        return this.getClass().getSimpleName();
    }

    public int getOrder() {
        return this.order;
    }

}
