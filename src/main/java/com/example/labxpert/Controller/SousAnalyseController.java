package com.example.labxpert.Controller;

import com.example.labxpert.Dtos.AnalyseDto;
import com.example.labxpert.Dtos.SousAnalyseDto;
import com.example.labxpert.Exception.MessageErrorException.MessageError;
import com.example.labxpert.Service.ISousAnalyseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/sous-analyses")
public class SousAnalyseController {

    private final ISousAnalyseService iSousAnalyseService;

    @GetMapping
    public ResponseEntity<List<SousAnalyseDto>> getAll()
    {
        return ResponseEntity.ok(iSousAnalyseService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<SousAnalyseDto> save(@RequestBody @Valid SousAnalyseDto sousAnalyseDto)
    {
        SousAnalyseDto sousAnalyseSaved = iSousAnalyseService.add(sousAnalyseDto);
        return new ResponseEntity<>(sousAnalyseSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<SousAnalyseDto> update(@PathVariable Long id, @RequestBody @Valid SousAnalyseDto sousAnalyseDto)
    {
        SousAnalyseDto sousAnalyseUpdated = iSousAnalyseService.update(id, sousAnalyseDto);
        return new ResponseEntity<>(sousAnalyseUpdated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SousAnalyseDto> getById(@PathVariable Long id)
    {
        try{
            SousAnalyseDto sousAnalyse = iSousAnalyseService.getById(id);
            return new ResponseEntity<>(sousAnalyse, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MessageError> delete(@PathVariable Long id)
    {
        MessageError messageError = new MessageError("Sous analyse deleted successfully.");
        iSousAnalyseService.delete(id);
        return new ResponseEntity<>(messageError, HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<SousAnalyseDto> getByTitle(@RequestParam String title)
    {
        try{
            SousAnalyseDto sousAnalyse = iSousAnalyseService.getByTitle(title);
            return new ResponseEntity<>(sousAnalyse, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
