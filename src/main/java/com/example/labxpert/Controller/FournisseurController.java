package com.example.labxpert.Controller;

import com.example.labxpert.Model.Fournisseur;
import com.example.labxpert.Service.IFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/Fournisseur")
public class FournisseurController {

@Autowired
    private IFournisseurService fournisseurService;

@GetMapping("/getAll")
    public List<Fournisseur> list(){
    return  fournisseurService.getAll();
}

@PostMapping("/add")
    public String add(@RequestBody Fournisseur fournisseur)
{
    fournisseurService.add(fournisseur);
    return "Add successfully";
}

@GetMapping("/{id}")
public ResponseEntity<Fournisseur> get(@PathVariable Long id)
{
    try{
        Fournisseur fournisseur = fournisseurService.getById(id);
        return new ResponseEntity<Fournisseur>(fournisseur, HttpStatus.OK);
    }catch(NoSuchElementException e){
        return new ResponseEntity<Fournisseur>(HttpStatus.NOT_FOUND);
    }
}


@PutMapping("/{id}")
    public ResponseEntity<Fournisseur> update(@RequestBody Fournisseur fournisseur,@PathVariable Long id)
{
    try {
        Fournisseur existFournisseur = fournisseurService.getById(id);
        fournisseurService.add(fournisseur);
        return new ResponseEntity<Fournisseur>(fournisseur, HttpStatus.OK);

    }catch (NoSuchElementException e)
    {
        return new ResponseEntity<Fournisseur>(HttpStatus.NOT_FOUND);
    }
}


    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id)
    {
        fournisseurService.delete(id);
        return "Delete successfully";
    }

}
