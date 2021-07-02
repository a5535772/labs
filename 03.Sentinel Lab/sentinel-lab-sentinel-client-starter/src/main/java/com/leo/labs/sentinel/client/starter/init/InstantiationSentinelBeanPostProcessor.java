//package com.leo.labs.sentinel.client.starter.init;
//
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//
//import com.alibaba.csp.sentinel.Env;
//
///**
// * 注意，把这个类放加入到spring中，可以保障sentinel云端策略加载顺序高于本地
// * <p/>
// * 
// * @see https://blog.csdn.net/wenmingfeng1215/article/details/80405371
// * @author leo
// *
// */
//public class InstantiationSentinelBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent event) {
//		if (event.getApplicationContext().getParent() == null) {// root application context 没有parent，他就是老大.
//			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
//			// 执行Env静态代码块
//			Object o = Env.sph;
//		}
//	}
//}
