const express = require("express");
const router = express.Router();
const recipeController = require("../controllers/RecipeController");

router.get("/getAllRecipes", recipeController.getAllRecipes);
router.get("/getRecipe/:userId", recipeController.getRecipeByUserId);

router.post("/createRecipe/:userId", recipeController.createRecipe);

router.post("/editRecipeLike/:recipeId", recipeController.updateLike);
router.post("/editRecipeClap/:recipeId", recipeController.updateClap);
router.post("/editRecipeHeart/:recipeId", recipeController.updateHeart);

module.exports = router;
