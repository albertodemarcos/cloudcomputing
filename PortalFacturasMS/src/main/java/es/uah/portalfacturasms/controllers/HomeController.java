package es.uah.portalfacturasms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.uah.portalfacturasms.infrastructure.service.UserService;
import es.uah.portalfacturasms.infrastructure.utils.ResponseMessage;

@RestController
public class HomeController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	
	
	@GetMapping(value="/usuario")
	public @ResponseBody ResponseMessage getSesion(@RequestParam(value="username", required = false) String username) {
		logger.info("Entramos en el controlador getSaveSession() username={}",username);
		ResponseMessage _response = this.userService.obtieneResponseMessageUsername(username);
		return _response;
	}
	
	@GetMapping(value="/sesion")
	public @ResponseBody ResponseMessage getSaveSession(
			@RequestParam(value="username", required = false) String username,
			@RequestParam(value="password", required = false) String password) {
		logger.info("Entramos en el controlador getSaveSession() username={}",username);
		ResponseMessage _response = this.userService.obtieneResponseMessageDeGuardarUsuarioEnSesion(username, password);
		return _response;
	}
	
	
}
