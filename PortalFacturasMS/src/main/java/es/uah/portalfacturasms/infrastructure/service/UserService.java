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
import es.uah.portalfacturasms.infrastructure.utils.ResponseMessage;

@Service
public class UserService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	
	private HashOperations<String, String, User> hashOperations;
	
	private final String USUARIO_CACHE = "USUARIO";
	
	
	@PostConstruct
    private void intializeHashOperations() {
       this.hashOperations = redisTemplate.opsForHash();
        
    }
	
	public ResponseMessage obtieneResponseMessageUsername(final String username)
	{
		logger.info("Recuperamos el usuario={} en el metodo obtieneResponseMessageUsername",username);
		User _user = this.obtieneUsuarioDeUsername(username);
		if( _user == null ) 
		{
			logger.error("No se ha podido recuperar la sesion del usuario={}",username);
			return new ResponseMessage ("-1", "No se ha podido recuperar la sesion con el usuario="+username+"");
		}
		return new ResponseMessage("1", "El usuario ["+username+"] se ha recuperado de la sesion", _user);
	}
	
	public ResponseMessage obtieneResponseMessageDeGuardarUsuarioEnSesion(final String username, final String password) 
	{
		logger.info("Recuperamos el usuario={} en el metodo obtieneResponseMessageUsername",username);
		User _user = this.guardarUsuarioEnSesion(username, password);
		if(_user == null) 
		{
			logger.error("No se ha podido crear la sesion con el usuario={}",username);
			return new ResponseMessage ("-1", "No se ha podido crear la sesion con el usuario="+username+"");
		}
		return new ResponseMessage ("1", "El usuario ["+username+"] se ha guardado en sesion", _user);
	}
	
	private User obtieneUsuarioDeUsername(final String username) 
	{
    	logger.info("Entramos en el metodo obtieneUsuarioDeUsername() a recuperar el usuario con nombre={} del redis",username);
    	if( StringUtils.isBlank(username) ) {
    		logger.error("Error! el username esta vacio y no se puede recuperar el usuario");
    		return null;
    	}
    	User usuario = (User) this.hashOperations.get(this.USUARIO_CACHE, username);
    	return usuario;
    }
    
    private User guardarUsuarioEnSesion(final String username, final String password)
    {
    	logger.info("Entramos en el metodo guardarUsuarioEnSesion() a guardar el usuario en la sesion del redis");
    	if( StringUtils.isBlank(username) || StringUtils.isBlank(password)  ) {
    		logger.error("Error! el usuario es nulo y no se puede guardar en el redis");
    		return null;
    	}
    	User _user = new User(username, password);
    	this.hashOperations.put(this.USUARIO_CACHE, username, _user);
    	return _user;
    }
	
}
