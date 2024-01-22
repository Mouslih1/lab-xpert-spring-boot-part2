package com.example.labxpert.Service;

import com.example.labxpert.Dtos.EchontillonDto;
import com.example.labxpert.Dtos.EchontillonMaterialDto;
import com.example.labxpert.Model.EchontillonMaterial;
import com.example.labxpert.Model.Material;
import com.example.labxpert.Model.StockRecuperer;

import java.util.List;

public interface IEchontillonMaterialService {
    EchontillonMaterialDto add(EchontillonMaterialDto echontillonMaterialDto);
    EchontillonMaterialDto update(Long id, EchontillonMaterialDto echontillonMaterialDto);
    void delete(Long id);
    List<EchontillonMaterialDto> getAll();
    EchontillonMaterialDto getById(Long id);
    void saveStockConsomer(Material material, EchontillonMaterial echontillonMaterial);
    StockRecuperer recuperationStockQuantity(Long MaterialId, Long EchontillonMaterialId);
    void RecupererQuantityConsomerAvailableQuantity(Material material, StockRecuperer stockRecuperer);
    void substractionNewQuantityToAvailableQuantity(Material material, EchontillonMaterialDto echontillonMaterial);
    void deleteByEchontillonMaterialId(Long echontillonMaterialId);
    List<EchontillonMaterialDto> getByPriceTotalBefore(double priceTotal);
    List<EchontillonMaterialDto> getByQuantityBefore(int quantity);
    void validationQuantity(int availableQuantity, int quantity);
    void validation(EchontillonMaterialDto echontillonMaterialDto);
}
