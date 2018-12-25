package xyz.riocode.guruspring.recipe.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Ingredient {


    private String id;
    private String description;
    private BigDecimal amount;

    public Ingredient(){}

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    private Recipe recipe;

    private UnitOfMeasure uom;
}
