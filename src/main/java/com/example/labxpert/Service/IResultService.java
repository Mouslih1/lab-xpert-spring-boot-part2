package com.example.labxpert.Service;

import com.example.labxpert.Dtos.ResultDto;
import com.example.labxpert.Model.SousAnalyse;

import java.util.List;

public interface IResultService {
    ResultDto add(ResultDto resultDto);
    void delete(Long id);
    ResultDto getById(Long id);
    List<ResultDto> getAll();
    ResultDto update(Long id, ResultDto resultDto);
    void changeStatusResultOfSousAnalyse(ResultDto resultDto, SousAnalyse sousAnalyse);
    void changeStatusResultOfAnalyse(SousAnalyse sousAnalyse);
    void validation(ResultDto resultDto);

}
