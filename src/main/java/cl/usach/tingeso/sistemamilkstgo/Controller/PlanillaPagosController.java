package cl.usach.tingeso.sistemamilkstgo.Controller;

import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import org.springframework.ui.Model;
import cl.usach.tingeso.sistemamilkstgo.Entities.PlanillaPagosEntity;
import cl.usach.tingeso.sistemamilkstgo.Services.PlanillaPagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/planilla")
public class PlanillaPagosController {
    @Autowired
    private PlanillaPagosService planillaPagosService;

    private static final Logger logger = Logger.getLogger(PlanillaPagosController.class.getName());

    @GetMapping
    public String crearPlanillaPagos(){
        planillaPagosService.calcularPagos();
        return "Validators/successful-pays";
    }

    @GetMapping("/pagos/formulario")
    public String mostrarFormularioPagos(Model model){
        return "planillas-pago";
    }


    @GetMapping("/pagos")
    public String mostrarPlanillaPagos(Model model,@RequestParam String codigo_proveedor){
        PlanillaPagosEntity planillaPagos = planillaPagosService.findByIdProveedor(codigo_proveedor);
        if (planillaPagos == null){
            return "Validators/error-pays";
        }
        model.addAttribute("planilla", planillaPagos);
        return "planillas-proveedor";
    }

}
