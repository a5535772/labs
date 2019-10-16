package com.leo.labs.oauth2.core.social.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import com.leo.labs.oauth2.core.properties.SecurityProperties;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SecurityProperties securityProperties;

	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
		/** 建表语句详见 JdbcUsersConnectionRepository 统计目录的SQL */
		repository.setTablePrefix("lab_");// 设置表前缀
		if (connectionSignUp != null) {//自动注册
			repository.setConnectionSignUp(connectionSignUp);
		}
		return repository;
	}

	@Bean
	public SpringSocialConfigurer labsSocialConfigurer() {
		/***
		 * return new SpringSocialConfigurer();
		 * <p/>
		 * spring 默认拦截的访问地址是 /auth/qq
		 * <p/>
		 * /auth @see org.springframework.social.security.SocialAuthenticationFilter
		 * <p/>
		 * /qq @see appid
		 * 
		 */
		log.info(securityProperties.getSocial().toString());
		// 使用自定义config代替spring的。
		return new LabsSpringSocialConfigurer(securityProperties.getSocial().getFilterProcessUrl());
	}

	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator,
			UsersConnectionRepository usersConnectionRepository) {
		return new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
	}

}
