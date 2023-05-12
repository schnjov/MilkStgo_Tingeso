package cl.usach.tingeso.sistemamilkstgo.Services;

import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.ValoresAcopioEntity;
import cl.usach.tingeso.sistemamilkstgo.Repositories.ValoresAcopioRepository;
import cl.usach.tingeso.sistemamilkstgo.SistemaMilkStgoApplication;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class ValoresAcopioService {
    @Autowired
    private ValoresAcopioRepository valoresAcopioRepository;

    @Autowired
    private ProveedorService proveedorService;
    private final Logger logger = Logger.getLogger(SistemaMilkStgoApplication.class.getName());

    public ResponseEntity<Void> saveExcel(MultipartFile file){
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            List<ValoresAcopioEntity> valoresAcopioEntityList = excelToList(sheet);
            valoresAcopioRepository.saveAll(valoresAcopioEntityList);
            return ResponseEntity.created(URI.create("/valores")).build();
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, e.toString());
            return ResponseEntity.status(400).build();
        }
    }
    public List<ValoresAcopioEntity> excelToList(Sheet sheet){
        List<ValoresAcopioEntity> valoresAcopioEntityList = new ArrayList<>();
        int counter = 0;
        while (counter <= sheet.getLastRowNum()) {
            Row row = sheet.getRow(counter);
            if (row != null) { // verifica si la fila no está vacía
                if (counter > 0) { // omite la primera fila (encabezados de columna)
                    Cell proveedorCell = row.getCell(0);
                    Cell grasaCell = row.getCell(1);
                    Cell solidosCell = row.getCell(2);
                    if (proveedorCell == null || grasaCell == null || solidosCell == null) {
                        break;
                    }
                    int grasa = (int) grasaCell.getNumericCellValue();
                    int solidos = (int) solidosCell.getNumericCellValue();
                    String proveedorCodigo = proveedorCell.getStringCellValue();
                    ProveedorEntity proveedor = proveedorService.findByCodigo(proveedorCodigo);
                    String id = UUID.randomUUID().toString();
                    if (valoresAcopioRepository.findById(id).isPresent()) {
                        id = UUID.randomUUID().toString();
                    }
                    ValoresAcopioEntity valoresAcopio = new ValoresAcopioEntity(id, proveedor, grasa, solidos);
                    valoresAcopioEntityList.add(valoresAcopio);
                }
            }
            counter++;
        }
        return valoresAcopioEntityList;
    }
}
