package br.com.ddev.hrworker.dtos;

import br.com.ddev.hrworker.entities.Worker;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class WorkerDTO extends RepresentationModel<WorkerDTO> implements Serializable {

    private Long id;
    private String name;
    private Double  dailyIncome;

    public WorkerDTO(Worker obj) {
        BeanUtils.copyProperties(obj, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(Double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

}
