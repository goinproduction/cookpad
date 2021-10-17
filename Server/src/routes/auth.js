const express = require('express');
const router = express.Router();
const authController = require('../controllers/AuthController');
const uploadMiddleware = require('../middlewares/upload');
// Auth handling
router.post('/register', authController.register);
router.post('/login', authController.login);
router.put(
    '/edit-user-info/:id',
    uploadMiddleware.single('picture'),
    authController.editUserInfo
);

module.exports = router;
