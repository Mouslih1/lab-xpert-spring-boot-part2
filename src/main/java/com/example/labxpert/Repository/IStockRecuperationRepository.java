package com.example.labxpert.Repository;

import com.example.labxpert.Model.StockRecuperer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStockRecuperationRepository extends JpaRepository<StockRecuperer, Long> {
    Optional<StockRecuperer> findByMaterialIdAndEchontillonMaterialId(Long materialId, Long echontillonMaterialId);
    void deleteByEchontillonMaterialId(Long echontillonMaterialId);
}
