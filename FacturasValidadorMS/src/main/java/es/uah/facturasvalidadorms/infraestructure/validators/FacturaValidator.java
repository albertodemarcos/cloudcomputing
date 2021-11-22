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
	}
	
	private void validarFactura(FacturaDto _facturaDto, Errors errors) {
		
		logger.debug("validarFactura()");
		
		if( StringUtils.isBlank(_facturaDto.getNumero()) ) 
		{
			errors.rejectValue("numero", "", null, "");
		}
		
		if( _facturaDto.getFechaEmision() == null  ) 
		{
			errors.rejectValue("fechaEmision", "", null, "");
		}
		
		if( StringUtils.isBlank( _facturaDto.getCliente() ) )
		{
			errors.rejectValue("cliente", "", null, "");
		}
		
		if( StringUtils.isBlank( _facturaDto.getEmisor() ) )
		{
			errors.rejectValue("emisor", "", null, "");
		}
		
		if( StringUtils.isBlank( _facturaDto.getConcepto() ) ) 
		{
			errors.rejectValue("concepto", "", null, "");
		}
		
		if( _facturaDto.getImporte() == null )
		{			
			errors.rejectValue("importe", "", null, "");
		}
	}
	
	private void validarLineasFactura(FacturaDto _facturaDto, Errors errors) {
		
		logger.debug("validarLineasFactura()");
		
		List<LineaFacturaDto> _lineas = _facturaDto.getLineasFacturaDto();
		
		if( CollectionUtils.isEmpty(_lineas) ) 
		{
			logger.error("No han llegado las lineas de la factura");
			errors.rejectValue("lineas", "", null, "");
			return;
		}
		
		for(LineaFacturaDto _linea : _lineas) 
		{
			this.validarLineaFactura(_linea, errors);
		}
	}
	
	private void validarLineaFactura(LineaFacturaDto _lineaFacturaDto, Errors errors) {
		
		logger.debug("validarLineaFactura()");
		
		if( StringUtils.isBlank( _lineaFacturaDto.getProducto() ) ) 
		{
			errors.rejectValue("producto", "", null, "");
		}
		
		if( StringUtils.isBlank( _lineaFacturaDto.getDescripcion() ) ) 
		{
			errors.rejectValue("descripcion", "", null, "");
		}
		
		if( StringUtils.isBlank( _lineaFacturaDto.getUnidad() ) ) 
		{
			errors.rejectValue("unidad", "", null, "");
		}
		
		if( StringUtils.isBlank( _lineaFacturaDto.getImpuesto() ) ) 
		{
			errors.rejectValue("impuesto", "", null, "");
		}
		
		if( _lineaFacturaDto.getImporte() == null ) 
		{
			errors.rejectValue("importe", "", null, "");
		}
		
		if( _lineaFacturaDto.getCantidad() == null ) 
		{
			errors.rejectValue("cantidad", "", null, "");
		}
		
	}
	
}
