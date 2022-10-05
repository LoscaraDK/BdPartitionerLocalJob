package com.springbatch.remotechunkingjob.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {
	@Value("${broker.url}")
	private String brokerUrl;
	@Value("${broker.userName}")
	private String userName;
	@Value("${broker.password}")
	private String password;

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(this.brokerUrl);
		connectionFactory.setUserName(this.userName);
		connectionFactory.setPassword(this.password);
		connectionFactory.setTrustAllPackages(true); //para evitar problemas de serialização
		return connectionFactory;
	}
}
