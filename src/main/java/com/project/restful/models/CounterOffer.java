package com.project.restful.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Setter
@Getter
@Table(name = "counter_offer")
@Entity
public class CounterOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String description;

    private Byte state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_offer")
    private Offers offers;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users users;


}
