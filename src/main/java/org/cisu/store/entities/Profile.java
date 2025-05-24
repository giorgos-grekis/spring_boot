package org.cisu.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bio")
    private String bio;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="date_of_birth")
    private Date dateOfBirth;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    @ToString.Exclude
    private User user;



}
