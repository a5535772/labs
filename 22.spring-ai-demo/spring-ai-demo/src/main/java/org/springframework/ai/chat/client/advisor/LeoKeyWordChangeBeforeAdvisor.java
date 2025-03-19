//package org.springframework.ai.chat.client.advisor;
//
//import com.leo.labs.springaidemo.demos.statics.KeyWordEncryptionUtil;
//import org.springframework.ai.chat.client.advisor.api.*;
//import org.springframework.ai.chat.messages.Message;
//import org.springframework.ai.chat.messages.MessageType;
//import org.springframework.ai.chat.messages.SystemMessage;
//import org.springframework.ai.chat.messages.UserMessage;
//import org.springframework.util.StringUtils;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import reactor.core.scheduler.Schedulers;
//
//import java.util.List;
//import java.util.function.Predicate;
//
//public class LeoKeyWordChangeBeforeAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {
//    private final boolean protectFromBlocking;
//    private final int order;
//    private final KeyWordEncryptionUtil keyWordChangeUtil;
//
//    public LeoKeyWordChangeBeforeAdvisor() {
//        this(true, 0, new KeyWordEncryptionUtil());
//    }
//
//    public LeoKeyWordChangeBeforeAdvisor(KeyWordEncryptionUtil keyWordChangeUtil) {
//        this(true, 0, keyWordChangeUtil);
//    }
//
//    public LeoKeyWordChangeBeforeAdvisor(boolean protectFromBlocking, int order, KeyWordEncryptionUtil keyWordChangeUtil) {
//        this.protectFromBlocking = protectFromBlocking;
//        this.order = order;
//        this.keyWordChangeUtil = keyWordChangeUtil;
//    }
//
//
//    @Override
//    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
//        AdvisedRequest otherRequest = this.before(advisedRequest);
//        AdvisedResponse advisedResponse = chain.nextAroundCall(otherRequest);
//        return this.after(advisedResponse);
//    }
//
//    protected AdvisedRequest before(AdvisedRequest request) {
//        String changedUserText = this.keyWordChangeUtil.doBeforeWordChange(request.userText());
//        List<Message> newMessages = request.messages().stream().map(message -> {
//            if (MessageType.USER.equals(message.getMessageType())) {
//                return new UserMessage(this.keyWordChangeUtil.doBeforeWordChange(message.getText()), ((UserMessage) message).getMedia(), ((UserMessage) message).getMetadata());
//            }
//            if (MessageType.SYSTEM.equals(message.getMessageType())) {
//                return new SystemMessage(this.keyWordChangeUtil.doBeforeWordChange(message.getText()));
//            }
//            return message;
//        }).toList();
//        return AdvisedRequest.from(request).userText(changedUserText).messages(newMessages).build();
//    }
//
//    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
//        Flux<AdvisedResponse> advisedResponses = this.protectFromBlocking ? Mono.just(advisedRequest).publishOn(Schedulers.boundedElastic()).map(this::before).flatMapMany((request) -> {
//            return chain.nextAroundStream(request);
//        }) : chain.nextAroundStream(this.before(advisedRequest));
//        return advisedResponses.map((ar) -> {
//            if (this.onFinishReason().test(ar)) {
//                ar = this.after(ar);
//            }
//            return ar;
//        });
//    }
//
//    private Predicate<AdvisedResponse> onFinishReason() {
//        return (advisedResponse) -> {
//            return advisedResponse.response().getResults().stream().filter((result) -> {
//                return result != null && result.getMetadata() != null && StringUtils.hasText(result.getMetadata().getFinishReason());
//            }).findFirst().isPresent();
//        };
//    }
//
//    /**
//     * do nothing
//     *
//     * @param advisedResponse
//     * @return
//     */
//    protected AdvisedResponse after(AdvisedResponse advisedResponse) {
//        return advisedResponse;
//    }
//
//
//    public String getName() {
//        return this.getClass().getSimpleName();
//    }
//
//    public int getOrder() {
//        return this.order;
//    }
//
//    public static final class Builder {
//        public LeoKeyWordChangeBeforeAdvisor build() {
//            return new LeoKeyWordChangeBeforeAdvisor();
//        }
//    }
//}
