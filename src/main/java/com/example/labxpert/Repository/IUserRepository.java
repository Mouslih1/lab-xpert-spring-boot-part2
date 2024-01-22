package com.example.labxpert.Repository;

import com.example.labxpert.Model.SousAnalyse;
import com.example.labxpert.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    List<User> findByDeletedFalse();
    Optional<User> findByIdAndDeletedFalse(Long id);
    Optional<User> findByNomAndDeletedFalse(String name);
    Optional<User> findByEmailAndDeletedFalse(String email);
}
