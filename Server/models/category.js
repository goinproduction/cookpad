const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const categorySchema = new Schema({
    name: {
        type: String
    },
    categoryLst: {
        type: [{
            title: String,
            image: {
                type: String
            },
            recipeLst: [{
                type: Schema.Types.ObjectId,
                ref: 'Recipe'
            }]
        }]
    }
});

module.exports = mongoose.model("Category", categorySchema);