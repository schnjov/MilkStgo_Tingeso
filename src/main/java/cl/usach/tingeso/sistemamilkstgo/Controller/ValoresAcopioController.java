package cl.usach.tingeso.sistemamilkstgo.Controller;

import cl.usach.tingeso.sistemamilkstgo.Services.ValoresAcopioService;
import cl.usach.tingeso.sistemamilkstgo.SistemaMilkStgoApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/valores-acopio")
public class ValoresAcopioController {
    @Autowired
    private ValoresAcopioService valoresAcopioService;
    private final static Logger logger = Logger.getLogger(SistemaMilkStgoApplication.class.getName());

    @GetMapping("/excel")
    public String valoresAcopioExcel() {
        return "valores-acopio-excel";
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String save(@ModelAttribute MultipartFile file) {
        if (valoresAcopioService.saveExcel(file).getStatusCode() == HttpStatus.CREATED) {
            logger.log(java.util.logging.Level.INFO, "POST /acopio/save: 200");
            return "Validators/successful-submit";
        } else {
            logger.log(Level.SEVERE, "POST /acopio/save: 400");
            return "Validators/error-submit";
        }
    }



}
