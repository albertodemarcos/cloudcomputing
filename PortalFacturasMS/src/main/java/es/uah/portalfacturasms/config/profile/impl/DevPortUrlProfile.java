package es.uah.portalfacturasms.config.profile.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import es.uah.portalfacturasms.config.profile.PortUrlProfile;

@Component
@Profile("dev")
public class DevPortUrlProfile implements PortUrlProfile {

	private static final String PORT_DEV = "8085";
	
	@Override
	public String getPortUrl() {
		// TODO Auto-generated method stub
		return PORT_DEV;
	}

}
