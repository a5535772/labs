//package org.springframework.ai.chat.client.advisor;
//
//import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
//import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
//import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
//import org.springframework.ai.chat.messages.AssistantMessage;
//import org.springframework.ai.chat.model.ChatResponse;
//import org.springframework.ai.chat.model.Generation;
//
//public class LeoKeyWordChangeAfterAdvisor<T> extends LeoKeyWordEncoderAdvisor {
//    protected final T chatMemoryStore;
//
//    public LeoKeyWordChangeAfterAdvisor(T chatMemoryStore) {
//        this(true, 0, chatMemoryStore);
//    }
//
//    public LeoKeyWordChangeAfterAdvisor(boolean protectFromBlocking, int order, T chatMemoryStore) {
//        super(protectFromBlocking, order);
//        this.chatMemoryStore = chatMemoryStore;
//    }
//
//
//    @Override
//    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
//        advisedRequest = this.before(advisedRequest);
//        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
//        return this.after(advisedResponse);
//    }
//
//    protected AdvisedRequest before(AdvisedRequest request) {
//        return request;
//    }
//
//    protected AdvisedResponse after(AdvisedResponse advisedResponse) {
//        chatMemoryStore.
//        List<Generation> generations = advisedResponse.response().getResults().stream().map(
//                generation -> {
//                    if (generation.getOutput() != null && generation.getOutput().getText().contains("张学友")) {
//                        AssistantMessage old = generation.getOutput();
//                        var newText = generation.getOutput().getText().replace("张学友", "刘德华");
//                        var newAssistantMessage = new AssistantMessage(newText, old.getReasonerContent(), old.getMetadata(), old.getToolCalls(), old.getMedia());
//                        return new Generation(newAssistantMessage, generation.getMetadata());
//                    }
//                    return generation;
//                }
//        ).toList();
//        //替换的返回的文本
//        ChatResponse.Builder chatResponseBuilder = ChatResponse.builder().from(advisedResponse.response()).generations(generations);
//        return new AdvisedResponse(chatResponseBuilder.build(), advisedResponse.adviseContext());
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
//        public LeoKeyWordChangeAfterAdvisor build() {
//            return new LeoKeyWordChangeAfterAdvisor(null);
//        }
//    }
//}
