package cl.usach.tingeso.sistemamilkstgo.REST;

import cl.usach.tingeso.sistemamilkstgo.Entities.Proveedor;
import cl.usach.tingeso.sistemamilkstgo.Services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class OrquestadorREST {

    @Autowired
    private ProveedorService proveedorService;
    @GetMapping("/proveedores/agregar")
    public String agregarProveedor(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "new-proveedor";
    }

    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/proveedores/listar")
    public String proveedorList(Model model) {
        List<Proveedor> proveedores = proveedorService.findAll();
        model.addAttribute("proveedores", proveedores);
        return "lista-proveedores";
    }
}
