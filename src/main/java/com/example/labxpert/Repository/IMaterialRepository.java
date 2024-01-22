package com.example.labxpert.Repository;

import com.example.labxpert.Model.Material;
import com.example.labxpert.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IMaterialRepository extends JpaRepository<Material,Long>{
    List<Material> findByDeletedFalse();
    Optional<Material> findByIdAndDeletedFalse(Long id);
    Optional<Material> findByNomAndDeletedFalse(String name);

}
