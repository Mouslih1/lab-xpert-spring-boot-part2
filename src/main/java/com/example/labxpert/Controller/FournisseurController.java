package com.example.labxpert.Controller;

import com.example.labxpert.Model.Fournisseur;
import com.example.labxpert.Service.IFournisseurService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fournisseur")
public class FournisseurController {


    private IFournisseurService fournisseurService;

    @GetMapping("/getAll")
    public List<Fournisseur> list() {
        return fournisseurService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Fournisseur> add(@RequestBody Fournisseur fournisseur) {
        Fournisseur fournisseurSaved = fournisseurService.add(fournisseur);
        return new ResponseEntity<Fournisseur>(fournisseur, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> get(@PathVariable Long id) {

        try {
            Fournisseur fournisseur = fournisseurService.getById(id);
            return new ResponseEntity<Fournisseur>(fournisseur, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Fournisseur>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Fournisseur> update(@RequestBody Fournisseur fournisseur) {
        Fournisseur fourniseurUpdated = fournisseurService.update(fournisseur);
        return new ResponseEntity<>(fourniseurUpdated, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        fournisseurService.delete(id);
        return new ResponseEntity<>("Fournisseur deleted", HttpStatus.OK);
    }

}
