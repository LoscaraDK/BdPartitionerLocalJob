package br.com.estudo.contasbancariasjob.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class DatasourcePropertiesConfig {
	
	@Bean
	public PropertySourcesPlaceholderConfigurer datasourceProperties() {
		PropertySourcesPlaceholderConfigurer datasourceProperties = new PropertySourcesPlaceholderConfigurer();
		datasourceProperties.setLocation(new FileSystemResource("/temp/config/application.properties"));
		return datasourceProperties;
	}
	
}
