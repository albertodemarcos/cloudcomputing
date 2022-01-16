package es.uah.portalfacturasms.infrastructure.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import es.uah.portalfacturasms.config.profile.PortUrlProfile;
import es.uah.portalfacturasms.infrastructure.model.FacturaDto;
import es.uah.portalfacturasms.infrastructure.utils.ResponseMessage;
import es.uah.portalfacturasms.infrastructure.validator.FacturaValidator;

@Service
public class FacturaService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//URL
	private static final String URL_GENERAL = "http://facturasprocesadorms:";
	private static final String URL_PROCESADOR_FACTURA = "/procesadorFacturas/";
	private static final String PERSISTE_FACTURA = "factura";
	
	//CODES
	private static final String OK = "1";
	private static final String NOK = "-1";
	
	@Autowired
    private RestTemplate httpTemplate;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PortUrlProfile portUrlProfile;
	
	@Autowired
	private FacturaValidator facturaValidator;
	
	public ResponseMessage persisteFacturaDeUsuario(final FacturaDto facturaDto, final String username, final String xHeaderHttp, BindingResult result) 
	{
		ResponseMessage _responeUser = userService.obtieneResponseMessageUsername(username);
		
		if(_responeUser == null || StringUtils.isBlank( _responeUser.getCode() ) || _responeUser.getCode().trim() != OK) 
		{
			logger.error("El usuario con username={} no existe y debe autenticase en redis", username);
			return new ResponseMessage(NOK, "El usuario no existe", null);
		}
		
		ResponseMessage _responseFacturador = this.persisteFactura(facturaDto, username, xHeaderHttp, result);
		
		if( _responeUser == null || StringUtils.isBlank( _responeUser.getCode() ) ) 
		{
			logger.error("La factura con numero={} del usuario={} no ha sido persistida", username);
			return new ResponseMessage(NOK, "El usuario no existe", null);
		}
		
		return _responseFacturador;
	}
	
	private ResponseMessage persisteFactura(final FacturaDto facturaDto, final String username, final String xHeaderHttp, BindingResult result)
	{		
		logger.info("Entramos en el metodo persisteFactura(factura={}, username={})",facturaDto.getNumero(),username);		
		
		ResponseMessage _response = null;
		ResponseEntity<ResponseMessage> _responseHttp = null;
		
		//Comprobamos que la factura es correcta
		this.facturaValidator.validate(facturaDto, result);
		
		if( result.hasErrors() ) {

			logger.error("La factura no esta bien formada. Errores={}", result.getFieldErrors().toString());
			
			return new ResponseMessage(NOK, "La factura no esta bien formada", result.getFieldErrors().toString() );
		}
		
		String _url = this.createUrl(PERSISTE_FACTURA, username);
		
		if( StringUtils.isBlank(_url) )
		{	
			logger.error("La url del microservicio de procesado de factura no es correcta. URL={}", _url);
			_response = new ResponseMessage(NOK, "Se ha producido un error interno");
		}
		try 
		{
			HttpEntity<?> httpEntity = this.obtieneParametrosURL(facturaDto, xHeaderHttp);
			
			_responseHttp = this.httpTemplate.exchange(_url, HttpMethod.POST, httpEntity, ResponseMessage.class);
			
			if( _responseHttp != null ) {
			
				return _responseHttp.getBody();
			}
		}
		catch(RestClientException e) 
		{
			logger.error("El microservicio de procesado de factura no esta operativo");
			e.printStackTrace();
			_response = new ResponseMessage(NOK, "No se ha podido obtener respuesta del procesador de facturas");
		}
		catch(Exception e) 
		{
			logger.error("El microservicio de procesado de factura no ha respondido correctamente");
			e.printStackTrace();
			_response = new ResponseMessage(NOK, "No se ha podido obtener respuesta del procesador de facturas");
		}
		return _response;
	}

	private HttpEntity<?> obtieneParametrosURL(final FacturaDto facturaDto, final String xHeaderHttp) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-tipo", xHeaderHttp);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<?> httpEntity = new HttpEntity<Object>(facturaDto, headers);
		return httpEntity;
	}
	
	
	private String createUrl(final String _uri, final String username)
	{
		logger.info("Entramos en el metodo createUrl(uri={})", _uri);		
		if( StringUtils.isBlank(_uri) || StringUtils.isBlank(username) )
		{			
			logger.error("La uri no es correcta. URI={}", _uri);
			return null;
		}		
		String _url = URL_GENERAL + portUrlProfile.getPortUrl() + URL_PROCESADOR_FACTURA + _uri.trim() + "?username="+username;		
		logger.info("Salimos del metodo createUrl(uri={}) con _url={}", _uri, _url);		
		return _url;
	}
	
	
	
}
