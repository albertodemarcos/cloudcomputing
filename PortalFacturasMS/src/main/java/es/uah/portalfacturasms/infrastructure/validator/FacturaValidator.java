package es.uah.portalfacturasms.infrastructure.validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uah.portalfacturasms.infrastructure.model.FacturaDto;


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
			errors.rejectValue("", "", null, "No ha llegado la factura");
		}
		
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

}
