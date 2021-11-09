package es.uah.facturasvalidadorms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration 
public class WebConfig implements WebMvcConfigurer {

	
	@Bean
	public RestTemplate template() {
		RestTemplate template = new RestTemplate();
		return template;
	}
	

	

}
