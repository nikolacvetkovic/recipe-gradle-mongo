package xyz.riocode.guruspring.recipe.services;

import xyz.riocode.guruspring.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteIngredientById(String recipeId, String ingredientId);

}
