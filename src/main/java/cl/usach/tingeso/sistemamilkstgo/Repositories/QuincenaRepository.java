package cl.usach.tingeso.sistemamilkstgo.Repositories;

import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.QuincenaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface QuincenaRepository extends JpaRepository<QuincenaEntity, Long>{
    public QuincenaEntity findTopByProveedorOrderByFechaDesc(ProveedorEntity proveedor);

    List<QuincenaEntity> findQuincenaEntityByProveedor(ProveedorEntity proveedor);
    List<QuincenaEntity> findQuincenaEntityByProveedorOrderByFechaDesc(ProveedorEntity proveedor);
}
