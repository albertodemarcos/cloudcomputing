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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import es.uah.portalfacturasms.infrastructure.model.Factura;
import es.uah.portalfacturasms.infrastructure.utils.ResponseMessage;

@Service
public class FacturaService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//URL
	private static final String URL_GENERAL = "http://facturasprocesadorms:8085";
	private static final String URL_PROCESADOR_FACTURA = "/procesadorFacturas/";
	private static final String PERSISTE_FACTURA = "factura";
	
	@Autowired
    private RestTemplate httpTemplate;
	
	@Autowired
	private UserService userService;
	
	public ResponseMessage persisteFacturaDeUsuario(final Factura factura, final String username, final String xHeaderHttp) 
	{
		ResponseMessage _responeUser = userService.obtieneResponseMessageUsername(username);
		
		if(_responeUser == null || StringUtils.isBlank( _responeUser.getCode() ) || _responeUser.getCode().trim() != "1") 
		{
			logger.error("El usuario con username={} no existe y debe autenticase en redis", username);
			return new ResponseMessage("-1", "El usuario no existe", null);
		}
		
		ResponseMessage _responseFacturador = this.persisteFactura(factura, username, xHeaderHttp);
		
		if( _responeUser == null || StringUtils.isBlank( _responeUser.getCode() ) ) 
		{
			logger.error("La factura con numero={} del usuario={} no ha sido persistida", username);
			return new ResponseMessage("-1", "El usuario no existe", null);
		}
		
		return _responseFacturador;
	}
	
	private ResponseMessage persisteFactura(final Factura factura, final String username, final String xHeaderHttp)
	{		
		logger.info("Entramos en el metodo persisteFactura(factura={}, username={})",factura.getNumero(),username);		
		
		ResponseMessage _response = null;
		ResponseEntity<ResponseMessage> _responseHttp = null;
		
		String _url = this.createUrl(PERSISTE_FACTURA, username);
		
		if( StringUtils.isBlank(_url) )
		{	
			logger.error("La url del microservicio de procesado de factura no es correcta. URL={}", _url);
			_response = new ResponseMessage("-1", "Se ha producido un error interno");
		}
		try 
		{
			HttpEntity<?> httpEntity = this.obtieneParametrosURL(factura, xHeaderHttp);
			
			_responseHttp = this.httpTemplate.exchange(_url, HttpMethod.POST, httpEntity, ResponseMessage.class);
			
			if( _responseHttp != null ) {
			
				return _responseHttp.getBody();
			}
		}
		catch(RestClientException e) 
		{
			logger.error("El microservicio de procesado de factura no esta operativo");
			e.printStackTrace();
			_response = new ResponseMessage("-1", "No se ha podido obtener respuesta del procesador de facturas");
		}
		catch(Exception e) 
		{
			logger.error("El microservicio de procesado de factura no ha respondido correctamente");
			e.printStackTrace();
			_response = new ResponseMessage("-1", "No se ha podido obtener respuesta del procesador de facturas");
		}
		return _response;
	}

	private HttpEntity<?> obtieneParametrosURL(final Factura factura, final String xHeaderHttp) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-tipo", xHeaderHttp);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<?> httpEntity = new HttpEntity<Object>(factura, headers);
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
		String _url = URL_GENERAL + URL_PROCESADOR_FACTURA + _uri.trim() + "?username="+username;		
		logger.info("Salimos del metodo createUrl(uri={}) con _url={}", _uri, _url);		
		return _url;
	}
	
}
