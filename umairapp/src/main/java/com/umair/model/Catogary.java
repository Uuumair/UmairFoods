package com.umair.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Catogary {
   @Id
@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    
    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    

}
