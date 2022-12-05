package com.example.seneservicebackend.controller;


import com.example.seneservicebackend.entity.CategorieService;
import com.example.seneservicebackend.service.CategorieServiceService;
import com.example.seneservicebackend.util.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/categorieS")
public class CategorieServiceController {

    public final CategorieServiceService categorieServiceService;

    public CategorieServiceController(CategorieServiceService categorieServiceService) {
        this.categorieServiceService = categorieServiceService;
    }

    @GetMapping("/AllCat")
    public ResponseEntity<List<CategorieService>> getAllCategorieService(){

        List<CategorieService> categorieServices = categorieServiceService.FindAllCategorieService();

        return new ResponseEntity<>(categorieServices, HttpStatus.OK);

    }

    @PostMapping("/addCat")
    public ResponseEntity<CategorieService> addCategorieService(CategorieService categorieService, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String name= FileUploadUtil.saveFile(fileName, multipartFile, "templates/files-categorie");
        categorieService.setImage(name);
        CategorieService newcat = categorieServiceService.AddCategorieService(categorieService);

        return new ResponseEntity<>(newcat, HttpStatus.CREATED);
    }

}
