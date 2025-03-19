//package org.springframework.ai.chat.client.advisor;
//
//import org.springframework.ai.chat.client.advisor.api.*;
//import org.springframework.util.StringUtils;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import reactor.core.scheduler.Schedulers;
//
//import java.util.Map;
//import java.util.function.Predicate;
//
//public abstract class LeoKeyWordEncoderAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {
//
//    protected LeoKeyWordEncoderAdvisor(boolean protectFromBlocking, int order) {
//        this.protectFromBlocking = protectFromBlocking;
//        this.order = order;
//    }
//
//    protected String doBeforeWordChange(String orgUserText) {
//        if (orgUserText != null) {
//            return orgUserText.replace("刘德华", "张学友");
//        }
//        return orgUserText;
//    }
//
//    protected String doGetConversationId(Map<String, Object> context) {
//        return context.containsKey("chat_memory_conversation_id") ? context.get("chat_memory_conversation_id").toString() : "";
//    }
//
//    protected abstract AdvisedRequest before(AdvisedRequest request) ;
//
//    protected abstract AdvisedResponse after(AdvisedResponse advisedResponse) ;
//
//
//}
