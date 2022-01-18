const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const cartSchema = new Schema({
    userId: {
        type: Schema.Types.ObjectId,
        ref: 'User'
    },
    recipeLst: [{
        type: Schema.Types.ObjectId,
        ref: 'Recipe'
    }]
});

module.exports = mongoose.model("Cart", cartSchema);