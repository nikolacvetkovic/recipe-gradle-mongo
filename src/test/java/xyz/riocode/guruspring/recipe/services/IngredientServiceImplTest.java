package xyz.riocode.guruspring.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.riocode.guruspring.recipe.commands.IngredientCommand;
import xyz.riocode.guruspring.recipe.commands.UnitOfMeasureCommand;
import xyz.riocode.guruspring.recipe.converters.IngredientCommandToIngredient;
import xyz.riocode.guruspring.recipe.converters.IngredientToIngredientCommand;
import xyz.riocode.guruspring.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import xyz.riocode.guruspring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import xyz.riocode.guruspring.recipe.domain.Ingredient;
import xyz.riocode.guruspring.recipe.domain.Recipe;
import xyz.riocode.guruspring.recipe.domain.UnitOfMeasure;
import xyz.riocode.guruspring.recipe.repositories.RecipeRepository;
import xyz.riocode.guruspring.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;

    IngredientServiceImpl ingredientService;

    Recipe recipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, unitOfMeasureRepository);
        recipe = new Recipe();
        recipe.setId("1");
    }

    @Test
    public void findByRecipeIdAndIngredientId() {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");
        recipe.addIngredient(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("2");
        recipe.addIngredient(ingredient2);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");
        recipe.addIngredient(ingredient3);

        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "2");

        assertEquals("2", ingredientCommand.getId());
        assertEquals("1", ingredientCommand.getRecipeId());
    }

    @Test
    public void testSaveIngredient(){
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("5");
        ingredientCommand.setRecipeId("1");

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("5");
        savedRecipe.addIngredient(ingredient);

        when(recipeRepository.findById(any())).thenReturn(Optional.of(new Recipe()));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);

        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        assertEquals("5", savedIngredientCommand.getId());
        verify(recipeRepository).findById(any());
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    public void testUpdateIngredient(){
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("5");
        ingredientCommand.setRecipeId("1");
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId("2");
        ingredientCommand.setUom(unitOfMeasureCommand);

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("5");
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId("2");
        ingredient.setUom(unitOfMeasure);
        savedRecipe.addIngredient(ingredient);

        when(recipeRepository.findById(any())).thenReturn(Optional.of(savedRecipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);
        when(unitOfMeasureRepository.findById(any())).thenReturn(Optional.of(unitOfMeasure));

        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        assertEquals("5", savedIngredientCommand.getId());
        verify(recipeRepository).findById(any());
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    public void testDeleteById(){
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");
        recipe.addIngredient(ingredient);

        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));

        ingredientService.deleteIngredientById("1", "3");

        verify(recipeRepository).findById(any());
        verify(recipeRepository).save(any(Recipe.class));
    }
}