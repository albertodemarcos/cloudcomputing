package es.uah.facturaspersistidorms.infraestructure.model.entities;

import java.math.BigDecimal;


public class LineaFactura {
	
	private String producto;
	private String descripcion;
	private BigDecimal importe;
	private BigDecimal cantidad;
	private String unidad;
	private String impuesto;
	
	public LineaFactura() {
		super();
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}

	@Override
	public String toString() {
		return "LineaFacturaDto [producto=" + producto + ", descripcion=" + descripcion + ", importe=" + importe
				+ ", cantidad=" + cantidad + ", unidad=" + unidad + ", impuesto=" + impuesto + "]";
	}

}
