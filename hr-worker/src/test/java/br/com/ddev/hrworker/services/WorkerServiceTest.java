package br.com.ddev.hrworker.services;

import br.com.ddev.hrworker.dtos.WorkerDTO;
import br.com.ddev.hrworker.entities.Worker;
import br.com.ddev.hrworker.repositories.WorkerRepository;
import br.com.ddev.hrworker.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkerServiceTest {

    @InjectMocks
    private WorkerService workerService;

    @Mock
    private WorkerRepository workerRepository;

    private Worker worker;

    @BeforeEach
    void setUp() {
        worker = new Worker(1L, "Bob", 200.0);
    }

    @Test
    @DisplayName("Find Worker by ID successfully")
    void findWorkerByIdSuccessfully() {
        when(workerRepository.findById(1L)).thenReturn(Optional.of(worker));

        WorkerDTO result = workerService.findById(1L);

        assertEquals(worker.getName(), result.getName());
        assertEquals(worker.getDailyIncome(), result.getDailyIncome());
    }

    @Test
    @DisplayName("Find Worker by ID with non-existing ID")
    void findWorkerByIdNonExistingId() {
        when(workerRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> workerService.findById(2L));
    }

    @Test
    @DisplayName("Find All Workers successfully")
    void findAllWorkersSuccessfully() {
        List<Worker> workerList = new ArrayList<>();
        workerList.add(worker);
        when(workerRepository.findAll()).thenReturn(workerList);

        List<WorkerDTO> result = workerService.findAll();

        assertEquals(1, result.size());
        assertEquals(worker.getName(), result.get(0).getName());
        assertEquals(worker.getDailyIncome(), result.get(0).getDailyIncome());
    }

    @Test
    @DisplayName("Find All Workers with no workers in the repository")
    void findAllWorkersEmptyRepository() {
        when(workerRepository.findAll()).thenReturn(new ArrayList<>());

        List<WorkerDTO> result = workerService.findAll();

        assertTrue(result.isEmpty());
    }

}