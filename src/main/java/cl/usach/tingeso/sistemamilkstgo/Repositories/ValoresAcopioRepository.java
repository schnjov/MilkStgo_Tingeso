package cl.usach.tingeso.sistemamilkstgo.Repositories;

import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.ValoresAcopioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValoresAcopioRepository extends JpaRepository<ValoresAcopioEntity, String>{
    public ValoresAcopioEntity findByProveedor(ProveedorEntity proveedor);
}