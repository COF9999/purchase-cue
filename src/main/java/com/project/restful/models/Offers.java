package com.project.restful.models;

import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Offers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_publication")
    private Publications publications;

    @ManyToOne
    @JoinColumn(name = "id_exchanged_product")
    private Products product;

    @ManyToOne
    @JoinColumn(name = "id_bidder")
    private Users users;

    @Column(name = "offer_value")
    private Float offerValue;

    @Column(name = "offer_date")
    private Date offerDate;

    @Column(name = "isCounterOffer")
    private Boolean isCounterOffer;

    private Byte state;

    @OneToMany(mappedBy = "offers",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CounterOffer> counterOfferList = new ArrayList<>();

}
