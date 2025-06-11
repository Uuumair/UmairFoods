package com.umair.model;
import com.umair.dto.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data


@AllArgsConstructor

@NoArgsConstructor
@Entity
public class User{
    @Id
@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fullname;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY   )
    private String password;

    private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order>orders=new ArrayList<>();
    @ElementCollection
    private List<RestaurantDto>favoroiteItem=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
private List<Address> addresses=new ArrayList<>();
    
    
}
