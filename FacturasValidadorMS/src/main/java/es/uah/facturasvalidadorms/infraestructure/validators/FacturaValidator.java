package es.uah.facturasvalidadorms.infraestructure.validators;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uah.facturasvalidadorms.infraestructure.model.Factura;

@Component
public class FacturaValidator implements Validator {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Factura.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
		logger.info("Se evaluan los datos del dto={} ", Factura.class );
		
		Factura _factura = (Factura) target;
		
		if( _factura == null ) 
		{
			errors.rejectValue("", "", null, "");
		}
		
		if( StringUtils.isBlank(_factura.getNumero()) ) 
		{
			errors.rejectValue("numero", "", null, "");
		}
		
		if( _factura.getFechaEmision() == null  ) 
		{
			errors.rejectValue("fechaEmision", "", null, "");
		}
		
		if( StringUtils.isBlank( _factura.getCliente() ) )
		{
			errors.rejectValue("cliente", "", null, "");
		}
		
		if( StringUtils.isBlank( _factura.getEmisor() ) )
		{
			errors.rejectValue("emisor", "", null, "");
		}
		
		if( StringUtils.isBlank( _factura.getConcepto() ) ) 
		{
			errors.rejectValue("concepto", "", null, "");
		}
		
		if( _factura.getImporte() == null )
		{			
			errors.rejectValue("importe", "", null, "");
		}
		
		
		
	}
	
	
}
