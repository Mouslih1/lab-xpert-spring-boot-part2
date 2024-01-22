package com.example.labxpert.Service.Impl;

import com.example.labxpert.Dtos.EchontillonDto;
import com.example.labxpert.Dtos.EchontillonMaterialDto;
import com.example.labxpert.Dtos.MaterialDto;
import com.example.labxpert.Exception.NotFoundException;
import com.example.labxpert.Model.Echontillon;
import com.example.labxpert.Model.EchontillonMaterial;
import com.example.labxpert.Model.Material;
import com.example.labxpert.Model.StockRecuperer;
import com.example.labxpert.Repository.IEchontillonMaterialRepository;
import com.example.labxpert.Repository.IEchontillonRepository;
import com.example.labxpert.Repository.IMaterialRepository;
import com.example.labxpert.Repository.IStockRecuperationRepository;
import com.example.labxpert.Service.IEchontillonMaterialService;
import com.example.labxpert.Service.IEchontillonService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class EchontillonMaterialServiceImpl implements IEchontillonMaterialService {

    private final IEchontillonMaterialRepository iEchontillonMaterialRepository;
    private final IMaterialRepository iMaterialRepository;
    private final IEchontillonRepository iEchontillonRepository;
    private final IStockRecuperationRepository iStockRecuperationRepository;
    private final ModelMapper modelMapper;

    @Override
    public EchontillonMaterialDto add(EchontillonMaterialDto echontillonMaterialDto)
    {
        validation(echontillonMaterialDto);

        Material materialExist = iMaterialRepository.findByIdAndDeletedFalse(echontillonMaterialDto.getMaterial().getId()).orElseThrow(() -> new NotFoundException("Material not found with this id : " + echontillonMaterialDto.getMaterial().getId()));
        Echontillon echontillonExist = iEchontillonRepository.findByIdAndDeletedFalse(echontillonMaterialDto.getEchontillon().getId()).orElseThrow(() -> new NotFoundException("Echontillon not found with this id : " + echontillonMaterialDto.getEchontillon().getId()));
        echontillonMaterialDto.setEchontillon(modelMapper.map(echontillonExist, EchontillonDto.class));
        echontillonMaterialDto.setMaterial(modelMapper.map(materialExist, MaterialDto.class));

        validationQuantity(materialExist.getAvailableQuantity(), echontillonMaterialDto.getQuantity());

        //TODO: CLEAN CODE WITH COLLECT THIS CODE IN ONE METHODE
        materialExist.setAvailableQuantity(materialExist.getAvailableQuantity() - echontillonMaterialDto.getQuantity());
        iMaterialRepository.save(materialExist);

        echontillonMaterialDto.setPriceTotal(materialExist.getPrice()*echontillonMaterialDto.getQuantity());
        EchontillonMaterial echontillonMaterial = iEchontillonMaterialRepository.save(modelMapper.map(echontillonMaterialDto, EchontillonMaterial.class));

        //TODO: THIS METHODE FOR SAVE QUANTITY WITH MATERIAL ID & ECHONTILLON MATERIAL ID
        saveStockConsomer(materialExist, echontillonMaterial);

        return modelMapper.map(echontillonMaterial, EchontillonMaterialDto.class);
    }

    @Override
    public EchontillonMaterialDto update(Long id,EchontillonMaterialDto echontillonMaterialDto)
    {
        validation(echontillonMaterialDto);

        EchontillonMaterial echontillonMaterialExist = iEchontillonMaterialRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Echontillon and Material not found with this id : " + id));
        Material materialExist = iMaterialRepository.findByIdAndDeletedFalse(echontillonMaterialDto.getMaterial().getId()).orElseThrow(() -> new NotFoundException("Material not found with this id : " + echontillonMaterialDto.getMaterial().getId()));
        Echontillon echontillonExist = iEchontillonRepository.findByIdAndDeletedFalse(echontillonMaterialDto.getEchontillon().getId()).orElseThrow(() -> new NotFoundException("Echontillon not found with this id : " + echontillonMaterialDto.getEchontillon().getId()));

        echontillonMaterialExist.setEchontillon(modelMapper.map(echontillonExist, Echontillon.class));
        echontillonMaterialExist.setMaterial(modelMapper.map(materialExist, Material.class));

        StockRecuperer stockRecuperer = recuperationStockQuantity(materialExist.getId(), echontillonMaterialExist.getId());

        //TODO: THIS FOR RECUPERATION QUANTITY CONSOMER TO MATERIAL
        RecupererQuantityConsomerAvailableQuantity(materialExist, stockRecuperer);

        validationQuantity(materialExist.getAvailableQuantity(), echontillonMaterialDto.getQuantity());

        echontillonMaterialExist.setQuantity(echontillonMaterialDto.getQuantity());
        echontillonMaterialExist.setPriceTotal(echontillonMaterialDto.getQuantity() * materialExist.getPrice());

        //TODO: THIS FOR soustracter THE NEW QUANTITY UPDATER
        substractionNewQuantityToAvailableQuantity(materialExist, echontillonMaterialDto);

        EchontillonMaterial echontillonMaterialSaved = iEchontillonMaterialRepository.save(echontillonMaterialExist);

        //TODO: SAVE THE CHANGED INFORMATIONS QUANTITY CHANGED
        StockRecuperer stockRecupererUpdated = recuperationStockQuantity(materialExist.getId(),echontillonMaterialSaved.getId());
        stockRecupererUpdated.setQuantityConsomer(echontillonMaterialSaved.getQuantity());
        iStockRecuperationRepository.save(stockRecupererUpdated);

        return modelMapper.map(echontillonMaterialSaved, EchontillonMaterialDto.class);
    }

    @Override
    public void delete(Long id)
    {
        EchontillonMaterial echontillonMaterial = iEchontillonMaterialRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Echontillon material not found with this id :" + id));
        echontillonMaterial.setDeleted(true);

        //TODO: DELETE TABLE STOCK RECUPERATION WITH ECHONTILLON MATERIAL ID
        deleteByEchontillonMaterialId(echontillonMaterial.getId());

        iEchontillonMaterialRepository.save(echontillonMaterial);
    }

    @Override
    public List<EchontillonMaterialDto> getAll()
    {
        List<EchontillonMaterial> echontillonMaterials = iEchontillonMaterialRepository.findByDeletedFalse();
        return echontillonMaterials
                .stream()
                .map(echontillonMaterial -> modelMapper.map(echontillonMaterial, EchontillonMaterialDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EchontillonMaterialDto getById(Long id)
    {
        EchontillonMaterial echontillonMaterial = iEchontillonMaterialRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new NotFoundException("Echontillon material not found with this id :" + id));
        return modelMapper.map(echontillonMaterial, EchontillonMaterialDto.class);
    }

    @Override
    public void saveStockConsomer(Material material, EchontillonMaterial echontillonMaterial)
    {
        StockRecuperer stockRecuperer = new StockRecuperer();
        stockRecuperer.setMaterialId(material.getId());
        stockRecuperer.setEchontillonMaterialId(echontillonMaterial.getId());
        stockRecuperer.setQuantityConsomer(echontillonMaterial.getQuantity());
        iStockRecuperationRepository.save(stockRecuperer);
    }

    @Override
    public StockRecuperer recuperationStockQuantity(Long materialId, Long echontillonMaterialId)
    {
        return iStockRecuperationRepository.findByMaterialIdAndEchontillonMaterialId(materialId,echontillonMaterialId).orElseThrow(() -> new NotFoundException("Stock recuperation not found with material id : " + materialId + " and echontillon material id : " + echontillonMaterialId));
    }

    @Override
    public void RecupererQuantityConsomerAvailableQuantity(Material material, StockRecuperer stockRecuperer)
    {
        material.setAvailableQuantity(material.getAvailableQuantity() + stockRecuperer.getQuantityConsomer());
        iMaterialRepository.save(material);
    }

    @Override
    public void substractionNewQuantityToAvailableQuantity(Material material, EchontillonMaterialDto echontillonMaterial)
    {
        material.setAvailableQuantity(material.getAvailableQuantity() - echontillonMaterial.getQuantity());
        iMaterialRepository.save(material);
    }

    @Override
    public void deleteByEchontillonMaterialId(Long echontillonMaterialId)
    {
        iStockRecuperationRepository.deleteByEchontillonMaterialId(echontillonMaterialId);
    }

    @Override
    public List<EchontillonMaterialDto> getByPriceTotalBefore(double priceTotal)
    {
        List<EchontillonMaterial> echontillonMaterials = iEchontillonMaterialRepository.findByPriceTotalBeforeAndDeletedFalse(priceTotal);
        return echontillonMaterials
                .stream()
                .map(echontillonMaterial -> modelMapper.map(echontillonMaterial,EchontillonMaterialDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EchontillonMaterialDto> getByQuantityBefore(int quantity)
    {
        List<EchontillonMaterial> echontillonMaterials = iEchontillonMaterialRepository.findByQuantityBeforeAndDeletedFalse(quantity);
        return echontillonMaterials
                .stream()
                .map(echontillonMaterial -> modelMapper.map(echontillonMaterial, EchontillonMaterialDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void validationQuantity(int availableQuantity, int quantity)
    {
        if(availableQuantity < quantity)
        {
            throw new ValidationException("Ce material est n'est pas disponible dans le stock.");
        }
    }

    @Override
    public void validation(EchontillonMaterialDto echontillonMaterialDto)
    {
        if (echontillonMaterialDto == null) {
            throw new ValidationException("Les données de l'échantillon sont nécessaires.");
        }

        if (echontillonMaterialDto.getEchontillon().getId() == null) {
            throw new ValidationException("Echantillon ID ne peut pas être null.");
        }

        if (echontillonMaterialDto.getMaterial().getId() == null) {
            throw new ValidationException("Material ID ne peut pas être null.");
        }

        if (echontillonMaterialDto.getQuantity() < 0) {
            throw new ValidationException("La quantité doit être supérieure ou égale à zéro.");
        }

        if (echontillonMaterialDto.getPriceTotal() < 0) {
            throw new ValidationException("Le prix total doit être supérieur ou égal à zéro.");
        }

    }
}
