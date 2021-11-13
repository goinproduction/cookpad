const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const recipeSchema = new Schema({
  author: {
    type: Schema.Types.ObjectId,
    ref: "User",
  },
  title: {
    type: String,
  },
  image: {
    type: String,
  },
  description: {
    type: String,
  },
  origin: {
    type: String,
  },
  serves: {
    type: Number,
  },
  cookTime: {
    type: Number,
  },
  dateCreate: {
    type: Date,
    default: Date.now(),
  },
  category: {
    type: String,
  },
  ingredients: [
    {
      name: String,
    },
  ],
  steps: [
    {
      name: String,
      picture: String,
    },
  ],
  likes: {
    type: Number,
    default: 0,
  },
  hearts: {
    type: Number,
    default: 0,
  },
  claps: {
    type: Number,
    default: 0,
  },
});

module.exports = mongoose.model("Recipe", recipeSchema);
