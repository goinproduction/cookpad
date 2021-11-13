const Recipe = require("../models/recipes");

class RecipeController {
  async getAllRecipes(req, res) {
    try {
      const response = await Recipe.find()
        .populate("author", "name avatar")
        .exec();
      if (response) {
        return res.status(200).json({
          success: true,
          data: response,
        });
      }
    } catch (error) {
      console.log(error);
      res.status(400).json({
        success: false,
        message: "Internal Server Error",
      });
    }
  }

  async createRecipe(req, res) {
    const {
      title,
      description,
      origin,
      serves,
      cookTime,
      category,
      ingredients,
      steps,
    } = req.body;

    try {
      const newRecipe = new Recipe({
        author: req.params.userId,
        title,
        description,
        origin,
        serves,
        cookTime,
        category,
        ingredients,
        steps,
      });

      if (await newRecipe.save()) {
        return res.status(200).json({
          success: true,
        });
      }
    } catch (error) {
      console.log(error);
      res.status(400).json({
        success: false,
        message: "Internal Server Error",
      });
    }
  }

  async getRecipeByUserId(req, res) {
    try {
      const response = await Recipe.findOne({ author: req.params.userId })
        .populate("author", "name avatar")
        .exec();
      // .populate("author")
      // .exec();
      if (response) {
        return res.status(200).json({
          success: true,
          data: response,
        });
      }
    } catch (error) {
      console.log(error);
      res.status(400).json({
        success: false,
        message: "Internal Server Error",
      });
    }
  }

  async updateLike(req, res) {
    try {
      const { likes } = req.body;
      let response = await Recipe.findOneAndUpdate(
        { _id: req.params.recipeId },
        { likes },
        { new: true }
      );
      if (response) {
        return res.status(200).json({
          success: true,
        });
      } else {
        return res.status(400).json({
          success: false,
        });
      }
    } catch (error) {
      console.log(error);
      res.status(400).json({
        success: false,
        message: "Internal Server Error",
      });
    }
  }

  async updateHeart(req, res) {
    try {
      const { hearts } = req.body;
      let response = await Recipe.findOneAndUpdate(
        { _id: req.params.recipeId },
        { hearts },
        { new: true }
      );
      if (response) {
        return res.status(200).json({
          success: true,
        });
      } else {
        return res.status(400).json({
          success: false,
        });
      }
    } catch (error) {
      console.log(error);
      res.status(400).json({
        success: false,
        message: "Internal Server Error",
      });
    }
  }

  async updateClap(req, res) {
    try {
      const { claps } = req.body;
      let response = await Recipe.findOneAndUpdate(
        { _id: req.params.recipeId },
        { claps },
        { new: true }
      );
      if (response) {
        return res.status(200).json({
          success: true,
        });
      } else {
        return res.status(400).json({
          success: false,
        });
      }
    } catch (error) {
      console.log(error);
      res.status(400).json({
        success: false,
        message: "Internal Server Error",
      });
    }
  }
}

module.exports = new RecipeController();
