package com.example.labxpert.Service;

import com.example.labxpert.Dtos.AnalyseDto;
import com.example.labxpert.Dtos.PatientDto;
import com.example.labxpert.Dtos.SousAnalyseDto;
import com.example.labxpert.Dtos.UserDto;
import com.example.labxpert.Model.SousAnalyse;

import java.util.List;

public interface ISousAnalyseService {
    SousAnalyseDto add(SousAnalyseDto sousAnalyseDto);
    SousAnalyseDto update(Long id, SousAnalyseDto sousAnalyseDto);
    void delete(Long id);
    List<SousAnalyseDto> getAll();
    SousAnalyseDto getById(Long id);
    SousAnalyseDto getByTitle(String title);
    void validation(SousAnalyseDto sousAnalyseDto);

}
