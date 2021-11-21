package es.uah.facturasvalidadorms.controllers;

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

import es.uah.facturasvalidadorms.infraestructure.model.FacturaDto;
import es.uah.facturasvalidadorms.infraestructure.service.FacturaService;
import es.uah.facturasvalidadorms.infraestructure.utils.ResponseMessage;


@RestController
public class FacturadorController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FacturaService facturaService;
	
	
	@PostMapping(value="/factura")
	public @ResponseBody ResponseMessage postFacturaValidada(
			@RequestParam(value="username", required = false) final String username,
			@RequestHeader(value="X-tipo") String xHeaderHttp,
			@RequestBody final FacturaDto facturaDto,
			BindingResult result ) {
		
		logger.info("Entramos en el controlador postFactura() username={}",username);
		ResponseMessage _response = this.facturaService.validarFactura(facturaDto, username, xHeaderHttp, result);
		return _response;
	}
	
}
