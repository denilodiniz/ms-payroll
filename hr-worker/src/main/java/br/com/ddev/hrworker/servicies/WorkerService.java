package br.com.ddev.hrworker.servicies;

import br.com.ddev.hrworker.dtos.WorkerDTO;
import br.com.ddev.hrworker.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    public List<WorkerDTO> findAll() {
        return workerRepository.findAll()
                .stream()
                .map(WorkerDTO::new)
                .collect(Collectors.toList());
    }

}
