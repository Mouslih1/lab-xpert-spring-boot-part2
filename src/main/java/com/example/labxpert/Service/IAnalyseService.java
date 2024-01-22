package com.example.labxpert.Service;

import com.example.labxpert.Dtos.AnalyseDto;
import com.example.labxpert.Dtos.MaterialDto;
import com.example.labxpert.Model.Analyse;
import com.example.labxpert.Model.Enum.TypeAnalyse;

import java.time.LocalDate;
import java.util.List;

public interface IAnalyseService {
    AnalyseDto add(AnalyseDto analyseDto);
    AnalyseDto update(Long id, AnalyseDto analyseDto);
    void delete(Long id);
    List<AnalyseDto> getAll();
    AnalyseDto getById(Long id);
    AnalyseDto getByTypeAnalyse(TypeAnalyse typeAnalyse);
    List<AnalyseDto> getByDateBetween(LocalDate dateStart, LocalDate dateEnd);
    void validation(AnalyseDto analyseDto);

}
