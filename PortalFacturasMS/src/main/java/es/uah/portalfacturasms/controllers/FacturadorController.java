package es.uah.portalfacturasms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.uah.portalfacturasms.infrastructure.model.FacturaDto;
import es.uah.portalfacturasms.infrastructure.service.FacturaService;
import es.uah.portalfacturasms.infrastructure.utils.ResponseMessage;

@RestController
public class FacturadorController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FacturaService facturaService;
	
	
	@PostMapping(value="/factura")
	public @ResponseBody ResponseMessage postFactura(
			@RequestParam(value="username", required = false) final String username,
			@RequestHeader(value="X-tipo") String xHeaderHttp,
			@RequestBody final FacturaDto facturaDto,
			BindingResult result) 
	{
		logger.info("Entramos en el controlador postFactura() username={}",username);
		ResponseMessage _response = this.facturaService.persisteFacturaDeUsuario(facturaDto, username, xHeaderHttp, result);
		return _response;
	}
	
}
