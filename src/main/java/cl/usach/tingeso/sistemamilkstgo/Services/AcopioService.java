package cl.usach.tingeso.sistemamilkstgo.Services;
import cl.usach.tingeso.sistemamilkstgo.Entities.AcopioEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Repositories.AcopioRepository;
import cl.usach.tingeso.sistemamilkstgo.SistemaMilkStgoApplication;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.*;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

@Service
public class AcopioService {

    @Autowired
    private AcopioRepository acopioRepository;

    @Autowired
    private ProveedorService proveedorService;

    private final Logger logger = Logger.getLogger(SistemaMilkStgoApplication.class.getName());

    public List<AcopioEntity> findAll() {
        return acopioRepository.findAll();
    }

    public ResponseEntity<Void> saveExcel(MultipartFile file){
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            List<AcopioEntity> acopios = excelToList(sheet);
            acopioRepository.saveAll(acopios);
            return ResponseEntity.created(URI.create("/acopio")).build();
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, e.toString());
            return ResponseEntity.status(400).build();
        }
    }
    public List<AcopioEntity> excelToList(Sheet sheet){
        List<AcopioEntity> acopios = new ArrayList<>();
        int counter = 0;
        while (counter <= sheet.getLastRowNum()) {
            Row row = sheet.getRow(counter);
            if (row != null) { // verifica si la fila no está vacía
                if (counter > 0) { // omite la primera fila (encabezados de columna)
                    Cell fechaCell = row.getCell(0);
                    Cell turnoCell = row.getCell(1);
                    Cell proveedorCell = row.getCell(2);
                    Cell kilosCell = row.getCell(3);
                    if (fechaCell == null || turnoCell == null || proveedorCell == null || kilosCell == null) {
                        break;
                    }
                    Date fecha = fechaCell.getDateCellValue();
                    String turno = turnoCell.getStringCellValue();
                    int turnout;
                    if (turno.equals("M")) {
                        turnout = 1;
                    } else {
                        turnout = 2;
                    }
                    String proveedorCodigo = proveedorCell.getStringCellValue();
                    Integer kilos = (int) kilosCell.getNumericCellValue();
                    ProveedorEntity proveedor = proveedorService.findByCodigo(proveedorCodigo);
                    String id = UUID.randomUUID().toString();
                    if (acopioRepository.findById(id) != null) {
                        id = UUID.randomUUID().toString();
                    }
                    AcopioEntity acopio = new AcopioEntity(id, fecha, turnout, proveedor, kilos);
                    acopios.add(acopio);
                }
            }
            counter++;
        }
        return acopios;
    }
    public List<AcopioEntity> findByProveedor(String codigoProveedor) {
        List<AcopioEntity> acopios;
        ProveedorEntity proveedor = proveedorService.findByCodigo(codigoProveedor);
        acopios = acopioRepository.findByProveedor(proveedor);
        return acopios;
    }
}