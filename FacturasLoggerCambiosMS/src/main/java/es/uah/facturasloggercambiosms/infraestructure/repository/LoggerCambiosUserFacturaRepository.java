package es.uah.facturasloggercambiosms.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.uah.facturasloggercambiosms.infraestructure.model.entities.LoggerCambiosUserFactura;

@Repository
public interface LoggerCambiosUserFacturaRepository extends JpaRepository<LoggerCambiosUserFactura, Long> {

}
