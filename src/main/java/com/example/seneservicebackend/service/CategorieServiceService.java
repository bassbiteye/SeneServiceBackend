package com.example.seneservicebackend.service;


import com.example.seneservicebackend.entity.CategorieService;
import com.example.seneservicebackend.repository.CategorieServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategorieServiceService {

    public final CategorieServiceRepo categorieServiceRepo;

    @Autowired
    public CategorieServiceService(CategorieServiceRepo categorieServiceRepo) {
        this.categorieServiceRepo = categorieServiceRepo;
    }

    public CategorieService AddCategorieService(CategorieService categorieService){


        return categorieServiceRepo.save(categorieService);
    }

    public List<CategorieService> FindAllCategorieService(){
        return categorieServiceRepo.findAll();
    }
}
