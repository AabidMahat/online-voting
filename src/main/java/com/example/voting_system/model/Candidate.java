package com.example.voting_system.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private byte[] candidatePhoto;

    private String partyName;

    @Lob
    private byte[] symbolPhoto;

    @Transient // Ignore in persistence
    private String candidatePhotoBase64;

    @Transient
    private String symbolPhotoBase64;

}
