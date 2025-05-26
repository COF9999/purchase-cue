package com.project.restful.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private Double price;
    private String name;
    @Column(name = "condition_product")
    private Byte condition;
    private String img;
    private String description;
    private int owners;
    private Boolean deleted;

    @Column(name = "is_cloud")
    private Boolean isCloud;

    @Column(name = "is_changed")
    private Boolean isChanged;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users user;
}
