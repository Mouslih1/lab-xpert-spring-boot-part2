package com.example.labxpert.Controller;

import com.example.labxpert.Model.Patient;
import com.example.labxpert.Service.IPatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    private IPatientService iPatientService;

    @GetMapping
    public List<Patient> getAll()
    {
        return iPatientService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Patient> save(@RequestBody Patient patient)
    {
        Patient patientSaved = iPatientService.add(patient);
        return new ResponseEntity<>(patientSaved, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Patient> update(@RequestBody Patient patient)
    {
        Patient patientUpdated = iPatientService.update(patient);
        return new ResponseEntity<>(patientUpdated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getById(@PathVariable Long id)
    {
        try{
            Patient patient = iPatientService.getById(id);
            return new ResponseEntity<>(patient, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id)
    {
        iPatientService.delete(id);
        return new ResponseEntity<>("Patient deleted successfully", HttpStatus.OK);
    }

}
