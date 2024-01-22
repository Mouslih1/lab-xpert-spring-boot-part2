package com.example.labxpert.Controller;

import com.example.labxpert.Dtos.PatientDto;
import com.example.labxpert.Exception.MessageErrorException.MessageError;
import com.example.labxpert.Service.IPatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final IPatientService iPatientService;

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAll()
    {
        return ResponseEntity.ok(iPatientService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<PatientDto> save(@RequestBody @Valid PatientDto patientDto)
    {
        PatientDto patientSaved = iPatientService.add(patientDto);
        return new ResponseEntity<>(patientSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<PatientDto> update(@RequestBody @Valid PatientDto patientDto, @PathVariable Long id)
    {
        PatientDto patientUpdated = iPatientService.update(id, patientDto);
        return new ResponseEntity<>(patientUpdated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getById(@PathVariable Long id)
    {
        try{
            PatientDto patient = iPatientService.getById(id);
            return new ResponseEntity<>(patient, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/patient")
    public ResponseEntity<PatientDto> getByName(@RequestParam String name)
    {
        try{
            PatientDto patient = iPatientService.getByName(name);
            return  new ResponseEntity<>(patient, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MessageError> delete(@PathVariable Long id)
    {
        MessageError messageError = new MessageError("Patient deleted successfully.");
        iPatientService.delete(id);
        return new ResponseEntity<>(messageError, HttpStatus.OK);
    }
}
