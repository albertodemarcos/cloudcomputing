package es.uah.facturasvalidadorms.infraestructure.validators;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uah.facturasvalidadorms.infraestructure.model.FacturaDto;
import es.uah.facturasvalidadorms.infraestructure.model.LineaFacturaDto;

@Component
public class FacturaValidator implements Validator {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Double totalLineas;

	public FacturaValidator() {
		super();
		this.totalLineas = 0D;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FacturaDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
		logger.info("Se evaluan los datos del dto={} ", FacturaDto.class );
		
		FacturaDto _facturaDto = (FacturaDto) target;
		
		if( _facturaDto == null ) 
		{
			logger.error("No ha llegado la factura");
			errors.rejectValue("factura", "", null, "");
		}
		
		this.validarFactura(_facturaDto, errors);
		
		if(errors.hasErrors()) {
			logger.error("Se ha producido errores al validar la factura");
			return;
		}
		
		this.validarLineasFactura(_facturaDto, errors);
		
		this.totalLineas = 0D;
	}
	
	private void validarFactura(FacturaDto _facturaDto, Errors errors) {
		
		logger.debug("validarFactura()");
		
		if( StringUtils.isBlank(_facturaDto.getNumero()) ) 
		{
			errors.rejectValue("numero", "", null, "El número de la factura esta vacio");
		}
		
		if( _facturaDto.getFechaEmision() == null  ) 
		{
			errors.rejectValue("fechaEmision", "", null, "La fecha de emisión esta vacia");
		}
		
		if( StringUtils.isBlank( _facturaDto.getCliente() ) )
		{
			errors.rejectValue("cliente", "", null, "El nombre del cliente esta vacio");
		}
		
		if( StringUtils.isBlank( _facturaDto.getEmisor() ) )
		{
			errors.rejectValue("emisor", "", null, "El nombre del emisor de la factura esta vacio");
		}
		
		if( StringUtils.isBlank( _facturaDto.getConcepto() ) ) 
		{
			errors.rejectValue("concepto", "", null, "El concepto de la factura esta vacio");
		}
		
		if( _facturaDto.getImporte() == null )
		{			
			errors.rejectValue("importe", "", null, "El importe de la factura esta vacio");
		}
	}
	
	private void validarLineasFactura(FacturaDto _facturaDto, Errors errors) {
		
		logger.debug("validarLineasFactura()");
		
		List<LineaFacturaDto> _lineas = _facturaDto.getLineasFacturaDto();
		
		if( CollectionUtils.isEmpty(_lineas) ) 
		{
			logger.error("No han llegado las lineas de la factura");
			errors.rejectValue("lineas", "", null, "No hay lineas de facturas que expliquen la factura");
			return;
		}
		
		for(int i=0; i < _lineas.size(); i++) 
		{
			LineaFacturaDto _linea = _lineas.get(i);
			this.validarLineaFactura(_linea, i, errors);
		}
		
		this.compruebaSumaTotalesFacturasLineas(_facturaDto, errors);
	}
	
	private void validarLineaFactura(LineaFacturaDto _lineaFacturaDto, int position, Errors errors) {
		
		logger.debug("validarLineaFactura()");
		
		if( StringUtils.isBlank( _lineaFacturaDto.getProducto() ) ) 
		{
			errors.rejectValue("lineasFacturaDto["+position+"].producto", "", null, "El nombre del producto de la linea["+(position+1)+"] esta vacio");
		}
		
		if( StringUtils.isBlank( _lineaFacturaDto.getDescripcion() ) ) 
		{
			errors.rejectValue("lineasFacturaDto["+position+"].descripcion", "", null, "La descripción del producto de la linea["+(position+1)+"] esta vacia");
		}
		
		if( StringUtils.isBlank( _lineaFacturaDto.getUnidad() ) ) 
		{
			errors.rejectValue("lineasFacturaDto["+position+"].unidad", "", null, "La unidad de medida del producto de la linea["+(position+1)+"] esta vacia");
		}
		
		if( StringUtils.isBlank( _lineaFacturaDto.getImpuesto() ) ) 
		{
			errors.rejectValue("lineasFacturaDto["+position+"].impuesto", "", null, "El impuesto del producto de la linea["+(position+1)+"] esta vacio");
		}
		
		if( _lineaFacturaDto.getImporte() == null ) 
		{
			errors.rejectValue("lineasFacturaDto["+position+"].importe", "", null, "El importe del producto de la linea["+(position+1)+"] esta vacio");
		}
		
		if( _lineaFacturaDto.getCantidad() == null ) 
		{
			errors.rejectValue("lineasFacturaDto["+position+"].cantidad", "", null, "La cantidad del producto de la linea["+(position+1)+"] esta vacia");
			return;
		}
		
		totalLineas = totalLineas.doubleValue() + _lineaFacturaDto.getImporte().doubleValue();
	}
	
	private void compruebaSumaTotalesFacturasLineas(FacturaDto _facturaDto, Errors errors) {
		
		Double facturaImporte = 0D;
		
		try {
			
			facturaImporte = 0D; _facturaDto.getImporte().doubleValue();
			
			if( Math.round(_facturaDto.getImporte().doubleValue()) != Math.round(totalLineas.doubleValue()) ) 
			{
				errors.rejectValue("importe", "", null, "El importe de la factura no coincide con la suma de las lineas de la factura");
			}
			
		}catch(Exception e) 
		{
			logger.error("Se ha producido un error al comprobar los importes de la factura={} con sus lineas={}",facturaImporte,totalLineas);
			errors.rejectValue("importe", "", null, "El importe de la factura no coincide con la suma de las lineas de la factura");
		}
	}

}
