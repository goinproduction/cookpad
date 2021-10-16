const express = require('express');
const router = express.Router();
const authController = require('../controllers/AuthController');

router.post('/register', authController.register);
router.post('/login', authController.login);
router.put('/edit-user-info/:id', authController.editUserInfo);

module.exports = router;
