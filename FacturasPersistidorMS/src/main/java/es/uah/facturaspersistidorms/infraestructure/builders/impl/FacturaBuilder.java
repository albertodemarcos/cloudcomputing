package es.uah.facturaspersistidorms.infraestructure.builders.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import es.uah.facturaspersistidorms.infraestructure.builders.IBuilder;
import es.uah.facturaspersistidorms.infraestructure.model.dtos.FacturaDto;
import es.uah.facturaspersistidorms.infraestructure.model.dtos.LineaFacturaDto;
import es.uah.facturaspersistidorms.infraestructure.model.entities.Factura;
import es.uah.facturaspersistidorms.infraestructure.model.entities.LineaFactura;


@Component
public class FacturaBuilder implements IBuilder<Factura, FacturaDto> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LineaFacturaBuilder lineaFacturaBuilder;
	
	
	@Override
	public Factura build(FacturaDto dto, String username) {
		
		logger.info("Se realiza el proceso de crear la factura por el usuario={}", username);
		
		if( dto == null ) 
		{
			logger.error("La factura no ha llegado correctamente por el usuario={}", username);
			return null;
		}
		
		Factura _factura = crearFacturaDesdeFacturaDto(dto, username);		
		
		return _factura;
	}

	private Factura crearFacturaDesdeFacturaDto(FacturaDto dto, String username) {
		
		Factura _factura = new Factura();
		
		_factura.setCliente(dto.getCliente());
		_factura.setConcepto(dto.getConcepto());
		_factura.setEmisor(dto.getEmisor());
		_factura.setFechaEmision(dto.getFechaEmision());
		_factura.setImporte(dto.getImporte());
		_factura.setNumero(dto.getNumero());
		_factura.setUsername(username);
		
		List<LineaFactura> lineasFactura = this.obtieneLineasFacturaDesdeLineasFacturaDto(dto, username);
		
		_factura.setLineasFactura(lineasFactura);
		
		return _factura;
	}

	private List<LineaFactura> obtieneLineasFacturaDesdeLineasFacturaDto(FacturaDto _facturaDto, String username) {
		
		List<LineaFactura> _lineasFactura = new ArrayList<LineaFactura>();
		
		if( CollectionUtils.isEmpty( _facturaDto.getLineasFacturaDto() ) )
		{
			logger.error("Las lineas de la factura no han llegado correctamente por el usuario={}", username);
		}
		
		for( LineaFacturaDto _lineaDto : _facturaDto.getLineasFacturaDto() )
		{
			LineaFactura _linea = lineaFacturaBuilder.build(_lineaDto, username);
			
			if( _linea == null ) 
			{
				logger.error("La linea de la factura no ha podido crearse");
				continue;
			}
			
			_lineasFactura.add(_linea);
		}
		
		return _lineasFactura;
	}
	
	
}
