const jwt = require("jsonwebtoken");
const User = require("../models/users");
const bcrypt = require("bcrypt");

const saltRounds = 10;
const salt = bcrypt.genSaltSync(saltRounds);

class AuthController {
  // @route GET /api/auth
  // @desc Check if user is logged in
  // @access public
  async isUserLogged(req, res) {
    try {
      const user = await User.findById(req.userId).select("-password");
      if (!user)
        return res
          .status(400)
          .json({
            success: false,
            message: "User not found"
          });
      return res.json({
        success: true,
        user
      });
    } catch (error) {
      console.log(error);
      res.status(500).json({
        success: "false",
        message: "Internal Server Error",
      });
    }
  }

  // @route POST /api/auth/register
  // @desc Register user
  // @access public
  async register(req, res) {
    const {
      username,
      password,
      name,
      role
    } = req.body;
    if (!username || !password) {
      return res.status(400).json({
        success: false,
        message: "Missing username or password",
      });
    }

    if (!name) {
      return res.status(400).json({
        success: false,
        message: "Name is required",
      });
    }

    try {
      // Check for existing user
      const user = await User.findOne({
        username
      });

      if (user)
        return res
          .status(400)
          .json({
            success: false,
            message: "User already exists"
          });

      const hashedPassword = bcrypt.hashSync(password, salt);
      const newUser = new User({
        username,
        password: hashedPassword,
        name,
        avatar: "",
        address: "",
        about: "",
        cookpadId: "",
        role,
      });
      await newUser.save();

      // Return token
      const token = jwt.sign({
          userId: newUser._id
        },
        process.env.ACCESS_TOKEN_SECRET
      );
      res.status(200).json({
        success: true,
        token,
      });
    } catch (error) {
      console.log(error);
      res.status(500).json({
        success: "false",
        message: "Internal Server Error",
      });
    }
  }

  // @route POST /api/auth/login
  // @desc Login user
  // @access public
  async login(req, res) {
    const {
      username,
      password
    } = req.body;

    // Simple validation
    if (!username || !password) {
      return res.status(400).json({
        success: false,
        message: "Missing username or password",
      });
    }

    try {
      // Check existing user
      const user = await User.findOne({
        username
      });
      if (!user)
        return res.status(400).json({
          success: false,
          message: "Invalid username or password",
        });

      // Username exists
      const validPassword = bcrypt.compareSync(password, user.password);

      if (!validPassword)
        return res.status(400).json({
          success: false,
          message: "Invalid username or password",
        });

      // All good
      const token = jwt.sign({
          userId: user._id
        },
        process.env.ACCESS_TOKEN_SECRET
      );
      res.json({
        success: true,
        token,
        id: req._id,
        user
      });
    } catch (error) {
      console.log(error);
      res.status(500).json({
        success: "false",
        message: "Internal Server Error",
      });
    }
  }
}

module.exports = new AuthController();