package cl.usach.tingeso.sistemamilkstgo.Services;

import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<ProveedorEntity> findAll() {
        return proveedorRepository.findAll();
    }

    public ResponseEntity<ProveedorEntity> save(ProveedorEntity proveedorEntity) {
        try {
            boolean exist = proveedorRepository.findAll().stream().anyMatch(p -> p.getCodigo().equals(proveedorEntity.getCodigo())); // Comprueba si ya existe un proveedor con el mismo código que el que se está intentando guardar en la base de datos.
            if (exist)
                throw new RuntimeException(); // Si se encuentra un proveedor existente con el mismo código, lanza una excepción.
            ProveedorEntity _proveedorEntity = proveedorRepository.save(proveedorEntity); // Si no se encuentra un proveedor existente con el mismo código, guarda el nuevo proveedor en la base de datos.
            return ResponseEntity.created(new URI("/proveedor/save" + _proveedorEntity.getCodigo())).body(_proveedorEntity); // Devuelve una respuesta HTTP 201 (CREATED) con una URI que apunta al recurso recién creado. El cuerpo de la respuesta contiene el objeto proveedor guardado.
        } catch (Exception e) {
            return ResponseEntity.status(400).build(); // Si se produce una excepción al intentar guardar el proveedor, devuelve una respuesta HTTP 400 (BAD REQUEST).
        }
    }

    public ProveedorEntity findByCodigo(String proveedorCodigo) {
        return proveedorRepository.findByCodigo(proveedorCodigo);
    }
}
