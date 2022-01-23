const authRouter = require("./user");
const recipeRouter = require("./recipe");

const route = (app) => {
  app.use("/api/user", authRouter);
  app.use("/api/recipe", recipeRouter);
};

module.exports = route;
