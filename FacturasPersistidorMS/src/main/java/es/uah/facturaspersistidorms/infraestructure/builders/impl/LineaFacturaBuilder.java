package es.uah.facturaspersistidorms.infraestructure.builders.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import es.uah.facturaspersistidorms.infraestructure.builders.IBuilder;
import es.uah.facturaspersistidorms.infraestructure.model.dtos.LineaFacturaDto;
import es.uah.facturaspersistidorms.infraestructure.model.entities.LineaFactura;


@Component
public class LineaFacturaBuilder implements IBuilder<LineaFactura, LineaFacturaDto> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public LineaFactura build(LineaFacturaDto dto, String username) {
		
		logger.info("Se va a crear una linea de factura por el usuario={}", username);
		
		if( dto == null ) 
		{
			logger.error("La linea de la factura no ha llegado correctamente por el usuario={}", username);
			return null;
		}
		
		LineaFactura _lineaFactura = creaLineaFacturaDesdeLineaFacturaDto(dto);
		
		logger.info("Se ha creado una linea de factura={} por el usuario={}", _lineaFactura.toString(), username);
				
		return _lineaFactura;
	}

	private LineaFactura creaLineaFacturaDesdeLineaFacturaDto(LineaFacturaDto dto) {
		
		LineaFactura _lineaFactura = new LineaFactura();
		
		_lineaFactura.setProducto(dto.getProducto());
		_lineaFactura.setDescripcion(dto.getDescripcion());
		_lineaFactura.setImporte(dto.getImporte());
		_lineaFactura.setCantidad(dto.getCantidad());
		_lineaFactura.setUnidad(dto.getUnidad());
		_lineaFactura.setImpuesto(dto.getImpuesto());
		
		return _lineaFactura;
	}

	

}
