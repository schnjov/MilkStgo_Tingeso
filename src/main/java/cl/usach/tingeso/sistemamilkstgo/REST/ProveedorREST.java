package cl.usach.tingeso.sistemamilkstgo.REST;

import cl.usach.tingeso.sistemamilkstgo.Entities.Proveedor;
import cl.usach.tingeso.sistemamilkstgo.Services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("proveedor/")
public class ProveedorREST {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("all")
    public List<Proveedor> getAll() {
        return proveedorService.findAll();
    }

    @PostMapping("save")
    public Proveedor save(@RequestBody Proveedor proveedor) {
        return proveedorService.save(proveedor);
    }


}
