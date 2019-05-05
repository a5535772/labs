package com.leo.labs.start.jar.demo.uid.properties.auto.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leo.labs.start.jar.demo.uid.api.UidGenerator;
import com.leo.labs.start.jar.demo.uid.impl.DefaultUidGenerator;
import com.leo.labs.start.jar.demo.uid.properties.StartUidProperties;

/**
 * 
 * @author LeoZhangli
 *
 */
@Configuration
@ConditionalOnClass(UidGenerator.class)
@EnableConfigurationProperties(StartUidProperties.class)
@ConditionalOnProperty(prefix = "startuid", value = "enabled", matchIfMissing = true)
public class StartUidStaterAutoConfigure {

	@Autowired
	private StartUidProperties startUidProperties;

	@Bean
	@ConditionalOnMissingBean
	public UidGenerator uidGenerator() {
		return new DefaultUidGenerator(startUidProperties.getServiceId());
	}

}
