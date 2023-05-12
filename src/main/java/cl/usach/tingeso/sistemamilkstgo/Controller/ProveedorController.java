package cl.usach.tingeso.sistemamilkstgo.Controller;

import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/proveedores/listar")
    public String proveedorList(Model model) {
        List<ProveedorEntity> proveedores = proveedorService.findAll();
        model.addAttribute("proveedores", proveedores);
        return "lista-proveedores";
    }
    @GetMapping("/proveedores/agregar")
    public String agregarProveedorVista(Model model) {
        model.addAttribute("proveedor", new ProveedorEntity());
        return "new-proveedor";
    }

    @PostMapping(value = "proveedor/save") // Mapea la solicitud HTTP POST para guardar un proveedor a este método controlador.
    public String save(@ModelAttribute("proveedor") ProveedorEntity proveedorEntity) { // Recibe un objeto Proveedor en formato JSON a través del cuerpo de la solicitud HTTP.
        ResponseEntity<ProveedorEntity> proveedor = proveedorService.save(proveedorEntity); // Guarda el proveedor recibido en la base de datos.
        String url;
        if (proveedor.getStatusCode() == HttpStatusCode.valueOf(201)){
            url = "Validators/successful-submit";
        }else {
            url = "Validators/error-submit";
        }
        return url;
    }
}