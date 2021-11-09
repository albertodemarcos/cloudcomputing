package es.uah.facturaspersistidorms.infraestructure.builders.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import es.uah.facturaspersistidorms.infraestructure.builders.IBuilder;
import es.uah.facturaspersistidorms.infraestructure.model.dtos.FacturaDto;
import es.uah.facturaspersistidorms.infraestructure.model.entities.Factura;


@Component
public class FacturaBuilder implements IBuilder<Factura, FacturaDto> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Factura build(FacturaDto dto, String username) {
		// TODO Auto-generated method stub.
		
		if( dto == null ) 
		{
			logger.error("La factura no ha llegado correctamente por el usuario={}", username);
			return null;
		}
		
		Factura _factura = new Factura();
		
		_factura.setCliente(dto.getCliente());
		_factura.setConcepto(dto.getConcepto());
		_factura.setEmisor(dto.getEmisor());
		_factura.setFechaEmision(dto.getFechaEmision());
		_factura.setImporte(dto.getImporte());
		_factura.setNumero(dto.getNumero());
		_factura.setUsername(username);		
		
		return _factura;
	}

}
