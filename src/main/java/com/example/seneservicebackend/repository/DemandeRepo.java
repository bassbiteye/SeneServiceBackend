package com.example.seneservicebackend.repository;

import com.example.seneservicebackend.entity.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeRepo extends JpaRepository<Demande, Integer> {
}
