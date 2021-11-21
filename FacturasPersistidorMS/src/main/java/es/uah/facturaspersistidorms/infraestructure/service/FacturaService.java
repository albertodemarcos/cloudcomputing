package es.uah.facturaspersistidorms.infraestructure.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uah.facturaspersistidorms.infraestructure.builders.impl.FacturaBuilder;
import es.uah.facturaspersistidorms.infraestructure.model.dtos.FacturaDto;
import es.uah.facturaspersistidorms.infraestructure.model.entities.Factura;
import es.uah.facturaspersistidorms.infraestructure.repository.FacturaRepository;
import es.uah.facturaspersistidorms.infraestructure.utils.ResponseMessage;


@Service
public class FacturaService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//CODES
	private static final String NOK = "-1";
	private static final String OK = "1";
	
	@Autowired
	private FacturaRepository facturaRepository;
	
	@Autowired
	private FacturaBuilder facturaBuilder;
	
	/**
	 * 
	 * @param factura
	 * @param username
	 * @return
	 */
	public ResponseMessage persisteFactura(final FacturaDto facturaDto, final String username, final String xHeaderHttp) {
		
		logger.info("Entramos en el metodo persisteFactura(factura={}, username={})",facturaDto.getNumero(),username);
		
		Factura _factura = this.facturaBuilder.build(facturaDto, username);
		
		if( _factura == null ) {
			
			logger.error("Se ha producido un error al crear la entidad factura para el username={}", username);
			
			return new ResponseMessage(NOK, "Se ha producido un error al crear la entidad factura", null);
		}
		
		_factura = this.persiste(_factura);
		
		if( _factura == null || StringUtils.isBlank(_factura.getId() ) ) {
			
			logger.error("Se ha producido un error al persistir la entidad factura para el username={}", username);
			
			return new ResponseMessage(NOK, "Se ha producido un error al persistir la entidad factura", null);
		}
		
		ResponseMessage _responsePersist = new ResponseMessage(OK, "", _factura);
		
		logger.info("Se ha persistido la factura={}, username={})",facturaDto.getNumero(),username);
		
		return _responsePersist;
	}
	
	
	private Factura persiste(Factura _factura) {
		
		logger.debug("Se va a persistir la factura={}, username={})",_factura.getNumero());
		
		try {
			
			_factura = facturaRepository.save(_factura);
			
		}catch(IllegalArgumentException iae) {
			
			logger.error("Se ha producido un error al persistir la entidad. ERROR={}",iae.getMessage());
			
			iae.printStackTrace();
			
		}catch(Exception e) {
			
			logger.error("Se ha producido un error al persistir la entidad. ERROR={}",e.getMessage());
			
			e.printStackTrace();
		}
		
		return _factura;
	}
	
}
