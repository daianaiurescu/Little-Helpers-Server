package com.little.helpers.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "category")
    private String category;

    @Column(name = "sold_by")
    private String sold_by;

    @Column(name = "price")
    private float price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "description")
    private String description;

    @Column(name = "photo")
    private String photo;

    public Product(String t, String c, String sb, float p, int s, String d, String ph){
        title = t;
        category = c;
        sold_by = sb;
        price = p;
        stock = s;
        description = d;
        photo = ph;
    }

    public int getId(){ return id; }
    public void setId(int i){ id=i; }

    public String getTitle(){ return title; }
    public void setTitle(String t) { title = t;}

    public String getCategory(){ return category; }
    public void setCategory(String c) { category = c; }

    public String getSold_by() { return sold_by; }
    public void setSold_by(String sb) { sold_by = sb;}

    public float getPrice(){ return price; }
    public void setPrice(float p){ price = p;}

    public int getStock(){ return stock; }
    public void setStock(int s){ stock = s; }

    public String getDescription(){ return description ;}
    public void setDescription(String d){ description = d;}

    public String getPhoto(){ return photo;}
    public void setPhoto(String p){ photo = p;}
}
