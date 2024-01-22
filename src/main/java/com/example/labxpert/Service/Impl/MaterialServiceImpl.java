package com.example.labxpert.Service.Impl;

import com.example.labxpert.Dtos.FournisseurDto;
import com.example.labxpert.Dtos.MaterialDto;
import com.example.labxpert.Dtos.PatientDto;
import com.example.labxpert.Exception.NotFoundException;
import com.example.labxpert.Model.Fournisseur;
import com.example.labxpert.Model.Material;
import com.example.labxpert.Model.Patient;
import com.example.labxpert.Repository.IMaterialRepository;
import com.example.labxpert.Repository.IPatientRepository;
import com.example.labxpert.Service.IMaterialService;
import io.micrometer.core.instrument.util.StringUtils;
import org.modelmapper.ModelMapper;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialServiceImpl implements IMaterialService {

    private IMaterialRepository iMaterialRepository;
    private  ModelMapper modelMapper;


    @Override
    public MaterialDto add(MaterialDto materialDto) {
        validation(materialDto);
        Material materialEntity = iMaterialRepository.save(modelMapper.map(materialDto, Material.class));
        return modelMapper.map(materialEntity, MaterialDto.class);
    }

    @Override
    public MaterialDto update(Long id, MaterialDto materialDto) {
        validation(materialDto);
        Material materialExist = iMaterialRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Material not found with this id :" + id));
        materialExist.setLibelle(materialDto.getLibelle());
        materialExist.setPrice(materialDto.getPrice());
        materialExist.setAvailableQuantity(materialDto.getAvailableQuantity());
        materialExist.setDeleted(materialDto.getDeleted());
        Material materialUpdated = iMaterialRepository.save(materialExist);
        return modelMapper.map(materialUpdated, MaterialDto.class);
    }

    @Override
    public void delete(Long id) {
        Material material = iMaterialRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Material not found with this id : " + id));
        material.setDeleted(true);
        iMaterialRepository.save(material);

    }

    @Override
    public List<MaterialDto> getAll() {
        List<Material> materials = iMaterialRepository.findByDeletedFalse();
        return materials
                .stream()
                .map(material -> modelMapper.map(material, MaterialDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MaterialDto getById(Long id) {
        Material material = iMaterialRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Material not found with id : " + id));
        return modelMapper.map(material, MaterialDto.class);
    }

    @Override
    public MaterialDto getByName(String name) {
        Material material = iMaterialRepository.findByNomAndDeletedFalse(name).orElseThrow(() -> new NotFoundException("Material not found with name :" + name));
        return modelMapper.map(material, MaterialDto.class);
    }

    @Override
    public void validation(MaterialDto materialDto) {

        if (materialDto == null) {
            throw new ValidationException("Les données du patient sont nécessaires.");
        }

        if (StringUtils.isBlank(materialDto.getLibelle())) {
            throw new ValidationException("Le nom est requise.");
        }

        if (StringUtils.isBlank(String.valueOf(materialDto.getPrice()))) {
            throw new ValidationException("Le prix est requise.");
        }

        if (StringUtils.isBlank(String.valueOf(materialDto.getAvailableQuantity()))) {
            throw new ValidationException("La quantité est requise.");
        }

    }
}
