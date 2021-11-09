package es.uah.portalfacturasms.infrastructure.service;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import es.uah.portalfacturasms.infrastructure.model.User;

@Service
public class UserService {

	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	
	private HashOperations<String, String, User> hashOperations;
	
	private final String USUARIO_CACHE = "USUARIO";
	
	
	@PostConstruct
    private void intializeHashOperations() {
       hashOperations = redisTemplate.opsForHash();
        
    }
	
	public User obtieneUsuarioDeUsername(final String username) {
    	logger.info("Entramos en el metodo obtieneUsuarioDeUsername() a recuperar el usuario con nombre={} del redis",username);
    	if( StringUtils.isBlank(username) ) {
    		logger.error("Error! el username esta vacio y no se puede recuperar el usuario");
    		return null;
    	}
    	User usuario = (User) hashOperations.get(USUARIO_CACHE, username);
    	return usuario;
    }
	
	public User obtieneUsuario(final String username, final String password) {
		logger.info("Entramos en el metodo obtieneUsuario() con username={}",username);
		if( StringUtils.isBlank(username) || StringUtils.isBlank(password)  ) {
			return null;
		}
		User usuario = new User(username,password);
		return usuario;
	}
    
    public User guardarUsuarioEnSesion(final String username, final String password) {
    	logger.info("Entramos en el metodo guardarUsuarioEnSesion() a guardar el usuario en la sesion del redis");
    	if( StringUtils.isBlank(username) || StringUtils.isBlank(password)  ) {
    		logger.error("Error! el usuario es nulo y no se puede guardar en el redis");
    		return null;
    	}
    	User _user = new User(username, password);
    	hashOperations.put(USUARIO_CACHE, username, _user);
    	return _user;
    }
 
    
	
	
}
