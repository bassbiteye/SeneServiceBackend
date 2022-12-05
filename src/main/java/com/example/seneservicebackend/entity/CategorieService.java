package com.example.seneservicebackend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CategorieService implements Serializable {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String titre;

    private String image;

    @OneToMany(mappedBy = "categorieService", fetch = FetchType.EAGER)
    private List<Service> services = new ArrayList<Service>();

    public CategorieService() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
