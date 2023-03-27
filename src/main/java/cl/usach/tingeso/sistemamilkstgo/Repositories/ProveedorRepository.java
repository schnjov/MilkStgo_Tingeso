package cl.usach.tingeso.sistemamilkstgo.Repositories;

import cl.usach.tingeso.sistemamilkstgo.Entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    public List<Proveedor> findByCategoria(String categoria);
}
