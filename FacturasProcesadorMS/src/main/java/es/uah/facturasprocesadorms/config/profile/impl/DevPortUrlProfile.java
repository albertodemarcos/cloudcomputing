package es.uah.facturasprocesadorms.config.profile.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import es.uah.facturasprocesadorms.config.profile.PortUrlProfile;


@Component
@Profile("dev")
public class DevPortUrlProfile implements PortUrlProfile {

	private static final String PORT_DEV_VALIDADOR = "8090";
	private static final String PORT_DEV_PERSISTIDOR = "8095";
	private static final String PORT_DEV_PERSISTIDOR_LOGGER = "8100";
	
	@Override
	public String getPortUrlValidador() {
		// TODO Auto-generated method stub
		return PORT_DEV_VALIDADOR;
	}
	@Override
	public String getPortUrlPersistidor() {
		// TODO Auto-generated method stub
		return PORT_DEV_PERSISTIDOR;
	}
	@Override
	public String getPortUrlPersistidorLogger() {
		// TODO Auto-generated method stub
		return PORT_DEV_PERSISTIDOR_LOGGER;
	}

}
