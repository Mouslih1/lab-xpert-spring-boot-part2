package com.example.labxpert.Repository;

import com.example.labxpert.Model.Analyse;
import com.example.labxpert.Model.Enum.TypeAnalyse;
import com.example.labxpert.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IAnalyseRepository extends JpaRepository<Analyse,Long> {
    List<Analyse> findByDeletedFalse();
    Optional<Analyse> findByIdAndDeletedFalse(Long id);
    Optional<Analyse> findByTypeAnalyseAndDeletedFalse(TypeAnalyse typeAnalyse);
    List<Analyse> findByDateDebutBetween(LocalDate dateStart, LocalDate dateEnd);
}
