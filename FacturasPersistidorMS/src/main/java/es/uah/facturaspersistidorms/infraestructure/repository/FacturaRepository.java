package es.uah.facturaspersistidorms.infraestructure.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.uah.facturaspersistidorms.infraestructure.model.entities.Factura;

public interface FacturaRepository extends MongoRepository<Factura, String> {

	public Factura findByNumero(String numero);
	public List<Factura> findByFechaEmision(String fechaEmision);
}
