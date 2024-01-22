package com.example.labxpert.Service;

import com.example.labxpert.Dtos.FournisseurDto;
import com.example.labxpert.Dtos.MaterialDto;

import java.util.List;

public interface IMaterialService  {

    MaterialDto add(MaterialDto materialDto);
    MaterialDto update(Long id, MaterialDto materialDto);
    void delete(Long id);
    List<MaterialDto> getAll();
    MaterialDto getById(Long id);
    MaterialDto getByName(String name);
    void validation(MaterialDto materialDto);
}
