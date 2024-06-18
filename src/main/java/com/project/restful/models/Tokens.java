package com.project.restful.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Tokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users users;

    @Column(name = "is_log_out")
    private Boolean isLogOut;
}
