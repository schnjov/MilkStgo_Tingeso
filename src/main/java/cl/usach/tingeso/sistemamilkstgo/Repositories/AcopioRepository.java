package cl.usach.tingeso.sistemamilkstgo.Repositories;

import cl.usach.tingeso.sistemamilkstgo.Entities.AcopioEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AcopioRepository extends JpaRepository<AcopioEntity, Long>{
    public AcopioEntity findById(String id);

    List<AcopioEntity> findByProveedorAndFechaGreaterThanEqual(ProveedorEntity proveedor, Date fecha);

    List<AcopioEntity> findByFechaGreaterThanEqual(Date date);

    List<AcopioEntity> findByFecha(Date fecha);

    List<AcopioEntity> findByProveedor(ProveedorEntity proveedor);
}
