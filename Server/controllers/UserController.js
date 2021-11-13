const User = require("../models/users");
const baseUrl = process.env.BASE_URL || "http://localhost:8080";

class UserController {
  async getUserInfoById(req, res) {
    try {
      const condition = {
        _id: req.params.userId,
      };
      const response = await User.findOne(condition).select(
        "-username -password -__v"
      );
      if (response) {
        return res.json({
          success: true,
          data: response,
        });
      }
    } catch (error) {
      console.log(error);
      res.status(500).json({
        success: "false",
        message: "Internal Server Error",
      });
    }
  }
  async updateUserInfoById(req, res) {
    const { name, email, about, cookpadId, address } = req.body;
    try {
      if (!req.file) {
        return res.status(400).json({
          success: false,
          message: "Avatar is required",
        });
      }
      const condition = {
        _id: req.params.userId,
      };

      const fieldUpdate = {
        name,
        email,
        about,
        cookpadId,
        address,
        avatar: `${baseUrl}/assets/img/${req.file.originalname}`,
      };

      let response = await User.findOneAndUpdate(condition, fieldUpdate, {
        new: true,
      });

      if (!response) {
        return res
          .status(401)
          .json({ success: false, message: "Fail to update" });
      }

      res.status(200).json({ success: true, message: "Success" });
    } catch (error) {
      console.log(error);
      res.status(400).json({
        success: false,
        message: "Internal Server Error",
      });
    }
  }
}

module.exports = new UserController();
