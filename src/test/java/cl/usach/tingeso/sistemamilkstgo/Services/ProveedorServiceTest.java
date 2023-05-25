package cl.usach.tingeso.sistemamilkstgo.Services;

import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Repositories.ProveedorRepository;
import cl.usach.tingeso.sistemamilkstgo.Services.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProveedorServiceTest {

    @InjectMocks
    private ProveedorService proveedorService;

    @Mock
    private ProveedorRepository proveedorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ReturnsListOfProveedores() {
        // Arrange
        List<ProveedorEntity> proveedores = new ArrayList<>();
        proveedores.add(new ProveedorEntity("123", "Proveedor 1", "Categoría 1", true));
        proveedores.add(new ProveedorEntity("456", "Proveedor 2", "Categoría 2", false));
        when(proveedorRepository.findAll()).thenReturn(proveedores);

        // Act
        List<ProveedorEntity> result = proveedorService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("123", result.get(0).getCodigo());
        assertEquals("Proveedor 1", result.get(0).getNombre());
        assertEquals("Categoría 1", result.get(0).getCategoria());
        assertTrue(result.get(0).getAfectoARetencion());
        assertEquals("456", result.get(1).getCodigo());
        assertEquals("Proveedor 2", result.get(1).getNombre());
        assertEquals("Categoría 2", result.get(1).getCategoria());
        assertFalse(result.get(1).getAfectoARetencion());
    }

    @Test
    void save_NewProveedor_ReturnsCreatedResponse() throws Exception {
        // Arrange
        ProveedorEntity proveedorEntity = new ProveedorEntity("789", "Proveedor 3", "Categoría 3", true);
        when(proveedorRepository.findAll()).thenReturn(new ArrayList<>());
        when(proveedorRepository.save(proveedorEntity)).thenReturn(proveedorEntity);

        // Act
        ResponseEntity<ProveedorEntity> response = proveedorService.save(proveedorEntity);

        // Assert
        assertEquals(ResponseEntity.created(new URI("/proveedor/save789")).body(proveedorEntity), response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("789", response.getBody().getCodigo());
        assertEquals("Proveedor 3", response.getBody().getNombre());
        assertEquals("Categoría 3", response.getBody().getCategoria());
        assertTrue(response.getBody().getAfectoARetencion());
    }

    @Test
    void save_ExistingProveedor_ThrowsRuntimeException() {
        // Arrange
        ProveedorEntity proveedorEntity = new ProveedorEntity("789", "Proveedor 3", "Categoría 3", true);
        List<ProveedorEntity> proveedores = new ArrayList<>();
        proveedores.add(new ProveedorEntity("789", "Proveedor existente", "Categoría existente", false));
        when(proveedorRepository.findAll()).thenReturn(proveedores);

        // Act
        ResponseEntity<ProveedorEntity> response = proveedorService.save(proveedorEntity);

        // Assert
        assertEquals(ResponseEntity.status(400).build(), response);
    }

    @Test
    void save_ExceptionOccurs_ReturnsBadRequestResponse() {
        // Arrange
        ProveedorEntity proveedorEntity = new ProveedorEntity("789", "Proveedor 3", "Categoría 3", true);
        when(proveedorRepository.findAll()).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<ProveedorEntity> response = proveedorService.save(proveedorEntity);

        // Assert
        assertEquals(ResponseEntity.status(400).build(), response);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void findByCodigo_ValidCodigo_ReturnsProveedorEntity() {
        // Arrange
        ProveedorEntity proveedorEntity = new ProveedorEntity("123", "Proveedor 1", "Categoría 1", true);
        when(proveedorRepository.findByCodigo("123")).thenReturn(proveedorEntity);

        // Act
        ProveedorEntity result = proveedorService.findByCodigo("123");

        // Assert
        assertEquals(proveedorEntity, result);
        assertEquals("123", result.getCodigo());
        assertEquals("Proveedor 1", result.getNombre());
        assertEquals("Categoría 1", result.getCategoria());
        assertTrue(result.getAfectoARetencion());
    }

    @Test
    void getProveedorByCodigo_ValidCodigo_ReturnsProveedorEntity() {
        // Arrange
        ProveedorEntity proveedorEntity = new ProveedorEntity("123", "Proveedor 1", "Categoría 1", true);
        when(proveedorRepository.findByCodigo("123")).thenReturn(proveedorEntity);

        // Act
        ProveedorEntity result = proveedorService.getProveedorByCodigo("123");

        // Assert
        assertEquals(proveedorEntity, result);
        assertEquals("123", result.getCodigo());
        assertEquals("Proveedor 1", result.getNombre());
        assertEquals("Categoría 1", result.getCategoria());
        assertTrue(result.getAfectoARetencion());
    }
}
