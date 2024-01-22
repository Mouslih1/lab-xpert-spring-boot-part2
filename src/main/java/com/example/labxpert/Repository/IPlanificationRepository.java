package com.example.labxpert.Repository;

import com.example.labxpert.Model.Patient;
import com.example.labxpert.Model.Planification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPlanificationRepository extends JpaRepository<Planification,Long> {
    List<Planification> findByDeletedFalse();
    Optional<Planification> findByIdAndDeletedFalse(Long id);
}
