package es.uah.facturasvalidadorms.infraestructure.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FacturaDto implements Serializable {

	private static final long serialVersionUID = 6639589389606784322L;

	private String numero;
	private Date fechaEmision;
	private String cliente;
	private String emisor;
	private String concepto;
	private BigDecimal importe;

	public FacturaDto() {
		super();
	}

	public FacturaDto(String numero, Date fechaEmision, String cliente, String emisor, String concepto,
			BigDecimal importe) {
		super();
		this.numero = numero;
		this.fechaEmision = fechaEmision;
		this.cliente = cliente;
		this.emisor = emisor;
		this.concepto = concepto;
		this.importe = importe;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

}
