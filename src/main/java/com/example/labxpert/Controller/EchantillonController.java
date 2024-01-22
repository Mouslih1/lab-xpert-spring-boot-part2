package com.example.labxpert.Controller;

import com.example.labxpert.Dtos.EchontillonDto;
import com.example.labxpert.Exception.MessageErrorException.MessageError;
import com.example.labxpert.Service.IEchontillonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/echontillons")
public class EchantillonController {

    private final IEchontillonService iEchontillonService;

    @GetMapping
    public ResponseEntity<List<EchontillonDto>> getALl()
    {
        return ResponseEntity.ok(iEchontillonService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<EchontillonDto> save(@RequestBody @Valid EchontillonDto echontillonDto)
    {
        EchontillonDto echontillonSaved = iEchontillonService.add(echontillonDto);
        return new ResponseEntity<>(echontillonSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<EchontillonDto> update(@PathVariable Long id, @RequestBody @Valid EchontillonDto echontillonDto)
    {
        EchontillonDto echontillonUpdated = iEchontillonService.update(id, echontillonDto);
        return new ResponseEntity<>(echontillonUpdated,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EchontillonDto> getById(@PathVariable Long id)
    {
        try{
            EchontillonDto echontillon = iEchontillonService.getById(id);
            return new ResponseEntity<>(echontillon, HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MessageError> delete(@PathVariable Long id)
    {
        MessageError messageError = new MessageError("Echontillon deleted successfully.");
        iEchontillonService.delete(id);
        return new ResponseEntity<>(messageError, HttpStatus.OK);
    }

    @GetMapping("/code-echontillon")
    public ResponseEntity<EchontillonDto> getByCodeEchontillon(@RequestParam String codeEchontillon)
    {
        try{
            EchontillonDto echontillon = iEchontillonService.getByCodeEchontillon(codeEchontillon);
            return new ResponseEntity<>(echontillon, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
