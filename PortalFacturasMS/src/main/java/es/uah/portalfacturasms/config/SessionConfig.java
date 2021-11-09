package es.uah.portalfacturasms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import es.uah.portalfacturasms.infrastructure.model.User;

@Configuration
public class SessionConfig {
	
	
	@Bean
	public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory connectionFactory) {
	    RedisTemplate<String, User> template = new RedisTemplate<String, User>();
	    template.setConnectionFactory(connectionFactory);
	    return template;
	}
	
}
