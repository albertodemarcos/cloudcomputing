package es.uah.facturasloggercambiosms.infraestructure.builders.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import es.uah.facturasloggercambiosms.infraestructure.builders.IBuilder;
import es.uah.facturasloggercambiosms.infraestructure.model.dtos.FacturaDto;
import es.uah.facturasloggercambiosms.infraestructure.model.entities.LoggerCambiosUserFactura;

@Component
public class FacturaBuilder implements IBuilder<LoggerCambiosUserFactura, FacturaDto> {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public LoggerCambiosUserFactura build(final FacturaDto facturaDto, final String username) {
		
		// TODO Auto-generated method stub
		
		if( facturaDto == null ) 
		{
			logger.error("La factura no ha llegado correctamente por el usuario={}", username);
			return null;
		}
		
		LoggerCambiosUserFactura _loggerCambios = new LoggerCambiosUserFactura();
		
		_loggerCambios.setNumeroFactura(facturaDto.getNumero());
		_loggerCambios.setUsername(username);
		
		String _factura = null;
		
		try {
			_factura = ( new Gson() ).toJson( facturaDto );
			
		}catch(Exception e) {
			
			logger.error("Se ha producido un error al convertir la factura a JSON. Username={} Error={}",
					username, e.getMessage() );
			
			e.printStackTrace();
		}
		
		_loggerCambios.setFactura(_factura);		
		
		return _loggerCambios;
	}

	
	
	

}
