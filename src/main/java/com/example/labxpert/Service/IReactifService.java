package com.example.labxpert.Service;

import com.example.labxpert.Dtos.MaterialDto;
import com.example.labxpert.Dtos.PatientDto;
import com.example.labxpert.Dtos.ReactifDto;
import com.example.labxpert.Model.Reactif;

import java.util.List;

public interface IReactifService {
    ReactifDto add(ReactifDto reactifDto);
    ReactifDto update(Long id, ReactifDto reactifDto);
    void delete(Long id);
    List<ReactifDto> getAll();
    ReactifDto getById(Long id);
    ReactifDto getByName(String name);
    List<ReactifDto> getByQuantityStockBefore(int quantityStock);
    void validation(ReactifDto reactifDto);
}
