package com.danduran.flavor_finder.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.danduran.flavor_finder.model.Recipe;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class RecipeSpecification implements Specification<Recipe> {

    private Integer difficulty;
    private Integer maxPreparationTime;
    private String word;

    public RecipeSpecification() {
    }

    public RecipeSpecification(Integer difficulty, Integer maxPreparationTime, String word) {
        this.difficulty = difficulty;
        this.maxPreparationTime = maxPreparationTime;
        this.word = word;
    }

    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if(difficulty != null){
            predicates.add(criteriaBuilder.equal(root.get("difficulty"), difficulty));
        }

        if(maxPreparationTime != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("preparationTime"), maxPreparationTime));
        }

        if(word != null){
            predicates.add(criteriaBuilder.like(root.get("name"), "%"+word+"%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
