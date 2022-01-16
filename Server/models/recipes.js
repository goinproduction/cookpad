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
    data: Buffer,
    type: String,
    default: ""
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
  ingredients: [String],
  steps: [{
    name: {
      type: String
    },
    picture: {
      data: Buffer,
      type: String,
      default: ""
    },
  },],
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
recipeSchema.index({ title: 'text' });
module.exports = mongoose.model("Recipe", recipeSchema);