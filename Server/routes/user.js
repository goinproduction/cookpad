const express = require("express");
const router = express.Router();
const verifyToken = require("../middlewares/auth");
const upload = require("../middlewares/upload");
const authController = require("../controllers/AuthController");
const userController = require("../controllers/UserController");

// AUTH
router.get("/", verifyToken, authController.isUserLogged);
router.post("/register", authController.register);
router.post("/login", authController.login);

// EDIT USER INFORMATION
router.get("/get/:userId", userController.getUserInfoById);
router.post(
  "/edit/:userId",
  upload.single("avatar"),
  userController.updateUserInfoById
);

module.exports = router;
