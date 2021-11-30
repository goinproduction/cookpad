const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const userSchema = new Schema({
  username: {
    type: String,
    required: true,
    unique: true,
  },
  password: {
    type: String,
    required: true,
  },
  email: {
    type: String,
  },
  avatar: {
    data: Buffer,
    type: String,
    default: ""
  },
  name: {
    type: String,
  },
  address: {
    type: String,
  },
  about: {
    type: String,
  },
  cookpadId: {
    type: String,
  },
  role: {
    type: Number,
  },
  address: {
    type: String,
  },
});

module.exports = mongoose.model("User", userSchema);