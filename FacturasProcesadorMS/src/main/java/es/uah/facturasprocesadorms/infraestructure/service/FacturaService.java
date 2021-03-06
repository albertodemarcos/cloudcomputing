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

import es.uah.facturasprocesadorms.config.profile.PortUrlProfile;
import es.uah.facturasprocesadorms.infraestructure.model.FacturaDto;
import es.uah.facturasprocesadorms.infraestructure.utils.ResponseMessage;


@Service
public class FacturaService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//URL
	private static final String URL_GENERAL_PERSISTIDOR_FACTURA = "http://facturaspersistidorms:";
	private static final String FACTURA = "factura";
	
	private static final String TIPO_PERSISTIDOR = "PERSISTIDOR";
	
	private static final String NOK = "-1";
	
	@Autowired
    private RestTemplate httpTemplate;
	
	@Autowired
	private PortUrlProfile portUrlProfile;
	
	/**
	 * 
	 * @param factura
	 * @param username
	 * @return
	 */
	public ResponseMessage procesaFactura(final FacturaDto facturaDto, final String username, final String xHeaderHttp) {
		
		logger.info("Entramos en el metodo procesaFactura(factura={}, username={})",facturaDto.getNumero(),username);
		
		ResponseMessage _responsePersist = this.postPersisteFactura(facturaDto, username, xHeaderHttp);
		
		if( this.validarPeticionHttp(_responsePersist) ) {
			
			logger.error("Se ha producido un error al persistir la factura para el usuario={}", username);
			
			ResponseMessage _responseErrorPersister = _responsePersist == null ? new ResponseMessage(NOK, "Se ha producido un error al guardar la factura", null) : _responsePersist;
			
			return _responseErrorPersister;
		}
		
		logger.info("Se ha persistido la factura={}, username={})",facturaDto.getNumero(),username);
		
		return _responsePersist;
	}
	
	/**
	 * 
	 * @param factura
	 * @param username
	 * @return
	 */
	private ResponseMessage postPersisteFactura(final FacturaDto facturaDto, final String username, final String xHeaderHttp) {
		
		logger.info("Entramos en el metodo postPersisteFactura(factura={}, username={})",facturaDto.getNumero(),username);
		
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
			HttpEntity<?> httpEntity = this.obtieneParametrosURL(facturaDto, xHeaderHttp);
			
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
		
		logger.info("validarPeticionHttp()");
		
		Boolean peticionValida = (_response == null || ( StringUtils.isNoneBlank(_response.getCode()) && NOK.equalsIgnoreCase( _response.getCode() ) ) );
		
		logger.debug("validarPeticionHttp() => {}", peticionValida);

		return peticionValida;
	}
	
	/**
	 * 
	 * @param factura
	 * @param xHeaderHttp
	 * @return
	 */
	private HttpEntity<?> obtieneParametrosURL(final FacturaDto facturaDto, final String xHeaderHttp) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-tipo", xHeaderHttp);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<?> httpEntity = new HttpEntity<Object>(facturaDto, headers);
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
		
			case TIPO_PERSISTIDOR:
				_url = URL_GENERAL_PERSISTIDOR_FACTURA + portUrlProfile.getPortUrlPersistidor() + "/persistidorFacturas" + _uri.trim();
				break;
			
			default:
				break;
		}
		
		logger.info("Salimos del metodo createUrl(uri={}) con _url={}", _uri, _url);		
		
		return _url;
	}
	
}
