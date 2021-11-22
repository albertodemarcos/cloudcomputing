package es.uah.facturaspersistidorms.infraestructure.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "facturas")
public class Factura implements Serializable {

	private static final long serialVersionUID = 6639589389606784322L;

	private String id;
	private String username;
	private String numero;
	private Date fechaEmision;
	private String cliente;
	private String emisor;
	private String concepto;
	private BigDecimal importe;
	private List<LineaFactura> lineasFactura;

	public Factura() {
		super();
	}

	public Factura(String numero, Date fechaEmision, String cliente, String emisor, String concepto,
			BigDecimal importe) {
		super();
		this.numero = numero;
		this.fechaEmision = fechaEmision;
		this.cliente = cliente;
		this.emisor = emisor;
		this.concepto = concepto;
		this.importe = importe;
	}

	@Id
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<LineaFactura> getLineasFactura() {
		return lineasFactura;
	}

	public void setLineasFactura(List<LineaFactura> lineasFactura) {
		this.lineasFactura = lineasFactura;
	}

}
