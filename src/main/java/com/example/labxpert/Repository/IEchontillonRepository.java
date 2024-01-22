package com.example.labxpert.Repository;

import com.example.labxpert.Model.Echontillon;
import com.example.labxpert.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEchontillonRepository extends JpaRepository<Echontillon, Long> {
    List<Echontillon> findByDeletedFalse();
    Optional<Echontillon> findByIdAndDeletedFalse(Long id);
    Optional<Echontillon> findByCodeEchontillonAndDeletedFalse(String codeEchontillon);
}
