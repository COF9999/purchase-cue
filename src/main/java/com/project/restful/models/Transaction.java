package com.project.restful.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_offer")
    private Offers offers;

    @Column(name = "transaction_date")
    private Date date;


    @ManyToOne
    @JoinColumn(name = "id_seller")
    private Users userSeller;


    @ManyToOne
    @JoinColumn(name = "id_commentary_user_seller")
    private Valorations valorationsSeller;

    @ManyToOne
    @JoinColumn(name = "id_commentary_user_bidder")
    private Valorations valorationsBidder;
}
