package com.example.labxpert.Service;

import com.example.labxpert.Dtos.PatientDto;
import com.example.labxpert.Dtos.PlanificationDto;
import com.example.labxpert.Model.Planification;

import java.util.List;

public interface IPlanificationService {
    PlanificationDto add(PlanificationDto planificationDto);
    PlanificationDto update(Long id, PlanificationDto planificationDto);
    void delete(Long id);
    List<PlanificationDto> getAll();
    PlanificationDto getById(Long id);
    void validation(PlanificationDto planificationDto);

}
