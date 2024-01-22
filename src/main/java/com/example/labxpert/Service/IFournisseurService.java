package com.example.labxpert.Service;


import com.example.labxpert.Dtos.EchontillonDto;
import com.example.labxpert.Dtos.FournisseurDto;
import com.example.labxpert.Dtos.MaterialDto;
import com.example.labxpert.Model.Fournisseur;

import java.util.List;

public interface IFournisseurService {
    FournisseurDto add(FournisseurDto fournisseurDto);
    FournisseurDto update(Long id, FournisseurDto fournisseurDto);
    void delete(Long id);
    List<FournisseurDto> getAll();
    FournisseurDto getById(Long id);
    FournisseurDto getByName(String name);

    void validation(FournisseurDto fournisseurDto);

}
