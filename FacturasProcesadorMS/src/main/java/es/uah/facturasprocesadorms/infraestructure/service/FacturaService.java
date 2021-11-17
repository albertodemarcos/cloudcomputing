package es.uah.facturasprocesadorms.infraestructure.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import es.uah.facturasprocesadorms.infraestructure.model.Factura;
import es.uah.facturasprocesadorms.infraestructure.utils.ResponseMessage;


@Service
public class FacturaService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//URL
	private static final String URL_GENERAL_VALIDADOR_FACTURA = "http://facturasvalidadorms:8090/validadorFacturas";
	private static final String URL_GENERAL_PERSISTIDOR_FACTURA = "http://facturaspersistidorms:8095/persistidorFacturas";
	private static final String URL_GENERAL_PERSISTIDOR_LOGGER = "http://facturasloggercambiosms:8100/loggerCambiosFacturas";
	private static final String FACTURA = "factura";
	
	//TYPES
	private static final String TIPO_VALIDADOR = "VALIDADOR";
	private static final String TIPO_PERSISTIDOR = "PERSISTIDOR";
	private static final String TIPO_LOGGER = "LOGGER_CAMBIOS";
	
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
			
			logger.error("Se ha producido un error al persistir la factura para el usuario={}", username);
			
			ResponseMessage _responseErrorPersister = _responsePersist == null ? new ResponseMessage(NOK, "Se ha producido un error al guardar la factura", null) : _responsePersist;
			
			return _responseErrorPersister;
		}
		
		ResponseMessage _responsePersistLogger = this.postPersisteLoggerFactura(factura, username, xHeaderHttp);
		
		if( this.validarPeticionHttp(_responsePersistLogger) ) {
			
			logger.error("Se ha producido un error al persistir el logger de la factura para el usuario={}", username);
			
			ResponseMessage _responseErrorPersister = _responsePersistLogger == null ? new ResponseMessage(NOK, "Se ha producido un error al guardar el logger", null) : _responsePersistLogger;
			
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
		
		logger.info("Entramos en el metodo postValidarFactura(factura={}, username={})",factura.getNumero(),username);
		
		ResponseMessage _response = null;
		ResponseEntity<ResponseMessage> _responseHttp = null;
		
		String _uri = "/" + FACTURA + "?username="+username;
		String _url = this.createUrl(_uri, TIPO_VALIDADOR);
		
		if( StringUtils.isBlank(_url) )
		{	
			logger.error("La url del microservicio de validacion de la factura no es correcta. URL={}", _url);
			_response = new ResponseMessage("-1", "Se ha producido un error interno");
		}
		try 
		{
			HttpEntity<?> httpEntity = this.obtieneParametrosURL(factura, xHeaderHttp);
			
			_responseHttp = this.httpTemplate.exchange(_url, HttpMethod.POST, httpEntity, ResponseMessage.class);
			
			_response = ( _responseHttp != null ) ? _responseHttp.getBody() : new ResponseMessage("-1", "No se ha podido obtener respuesta del validador de facturas");
		}
		catch(RestClientException e) 
		{
			logger.error("El microservicio de validacion de la factura no esta operativo");
			e.printStackTrace();
			_response = new ResponseMessage("-1", "No se ha podido obtener respuesta del validador de facturas");
		}
		catch(Exception e) 
		{
			logger.error("El microservicio de procesado de factura no ha respondido correctamente");
			e.printStackTrace();
			_response = new ResponseMessage("-1", "No se ha podido obtener respuesta del validador de facturas");
		}
		
		return _response;
	}
	
	/**
	 * 
	 * @param factura
	 * @param username
	 * @return
	 */
	private ResponseMessage postPersisteFactura(final Factura factura, final String username, final String xHeaderHttp) {
		
		logger.info("Entramos en el metodo postPersisteFactura(factura={}, username={})",factura.getNumero(),username);
		
		ResponseMessage _response = null;
		ResponseEntity<ResponseMessage> _responseHttp = null;
		
		String _uri = "/" + FACTURA + "?username="+username;
		String _url = this.createUrl(_uri, TIPO_PERSISTIDOR);
		
		if( StringUtils.isBlank(_url) )
		{	
			logger.error("La url del microservicio de persistidor de la factura no es correcta. URL={}", _url);
			_response = new ResponseMessage("-1", "Se ha producido un error interno");
		}
		try 
		{
			HttpEntity<?> httpEntity = this.obtieneParametrosURL(factura, xHeaderHttp);
			
			_responseHttp = this.httpTemplate.exchange(_url, HttpMethod.POST, httpEntity, ResponseMessage.class);
			
			_response = ( _responseHttp != null ) ? _responseHttp.getBody() : new ResponseMessage("-1", "No se ha podido obtener respuesta del persistidor de facturas");
		}
		catch(RestClientException e) 
		{
			logger.error("El microservicio de procesado de factura no esta operativo");
			e.printStackTrace();
			_response = new ResponseMessage("-1", "No se ha podido obtener respuesta del persistidor de facturas");
		}
		catch(Exception e) 
		{
			logger.error("El microservicio de procesado de factura no ha respondido correctamente");
			e.printStackTrace();
			_response = new ResponseMessage("-1", "No se ha podido obtener respuesta del persistidor de facturas");
		}
		
		return _response;
	}
	
	/**
	 * 
	 * @param factura
	 * @param username
	 * @return
	 */
	private ResponseMessage postPersisteLoggerFactura(final Factura factura, final String username, final String xHeaderHttp) {
		
		logger.info("Entramos en el metodo postPersisteLoggerFactura(factura={}, username={})",factura.getNumero(),username);
		
		ResponseMessage _response = null;
		ResponseEntity<ResponseMessage> _responseHttp = null;
		
		String _uri = "/" + FACTURA + "?username="+username;
		String _url = this.createUrl(_uri, TIPO_LOGGER);
		
		if( StringUtils.isBlank(_url) )
		{	
			logger.error("La url del microservicio de persistidor de la factura no es correcta. URL={}", _url);
			_response = new ResponseMessage("-1", "Se ha producido un error interno");
		}
		try 
		{
			HttpEntity<?> httpEntity = this.obtieneParametrosURL(factura, xHeaderHttp);
			
			_responseHttp = this.httpTemplate.exchange(_url, HttpMethod.POST, httpEntity, ResponseMessage.class);
			
			_response = ( _responseHttp != null ) ? _responseHttp.getBody() : new ResponseMessage("-1", "No se ha podido obtener respuesta del persistidor de facturas");
		}
		catch(RestClientException e) 
		{
			logger.error("El microservicio de procesado de factura no esta operativo");
			e.printStackTrace();
			_response = new ResponseMessage("-1", "No se ha podido obtener respuesta del persistidor de facturas");
		}
		catch(Exception e) 
		{
			logger.error("El microservicio de procesado de factura no ha respondido correctamente");
			e.printStackTrace();
			_response = new ResponseMessage("-1", "No se ha podido obtener respuesta del persistidor de facturas");
		}
		
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
	 * @param factura
	 * @param xHeaderHttp
	 * @return
	 */
	private HttpEntity<?> obtieneParametrosURL(final Factura factura, final String xHeaderHttp) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-tipo", xHeaderHttp);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<?> httpEntity = new HttpEntity<Object>(factura, headers);
		return httpEntity;
	}
	
	/**
	 * 
	 * @param _uri
	 * @param username
	 * @return
	 */
	private String createUrl(final String _uri, final String _tipo)
	{
		logger.info("Entramos en el metodo createUrl(uri={})", _uri);		
		if( StringUtils.isBlank(_uri) || StringUtils.isBlank(_tipo) )
		{			
			logger.error("La uri no es correcta. URI={}", _uri);
			return null;
		}
		
		String _url = null;
		
		switch(_tipo) {
		case TIPO_VALIDADOR:
			_url = URL_GENERAL_VALIDADOR_FACTURA + _uri.trim();
			break;
		case TIPO_PERSISTIDOR:
			_url = URL_GENERAL_PERSISTIDOR_FACTURA + _uri.trim();
			break;
		case TIPO_LOGGER:
			_url = URL_GENERAL_PERSISTIDOR_LOGGER + _uri.trim();
		default:
			break;
		}
		logger.info("Salimos del metodo createUrl(uri={}) con _url={}", _uri, _url);		
		return _url;
	}
	
}
