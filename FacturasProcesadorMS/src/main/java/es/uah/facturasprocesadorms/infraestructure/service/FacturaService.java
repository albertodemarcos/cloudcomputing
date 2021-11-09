package es.uah.facturasprocesadorms.infraestructure.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.uah.facturasprocesadorms.infraestructure.model.Factura;
import es.uah.facturasprocesadorms.infraestructure.utils.ResponseMessage;


@Service
public class FacturaService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//URL
	private static final String URL_GENERAL = "http://";
	private static final String URL_PROCESADOR_FACTURA = "/procesadorFactura";
	//CODES
	private static final String NOK = "-1";
	
	@Autowired
    private RestTemplate httpTemplate;
	
	/**
	 * 
	 * @param factura
	 * @param username
	 * @return
	 */
	public ResponseMessage procesaFactura(final Factura factura, final String username, final String xHeaderHttp) {
		
		logger.info("Entramos en el metodo procesaFactura(factura={}, username={})",factura.getNumero(),username);
		
		ResponseMessage _responseValidator = this.postValidarFactura(factura, username, xHeaderHttp);
		
		if( this.validarPeticionHttp(_responseValidator) ) {
			
			logger.error("Se ha producido un error al validar la factura para el usuario={}", username);
			
			ResponseMessage _responseErrorValidator = _responseValidator == null ? new ResponseMessage(NOK, "Se ha producido un error al validar la factura", null) : _responseValidator;
			
			return _responseErrorValidator;
		}
		
		ResponseMessage _responsePersist = this.postPersisteFactura(factura, username, xHeaderHttp);
		
		if( this.validarPeticionHttp(_responsePersist) ) {
			
			logger.error("Se ha producido un error al validar la factura para el usuario={}", username);
			
			ResponseMessage _responseErrorPersister = _responseValidator == null ? new ResponseMessage(NOK, "Se ha producido un error al guardar la factura", null) : _responseValidator;
			
			return _responseErrorPersister;
		}
		
		logger.info("Se ha persistido la factura={}, username={})",factura.getNumero(),username);
		
		return _responsePersist;
	}
	
	/**
	 * 
	 * @param factura
	 * @param username
	 * @return
	 */
	private ResponseMessage postValidarFactura(final Factura factura, final String username, final String xHeaderHttp) {
		
		String _urlValidaFactura = this.createUrl("");
		
		ResponseMessage _response = httpTemplate.postForObject(_urlValidaFactura, factura, ResponseMessage.class);
		
		return _response;
	}
	
	/**
	 * 
	 * @param factura
	 * @param username
	 * @return
	 */
	private ResponseMessage postPersisteFactura(final Factura factura, final String username, final String xHeaderHttp) {
		
		String _urlPersisteFactura = this.createUrl("");
		
		ResponseMessage _response = httpTemplate.postForObject(_urlPersisteFactura, factura, ResponseMessage.class);
		
		return _response;
	}
	
	/**
	 * 
	 * @param _response
	 * @return
	 */
	private Boolean validarPeticionHttp(final ResponseMessage _response) {
		
		 return (_response == null || ( StringUtils.isNoneBlank(_response.getCode()) && NOK.equalsIgnoreCase( _response.getCode() ) ) );
	}
	
	/**
	 * 
	 * @param uri
	 * @return
	 */
	private String createUrl(String uri) {

		logger.info("Entramos en el metodo createUrl(uri={})", uri);
		
		if( StringUtils.isBlank(uri) ) {
			
			return null;
		}
		
		String _url = URL_GENERAL + URL_PROCESADOR_FACTURA + uri.trim();
		
		logger.info("Salimos del metodo createUrl(uri={}) con _url={}", uri, _url);
		
		return _url;
	}
	
}
