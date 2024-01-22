package com.example.labxpert.Controller;

import com.example.labxpert.Dtos.ResultDto;
import com.example.labxpert.Exception.MessageErrorException.MessageError;
import com.example.labxpert.Service.IResultService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/results")
public class ResultatController {

    private final IResultService iResultService;

    @GetMapping
    public ResponseEntity<List<ResultDto>> getAll()
    {
        return ResponseEntity.ok(iResultService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<ResultDto> save(@RequestBody @Valid ResultDto resultDto)
    {
        ResultDto resultSaved = iResultService.add(resultDto);
        return new ResponseEntity<>(resultSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ResultDto> update(@PathVariable Long id, @RequestBody @Valid ResultDto resultDto)
    {
        ResultDto resultUpdated = iResultService.update(id, resultDto);
        return new ResponseEntity<>(resultUpdated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDto> getById(@PathVariable Long id)
    {
        try{
            ResultDto result = iResultService.getById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MessageError> delete(@PathVariable Long id)
    {
        MessageError messageError = new MessageError("Sous analyse deleted successfully.");
        iResultService.delete(id);
        return new ResponseEntity<>(messageError, HttpStatus.OK);
    }
}
