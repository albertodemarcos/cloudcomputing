package es.uah.facturasloggercambiosms.infraestructure.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "loggercambiosuserfactura",
	indexes = {
		@Index(columnList = "id"),
		@Index(columnList = "username"),
		@Index(columnList = "numeroFactura")
})
public class LoggerCambiosUserFactura implements Serializable {

	private static final long serialVersionUID = -6163814021931110270L;

	private Long id;
	private String username;
	private String numeroFactura;
	private String factura;

	public LoggerCambiosUserFactura() {
		super();
	}
	
	public LoggerCambiosUserFactura(Long id, String username, String numeroFactura, String factura) {
		super();
		this.id = id;
		this.username = username;
		this.numeroFactura = numeroFactura;
		this.factura = factura;
	}

	@Id
	@SequenceGenerator(name = "LoggerCambiosUserFactura_pk_sequence", sequenceName = "LoggerCambiosUserFactura_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LoggerCambiosUserFactura_pk_sequence")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	@Type(type = "com.vladmihalcea.hibernate.type.json.JsonBinaryType")
	@Column(columnDefinition = "json")
	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

}
