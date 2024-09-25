package com.danduran.flavor_finder.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import com.danduran.flavor_finder.model.enums.DifficultyEnum;
import com.danduran.flavor_finder.model.enums.RecipeStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "recipes")
public class Recipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name;

    @NonNull
    @Lob
    @Column(name="description", length=512)
    private String description;

    @NonNull
    @Lob
    @Column(name="steps", length=512)
    private String steps;

    @NonNull
    @Lob
    @Column(name="ingredients", length=512)
    private String ingredients;

    @NonNull
    @Column(name = "difficulty")
    @Enumerated(EnumType.ORDINAL)
    private DifficultyEnum difficulty;

    @Column(name = "preparation_time")
    private int preparationTime;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private RecipeStatus status;

    @Lob
    @Column(name="tools", length=512)
    private String tools;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
