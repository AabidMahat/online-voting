package com.example.voting_system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private Date startDate;
    private Date endDate;

    @Lob
    private byte[] electionBanner;

    @Transient // Ignore in persistence
    private String electionBannerBase64;


}
