package com.umair.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ContactInformation")
@Entity
public class ContactInformation {

    @Id
    private long id; // Should this be generated automatically? If so, use @GeneratedValue.

    private String email;
    private String mobile;
    private String twitter;
    private String instagram;
}
