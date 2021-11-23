package es.uah.facturasprocesadorms.config.profile.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import es.uah.facturasprocesadorms.config.profile.PortUrlProfile;


@Component
@Profile("default")
public class DefaultPortUrlProfile implements PortUrlProfile {

	private static final String PORT_DEFAULT = "8080";
	
	@Value("${validador.port:}")
	private String PORT_VALIDADOR;
	
	@Value("${persistidor.port:}")
	private String PORT_PERSISTIDOR;
	
	@Value("${persistidorlogger.port:}")
	private String PORT_PERSISTIDOR_LOGGER;
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String getPortUrlValidador() {
		
		logger.info("getPortUrlValidador()");
		
		if( StringUtils.isNotBlank(PORT_VALIDADOR) ) 
		{
			logger.info("getPortUrl() => port={}", PORT_VALIDADOR);
			return PORT_VALIDADOR;
		}
		
		logger.info("Not found port. getPortUrl() => portDefault={}", PORT_DEFAULT);
		
		return PORT_DEFAULT;
	}

	@Override
	public String getPortUrlPersistidor() {
		
		logger.info("getPortUrlPersistidor()");
		
		if( StringUtils.isNotBlank(PORT_PERSISTIDOR) ) 
		{
			logger.info("getPortUrl() => port={}", PORT_PERSISTIDOR);
			return PORT_PERSISTIDOR;
		}
		
		logger.info("Not found port. getPortUrl() => portDefault={}", PORT_DEFAULT);
		
		return PORT_DEFAULT;
	}

	@Override
	public String getPortUrlPersistidorLogger() {
		
		logger.info("getPortUrlPersistidorLogger()");
		
		if( StringUtils.isNotBlank(PORT_PERSISTIDOR_LOGGER) ) 
		{
			logger.info("getPortUrl() => port={}", PORT_PERSISTIDOR_LOGGER);
			return PORT_PERSISTIDOR_LOGGER;
		}
		
		logger.info("Not found port. getPortUrl() => portDefault={}", PORT_DEFAULT);
		
		return PORT_DEFAULT;
	}

}
