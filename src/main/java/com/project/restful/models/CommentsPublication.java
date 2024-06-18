package com.project.restful.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "comments_publication")
public class CommentsPublication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commnetary;

    @ManyToOne
    @JoinColumn(name = "id_publication")
    private Publications publications;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users users;
}
