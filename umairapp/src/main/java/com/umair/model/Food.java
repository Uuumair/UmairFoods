package com.umair.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String description;
    private Long price;

    @ManyToOne
    private Catogary foodCategory; // Corrected typo

    @Column(length = 1000)
    @ElementCollection
    private List<String> images;
    private boolean available;

    @ManyToOne
    private Restaurant restaurant;

    private boolean isVegetarian; // Corrected typo
    private boolean isSeasonable;

    @ManyToMany
    private List<IngredientsItems> ingredients = new ArrayList<>();

    private Date creationDate;
}
