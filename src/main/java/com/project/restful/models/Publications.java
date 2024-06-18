package com.project.restful.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Setter
@Getter
@Entity
public class Publications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Date date;
    private Byte status;
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Products product;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users user;
}
