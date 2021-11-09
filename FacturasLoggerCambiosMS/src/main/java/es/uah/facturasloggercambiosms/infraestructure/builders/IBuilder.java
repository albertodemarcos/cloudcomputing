package es.uah.facturasloggercambiosms.infraestructure.builders;

public interface IBuilder<T, U> {
	
	public T build(final U dto, final String username);
	
}
