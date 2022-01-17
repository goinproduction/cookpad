const Recipe = require("../models/recipes");
const Category = require('../models/category')

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
      image
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
        image
      });

      if (await newRecipe.save()) {
        return res.status(200).json({
          success: true,
          new: newRecipe
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
      const response = await Recipe.findOne({
        author: req.params.userId
      })
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
      const like_num = req.query.like_num;
      let response = await Recipe.findOneAndUpdate({
        _id: req.params.recipeId
      }, {
        likes: parseInt(like_num)
      }, {
        new: true
      });
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
      const heart_num = req.query.heart_num;
      let response = await Recipe.findOneAndUpdate({
        _id: req.params.recipeId
      }, {
        hearts: parseInt(heart_num)
      }, {
        new: true
      });
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
      const clap_num = req.query.clap_num;
      let response = await Recipe.findOneAndUpdate({
        _id: req.params.recipeId
      }, {
        claps: parseInt(clap_num)
      }, {
        new: true
      });
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

  async getAllCategories(req, res) {
    try {
      const response = await Category.find().populate({ path: 'categoryLst.recipeLst', populate: { path: 'author', select: '-password' } }).exec();
      if (response) {
        return res.status(200).json({
          success: true,
          data: response,
        });
      }
      return res.status(400).json({
        success: false,
        message: "Bad Request"
      })
    } catch (error) {
      console.log(error);
      res.status(400).json({
        success: false,
        message: "Internal Server Error",
      });
    }
  }
  async createCategory(req, res) {
    const {
      name,
      categoryLst
    } = req.body;

    try {
      const newCategory = new Category({
        name,
        categoryLst
      });

      if (await newCategory.save()) {
        return res.status(200).json({
          success: true,
        });
      }
      return res.status(400).json({
        success: false,
        message: "Bad Request"
      })
    } catch (error) {
      console.log(error);
      res.status(400).json({
        success: false,
        message: "Internal Server Error",
      });
    }
  }
  async search(req, res) {
    try {
      const { payload } = req.body;
      let data = await Recipe.find({ $text: { $search: payload } }).limit(2).exec();
      if (data.length > 0) {
        return res.status(200).json({
          success: true,
          data
        })
      } else {
        return res.status(200).json({
          success: false,
          message: "Không tìm thấy công thức phù hợp!"
        })
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