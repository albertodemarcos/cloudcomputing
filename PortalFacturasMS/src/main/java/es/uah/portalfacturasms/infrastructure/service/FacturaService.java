package es.uah.portalfacturasms.infrastructure.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.uah.portalfacturasms.infrastructure.model.Factura;
import es.uah.portalfacturasms.infrastructure.utils.ResponseMessage;

@Service
public class FacturaService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//URL
	private static final String URL_GENERAL = "http://";
	private static final String URL_PROCESADOR_FACTURA = "/procesadorFactura";
	
	@Autowired
    private RestTemplate httpTemplate;
	
	public ResponseMessage persisteFactura(final Factura factura, final String username) {
		
		logger.info("Entramos en el metodo persisteFactura(factura={}, username={})",factura.getNumero(),username);
		
		String _url = this.createUrl("");
		
		ResponseMessage _response = httpTemplate.postForObject(_url, factura, ResponseMessage.class);
		
		return _response;
	}
	
	
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
