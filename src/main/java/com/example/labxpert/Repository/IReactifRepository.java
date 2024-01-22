package com.example.labxpert.Repository;

import com.example.labxpert.Model.Planification;
import com.example.labxpert.Model.Reactif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReactifRepository extends JpaRepository<Reactif,Long> {
    List<Reactif> findByDeletedFalse();
    Optional<Reactif> findByIdAndDeletedFalse(Long id);
    Optional<Reactif> findByNomAndDeletedFalse(String name);
    List<Reactif> findByQuantityStockBeforeAndDeletedFalse(int quantityStock);
}
