package br.com.ddev.hrworker.resources;

import br.com.ddev.hrworker.dtos.WorkerDTO;
import br.com.ddev.hrworker.servicies.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/workes")
public class WorkerResource {

    @Autowired
    private WorkerService workerService;

    @GetMapping
    public ResponseEntity<List<WorkerDTO>> findAll() {
        return ResponseEntity.ok().body(workerService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<WorkerDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(workerService.findById(id));
    }

}
