package br.com.ddev.hrworker.resources;

import br.com.ddev.hrworker.dtos.WorkerDTO;
import br.com.ddev.hrworker.entities.Worker;
import br.com.ddev.hrworker.services.WorkerService;
import br.com.ddev.hrworker.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkerResourceTest {

    @InjectMocks
    WorkerResource workerResource;

    @Mock
    WorkerService workerService;

    private WorkerDTO workerDTO;

    @BeforeEach
    void setUp() {
        workerDTO = new WorkerDTO(
                new Worker(1L, "Bob", 200.0)
        );
    }

    @Test
    @DisplayName("Find all workers successfully")
    void findAllWorkersSuccessfully() {
        List<WorkerDTO> workerDTOList = new ArrayList<>();
        workerDTOList.add(workerDTO);
        when(workerService.findAll()).thenReturn(workerDTOList);

        ResponseEntity<List<WorkerDTO>> result = workerResource.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(workerDTOList, result.getBody());
        assertEquals(workerDTO.getClass(), result.getBody().get(0).getClass());
    }

    @Test
    @DisplayName("Find all workers with empty list")
    void findAllWorkersWithEmptyList() {
        List<WorkerDTO> workerDTOList = new ArrayList<>();

        when(workerService.findAll()).thenReturn(workerDTOList);

        ResponseEntity<List<WorkerDTO>> result = workerResource.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(result.getBody().isEmpty(), true);
    }

    @Test
    @DisplayName("Find worker by id successfully")
    void findWorkerByIdSuccessfully() {
        when(workerService.findById(Mockito.anyLong())).thenReturn(workerDTO);

        ResponseEntity<WorkerDTO> result = workerResource.findById(Mockito.anyLong());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(workerDTO, result.getBody());
        assertEquals(workerDTO.getClass(), result.getBody().getClass());
    }

    @Test
    @DisplayName("Find worker by id when worker does not exist")
    void findWorkerByIdWhenNotExist() {
        // Configurar o comportamento do serviço para lançar a exceção quando um trabalhador não existe
        when(workerService.findById(workerDTO.getId()))
                .thenThrow(new ResourceNotFoundException("Worker with id " + workerDTO.getId() + " does not exist."));

        // Chame o endpoint
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
                () -> workerResource.findById(workerDTO.getId()));

        assertEquals("Worker with id " + workerDTO.getId() + " does not exist.", resourceNotFoundException.getMessage());
    }

}