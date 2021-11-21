package es.uah.facturasloggercambiosms.infraestructure.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uah.facturasloggercambiosms.infraestructure.builders.impl.FacturaBuilder;
import es.uah.facturasloggercambiosms.infraestructure.model.dtos.FacturaDto;
import es.uah.facturasloggercambiosms.infraestructure.model.entities.LoggerCambiosUserFactura;
import es.uah.facturasloggercambiosms.infraestructure.repository.LoggerCambiosUserFacturaRepository;
import es.uah.facturasloggercambiosms.infraestructure.utils.ResponseMessage;



@Service
public class FacturaService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//CODES
	private static final String NOK = "-1";
	private static final String OK = "1";
	
	@Autowired
	private FacturaBuilder facturaBuilder;
	
	@Autowired
	private LoggerCambiosUserFacturaRepository loggerCambiosUserFacturaRepository;
	
	/**
	 * 
	 * @param factura
	 * @param username
	 * @return
	 */
	public ResponseMessage persisteLoggerCambiosUserFactura(final FacturaDto facturaDto, final String username, final String xHeaderHttp) {
		
		logger.info("Entramos en el metodo persisteLoggerCambiosUserFactura(factura={}, username={})",facturaDto.getNumero(),username);
		
		LoggerCambiosUserFactura _loggerCambios = this.obtieneBuilderFactura(facturaDto, username);
		
		if( _loggerCambios == null ) {
			
			logger.error("Se ha producido un error al crear la entity LoggerCambiosUserFactura");
			
			return new ResponseMessage(NOK, "Se ha producido un error al crear la entity LoggerCambiosUserFactura", null);
		}
		
		_loggerCambios = this.persiste(_loggerCambios,username);
		
		if( _loggerCambios == null || _loggerCambios.getId() == null ) {
			
			logger.error("Se ha producido un error al persistir la entidad");
			
			return new ResponseMessage(NOK, "Se ha producido un error al persistir la entidad", null);
		}
		
		ResponseMessage _responsePersist = new ResponseMessage(OK, "", _loggerCambios);
		
		logger.info("Se ha persistido el LoggerCambiosUserFactura de la factura={}, username={})",facturaDto.getNumero(),username);
		
		return _responsePersist;
	}
	
	private LoggerCambiosUserFactura persiste(LoggerCambiosUserFactura _loggerCambios, String username) {
		
		logger.debug("Se va a persistir el logger del usuarion={}",username);
		
		try {
		
			_loggerCambios = this.loggerCambiosUserFacturaRepository.save(_loggerCambios);
			
		} catch(IllegalArgumentException iae) {
			
			logger.error("Se ha producido un error. Error={}", iae.getMessage() );
			
			iae.printStackTrace();
			
		} catch(Exception e) {
			
			logger.error("Se ha producido un error. Error={}", e.getMessage() );
		
			e.printStackTrace();
		}
		
		return _loggerCambios;
	}
	
	/**
	 * 
	 * @param factura
	 * @param username
	 * @return
	 */
	private LoggerCambiosUserFactura obtieneBuilderFactura(final FacturaDto facturaDto, final String username) {
		
		LoggerCambiosUserFactura _loggerCambios = this.facturaBuilder.build(facturaDto, username);
		
		return _loggerCambios;
	}
}
