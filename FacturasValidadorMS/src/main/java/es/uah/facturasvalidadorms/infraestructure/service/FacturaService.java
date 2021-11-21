package es.uah.facturasvalidadorms.infraestructure.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import es.uah.facturasvalidadorms.infraestructure.model.FacturaDto;
import es.uah.facturasvalidadorms.infraestructure.utils.ResponseMessage;
import es.uah.facturasvalidadorms.infraestructure.validators.FacturaValidator;


@Service
public class FacturaService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//CODES
	private static final String OK = "1";
	private static final String NOK = "-1";
	
	@Autowired
	private FacturaValidator facturaValidator;
	
	/**
	 * 
	 * @param factura
	 * @param username
	 * @return
	 */
	public ResponseMessage validarFactura(final FacturaDto facturaDto, final String username, final String xHeaderHttp, BindingResult result) {
		
		logger.info("Entramos en el metodo validarFactura(factura={}, username={})", facturaDto!=null?facturaDto.getNumero():null, username);
		
		this.facturaValidator.validate(facturaDto, result);
		
		if( result.hasErrors() ) {
			
			logger.error("La factura no esta bien formada. Errores={}", result.getAllErrors().toString());
			
			return new ResponseMessage(NOK, "La factura no esta bien formada", result.getAllErrors() );
		}
		
		ResponseMessage _responsePersist = new ResponseMessage(OK, "Se ha validado la factura", facturaDto);
		
		logger.info("Se ha validado la factura={}, username={})",facturaDto.getNumero(), username);
		
		return _responsePersist;
	}
	
	
}
