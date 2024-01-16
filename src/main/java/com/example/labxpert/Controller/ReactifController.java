package com.example.labxpert.Controller;

import com.example.labxpert.Model.Fournisseur;
import com.example.labxpert.Model.Reactif;
import com.example.labxpert.Service.IReactifService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/reactif")
public class ReactifController {

    private IReactifService iReactifService;

    @GetMapping("/getAll")
    public List<Reactif> list() {
        return iReactifService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reactif> get(@PathVariable Long id) {

        try {
            Reactif reactif = iReactifService.getById(id);
            return new ResponseEntity<Reactif>(reactif, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Reactif>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Reactif> add(@RequestBody Reactif reactif) {
        Reactif reactifSaved = iReactifService.add(reactif);
        return new ResponseEntity<Reactif>(reactifSaved, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Reactif> update(@RequestBody Reactif reactif) {
        Reactif reactifUpdated = iReactifService.update(reactif);
        return new ResponseEntity<>(reactifUpdated, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        iReactifService.delete(id);
        return new ResponseEntity<>("Reactif deleted", HttpStatus.OK);
    }


}
