package cl.usach.tingeso.sistemamilkstgo.Repositories;

import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, String> {
    public List<ProveedorEntity> findByCategoria(String categoria);
    ProveedorEntity findByCodigo(String codigo);
}
