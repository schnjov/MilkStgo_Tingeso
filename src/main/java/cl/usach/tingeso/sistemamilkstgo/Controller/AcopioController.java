package cl.usach.tingeso.sistemamilkstgo.Controller;

import cl.usach.tingeso.sistemamilkstgo.Services.AcopioService;
import cl.usach.tingeso.sistemamilkstgo.SistemaMilkStgoApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping
public class AcopioController {
    private final Logger logger = Logger.getLogger(SistemaMilkStgoApplication.class.getName());

    @Autowired
    private AcopioService acopioService;
    @GetMapping("/acopio/excel")
    public String acopioExcel() {
        return "acopio-excel";
    }
    @PostMapping(value = "acopio/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String save(@ModelAttribute MultipartFile file) {
        String redirect;
        if (acopioService.saveExcel(file).getStatusCode() == HttpStatus.CREATED) {
            logger.log(java.util.logging.Level.INFO, "POST /acopio/save: 200");
            redirect = "Validators/successful-submit";
        } else {
            logger.log(Level.SEVERE, "POST /acopio/save: 400");
            redirect = "Validators/error-submit";
        }
        return redirect;
    }
}
