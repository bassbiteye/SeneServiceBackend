package com.example.seneservicebackend.repository;

import com.example.seneservicebackend.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ServiceRepo extends JpaRepository<Service, Integer> {
}
