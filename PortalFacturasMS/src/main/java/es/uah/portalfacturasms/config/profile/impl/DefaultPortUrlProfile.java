package es.uah.portalfacturasms.config.profile.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import es.uah.portalfacturasms.config.profile.PortUrlProfile;

@Component
@Profile("default")
public class DefaultPortUrlProfile implements PortUrlProfile {

	private static final String PORT_DEFAULT = "8080";
	
	@Value("${url.port:}")
	private String port;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String getPortUrl() {

		logger.info("getPortUrl()");
		
		if( StringUtils.isNotBlank(port) ) 
		{
			logger.info("getPortUrl() => port={}", port);
			return port;
		}
		
		logger.info("Not found port. getPortUrl() => portDefault={}", PORT_DEFAULT);
		
		return PORT_DEFAULT;
	}
	
}
