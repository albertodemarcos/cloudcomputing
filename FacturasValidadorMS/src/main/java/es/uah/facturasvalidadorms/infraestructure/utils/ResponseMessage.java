package es.uah.facturasvalidadorms.infraestructure.utils;

import java.io.Serializable;

public class ResponseMessage implements Serializable {

	private static final long serialVersionUID = 5190554317900779419L;
	
	private String code;
	private String mensaje;
	private Object data;

	public ResponseMessage() {
		super();
	}
	
	public ResponseMessage(String mensaje, Object data) {
		super();
		this.mensaje = mensaje;
		this.data = data;
	}
	
	public ResponseMessage(String code, String mensaje, Object data) {
		super();
		this.code = code;
		this.mensaje = mensaje;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	
	
}
