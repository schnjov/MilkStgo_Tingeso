package cl.usach.tingeso.sistemamilkstgo.Controller;

import cl.usach.tingeso.sistemamilkstgo.Entities.QuincenaEntity;
import cl.usach.tingeso.sistemamilkstgo.Services.QuincenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/quincena")
public class QuincenaController {
    @Autowired
    private QuincenaService quincenaService;


}
