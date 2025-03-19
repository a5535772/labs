package org.springframework.ai.chat.client.advisor;


import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisorChain;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.MessageAggregator;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class CustomMessageChatMemoryAdvisor extends AbstractChatMemoryAdvisor<ChatMemory> {

    public CustomMessageChatMemoryAdvisor(ChatMemory chatMemory) {
        super(chatMemory);
    }

    public CustomMessageChatMemoryAdvisor(ChatMemory chatMemory, String defaultConversationId, int chatHistoryWindowSize) {
        this(chatMemory, defaultConversationId, chatHistoryWindowSize, -2147482648);
    }

    public CustomMessageChatMemoryAdvisor(ChatMemory chatMemory, String defaultConversationId, int chatHistoryWindowSize, int order) {
        super(chatMemory, defaultConversationId, chatHistoryWindowSize, true, order);
    }

    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        advisedRequest = this.before(advisedRequest);
        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
        this.observeAfter(advisedResponse);
        return advisedResponse;
    }

    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        Flux<AdvisedResponse> advisedResponses = this.doNextWithProtectFromBlockingBefore(advisedRequest, chain, this::before);
        return (new MessageAggregator()).aggregateAdvisedResponse(advisedResponses, this::observeAfter);
    }

    private AdvisedRequest before(AdvisedRequest request) {
        String conversationId = this.doGetConversationId(request.adviseContext());
        int chatMemoryRetrieveSize = this.doGetChatMemoryRetrieveSize(request.adviseContext());
        List<Message> memoryMessages = ((ChatMemory) this.getChatMemoryStore()).get(conversationId, chatMemoryRetrieveSize);
        List<Message> advisedMessages = new ArrayList(request.messages());
        advisedMessages.addAll(memoryMessages);
        AdvisedRequest advisedRequest = AdvisedRequest.from(request).messages(advisedMessages).build();
        UserMessage userMessage = new UserMessage(request.userText(), request.media());
        ((ChatMemory) this.getChatMemoryStore()).add(this.doGetConversationId(request.adviseContext()), userMessage);
        return advisedRequest;
    }

    private void observeAfter(AdvisedResponse advisedResponse) {
        List<Message> messageList;
        ChatResponse chatResponse = advisedResponse.response();
        String decryptText = chatResponse.getMetadata().get(CustomWordEncryptionAdvisor.decryptText);
        if (StringUtils.hasText(decryptText)) {
            AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
            assistantMessage = new AssistantMessage(decryptText, assistantMessage.getReasonerContent(), assistantMessage.getMetadata(), assistantMessage.getToolCalls(), assistantMessage.getMedia());
            messageList = List.of(assistantMessage);
        } else {
            messageList = advisedResponse.response().getResults().stream().map((g) -> {
                return (Message) g.getOutput();
            }).toList();
        }
        ((ChatMemory) this.getChatMemoryStore()).add(this.doGetConversationId(advisedResponse.adviseContext()), messageList);
    }

//    private AdvisedResponse observeAfter(AdvisedResponse advisedResponse) {
//        List<Message> messageList;
//        if (this.afterWordChange) {
//            messageList = advisedResponse.response().getResults().stream().map((g) -> {
//                return g.getOutput();
//            }).toList().stream().map(
//                    oldMsg -> {
//                        var newText = this.keyWordChangeUtil.doAfterWordChange(oldMsg.getText());
//                        return (Message) new AssistantMessage(newText, oldMsg.getReasonerContent(), oldMsg.getMetadata(), oldMsg.getToolCalls(), oldMsg.getMedia());
//                    }
//            ).toList();
//        } else {
//            messageList = advisedResponse.response().getResults().stream().map((g) -> {
//                return (Message) g.getOutput();
//            }).toList();
//        }
//        ((ChatMemory) this.getChatMemoryStore()).add(this.doGetConversationId(advisedResponse.adviseContext()), messageList);
//
//        ChatResponse.Builder chatResponseBuilder = ChatResponse.builder().from(advisedResponse.response());
//        chatResponseBuilder.metadata("store_assistantMessage", messageList);
//        return new AdvisedResponse(chatResponseBuilder.build(), advisedResponse.adviseContext());
//    }

}