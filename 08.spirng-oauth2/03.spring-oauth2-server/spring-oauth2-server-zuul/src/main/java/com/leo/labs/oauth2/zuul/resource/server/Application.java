package com.leo.labs.oauth2.zuul.resource.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
/** @see https://www.e-learn.cn/content/wangluowenzhang/270295 */
@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class })

@EnableZuulProxy
public class Application {
	public static void main(String[] args) {
//		String[] appArgs = { "--debug" };
//		SpringApplication app = new SpringApplication(Application.class);
//		app.setBannerMode(Banner.Mode.OFF);
//		app.setLogStartupInfo(false);
//		app.run(appArgs);
		SpringApplication.run(Application.class, args);
	}

}
