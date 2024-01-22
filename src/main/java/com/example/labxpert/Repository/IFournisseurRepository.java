package com.example.labxpert.Repository;

import com.example.labxpert.Model.Fournisseur;
import com.example.labxpert.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFournisseurRepository extends JpaRepository<Fournisseur,Long> {
    List<Fournisseur> findByDeletedFalse();
    Optional<Fournisseur> findByIdAndDeletedFalse(Long id);
    Optional<Fournisseur> findByNomAndDeletedFalse(String name);
}
