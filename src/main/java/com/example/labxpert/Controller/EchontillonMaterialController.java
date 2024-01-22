package com.example.labxpert.Controller;

import com.example.labxpert.Dtos.EchontillonMaterialDto;
import com.example.labxpert.Exception.MessageErrorException.MessageError;
import com.example.labxpert.Service.IEchontillonMaterialService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/echontillons_materials")
public class EchontillonMaterialController {

    private final IEchontillonMaterialService iEchontillonMaterialService;

    @GetMapping
    public ResponseEntity<List<EchontillonMaterialDto>> getAll()
    {
        return ResponseEntity.ok(iEchontillonMaterialService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<EchontillonMaterialDto> save(@RequestBody @Valid EchontillonMaterialDto echontillonMaterialDto)
    {
        EchontillonMaterialDto echontillonMaterialSaved = iEchontillonMaterialService.add(echontillonMaterialDto);
        return new ResponseEntity<>(echontillonMaterialSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<EchontillonMaterialDto> update(@PathVariable Long id, @RequestBody @Valid EchontillonMaterialDto echontillonMaterialDto)
    {
        EchontillonMaterialDto echontillonMaterialUpdated = iEchontillonMaterialService.update(id, echontillonMaterialDto);
        return new ResponseEntity<>(echontillonMaterialUpdated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EchontillonMaterialDto> getById(@PathVariable Long id)
    {
        try{
            EchontillonMaterialDto echontillonMaterial = iEchontillonMaterialService.getById(id);
            return new ResponseEntity<>(echontillonMaterial, HttpStatus.OK);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MessageError> delete(@PathVariable Long id)
    {
        MessageError messageError = new MessageError("Echontillon material deleted successfully.");
        iEchontillonMaterialService.delete(id);
        return new ResponseEntity<>(messageError, HttpStatus.OK);
    }

    @GetMapping("/price-total")
    public ResponseEntity<List<EchontillonMaterialDto>> getByPriceTotalBefore(@RequestParam(name = "price-total") double priceTotal)
    {
        try{
            List<EchontillonMaterialDto> echontillonMaterials = iEchontillonMaterialService.getByPriceTotalBefore(priceTotal);
            return new ResponseEntity<>(echontillonMaterials, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/quantity")
    public ResponseEntity<List<EchontillonMaterialDto>> getByQuantityBefore(@RequestParam int quantity)
    {
        try{
            List<EchontillonMaterialDto> echontillonMaterials = iEchontillonMaterialService.getByQuantityBefore(quantity);
            return new ResponseEntity<>(echontillonMaterials, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
