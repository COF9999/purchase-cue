package com.project.restful.models;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Valorations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comentary;

    private byte valoration;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users users;

}
