package com.example.labxpert.Service.Impl;

import com.example.labxpert.Dtos.MaterialDto;
import com.example.labxpert.Exception.NotFoundException;
import com.example.labxpert.Model.Material;
import com.example.labxpert.Repository.IMaterialRepository;
import com.example.labxpert.Service.IMaterialService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MaterialServiceImpl implements IMaterialService {

    private final IMaterialRepository iMaterialRepository;
    private final ModelMapper modelMapper;

    @Override
    public MaterialDto add(MaterialDto materialDto)
    {
        validation(materialDto);
        Material material = iMaterialRepository.save(modelMapper.map(materialDto, Material.class));
        return modelMapper.map(material, MaterialDto.class);
    }

    @Override
    public MaterialDto update(Long id, MaterialDto materialDto)
    {
        validation(materialDto);
        Material materialExist = iMaterialRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Material not found with this id : "+ id));
        materialExist.setLibelle(materialDto.getLibelle());
        materialExist.setPrice(materialDto.getPrice());
        materialExist.setAvailableQuantity(materialDto.getAvailableQuantity());
        Material materialUpdated = iMaterialRepository.save(materialExist);
        return modelMapper.map(materialUpdated, MaterialDto.class);
    }

    @Override
    public void delete(Long id)
    {
        Material material = iMaterialRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Material not found with this id : " + id));
        material.setDeleted(true);
        iMaterialRepository.save(material);
    }

    @Override
    public List<MaterialDto> getAll()
    {
        List<Material> materials = iMaterialRepository.findByDeletedFalse();
        return materials.stream()
                .map(material -> modelMapper.map(material, MaterialDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MaterialDto getById(Long id)
    {
        Material material = iMaterialRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Material not found with this id : " + id));
        return modelMapper.map(material, MaterialDto.class);
    }

    @Override
    public MaterialDto getByLibelle(String libelle)
    {
        Material material = iMaterialRepository.findByLibelleAndDeletedFalse(libelle).orElseThrow(() -> new NotFoundException("Material not found with this name : " + libelle));
        return modelMapper.map(material, MaterialDto.class);
    }

    @Override
    public List<MaterialDto> getByPriceBefore(double price)
    {
        List<Material> materials = iMaterialRepository.findByPriceBeforeAndDeletedFalse(price);
        return materials
                .stream()
                .map(material -> modelMapper.map(material, MaterialDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialDto> getByAvailableQuantityBefore(int availableQuantity)
    {
        List<Material> materials = iMaterialRepository.findByAvailableQuantityBeforeAndDeletedFalse(availableQuantity);
        return materials
                .stream()
                .map(material -> modelMapper.map(material, MaterialDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void validation(MaterialDto materialDto)
    {
        if (materialDto == null) {
            throw new ValidationException("Les données du produit sont nécessaires.");
        }

        if (StringUtils.isBlank(materialDto.getLibelle())) {
            throw new ValidationException("Le libellé est requise.");
        }

        if (materialDto.getAvailableQuantity() <= 0) {
            throw new ValidationException("La quantité disponible doit être supérieure à 0.");
        }

        if (materialDto.getPrice() <= 0) {
            throw new ValidationException("Le prix doit être supérieur à 0.");
        }
    }
}
