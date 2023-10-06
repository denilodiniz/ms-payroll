package br.com.ddev.hrworker.resources;

import br.com.ddev.hrworker.dtos.WorkerDTO;
import br.com.ddev.hrworker.resources.exceptions.StandardError;
import br.com.ddev.hrworker.services.WorkerService;
import br.com.ddev.hrworker.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/workers")
public class WorkerResource {

    @Autowired
    private WorkerService workerService;

    @Operation(description = "Returns a list of workers.")
    @ApiResponse(responseCode = "200", description = "If there is no worker, returns an empty worker list")
    @GetMapping
    public ResponseEntity<List<WorkerDTO>> findAll() {
        return ResponseEntity.ok().body(workerService.findAll()
                .stream()
                .map(x -> x.add(linkTo(methodOn(WorkerResource.class).findById(x.getId())).withSelfRel()))
                .collect(Collectors.toList())
        );
    }

    @Operation(description = "Searches for a worker by ID and returns the worker data in the request body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns worker successfully."),
            @ApiResponse(responseCode = "404", description = "Worker not found.", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<WorkerDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(workerService.findById(id)
                .add(linkTo(methodOn(WorkerResource.class).findById(id)).withSelfRel())
        );
    }

}
