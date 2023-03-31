package cl.usach.tingeso.sistemamilkstgo.REST;

import cl.usach.tingeso.sistemamilkstgo.Entities.Proveedor;
import cl.usach.tingeso.sistemamilkstgo.Services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class ProveedorREST {
    @Autowired
    private ProveedorService proveedorService;



    @GetMapping("proveedor/all")
    public List<Proveedor> getAll() {
        return proveedorService.findAll();
    }


    @PostMapping("proveedor/save") // Mapea la solicitud HTTP POST para guardar un proveedor a este método controlador.
    public ResponseEntity<Proveedor> save(@RequestBody Proveedor proveedor) { // Recibe un objeto Proveedor en formato JSON a través del cuerpo de la solicitud HTTP.
        try {
            boolean exist = proveedorService.findAll().stream().anyMatch(p -> p.getCodigo().equals(proveedor.getCodigo())); // Comprueba si ya existe un proveedor con el mismo código que el que se está intentando guardar en la base de datos.
            if (exist)
                throw new RuntimeException(); // Si se encuentra un proveedor existente con el mismo código, lanza una excepción.
            Proveedor _proveedor = proveedorService.save(proveedor); // Si no se encuentra un proveedor existente con el mismo código, guarda el nuevo proveedor en la base de datos.
            return ResponseEntity.created(new URI("/proveedor/save" + _proveedor.getCodigo())).body(_proveedor); // Devuelve una respuesta HTTP 201 (CREATED) con una URI que apunta al recurso recién creado. El cuerpo de la respuesta contiene el objeto proveedor guardado.
        } catch (Exception e) {
            return ResponseEntity.status(400).build(); // Si se produce una excepción al intentar guardar el proveedor, devuelve una respuesta HTTP 400 (BAD REQUEST).
        }
    }
}
