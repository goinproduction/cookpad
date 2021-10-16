const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
const database = require('../models/index');

const salt = bcrypt.genSaltSync(10);

class AuthController {
    // @route POST /api/auth/register
    // @desc Register user
    // @access public
    async register(req, res) {
        const { name, username, password, email, userType } = req.body;
        // Simple validation
        if (!username || !password) {
            return res.status(400).json({
                success: false,
                message: 'Missing username or password',
            });
        }
        try {
            const user = await database.User.findOne({ where: { username } });
            // Check for existing user
            if (user)
                return res
                    .status(400)
                    .json({ success: false, message: 'User already exists' });

            // All good
            const hashedPassword = bcrypt.hashSync(password, salt);
            const newUser = {
                name,
                username,
                password: hashedPassword,
                email,
                userType,
            };

            await database.User.create(newUser);

            // Return token
            const accessToken = jwt.sign(
                { userId: newUser.id },
                process.env.ACCESS_TOKEN_SECRET
            );

            res.json({
                success: true,
                message: 'User has been created successfully',
                accessToken,
            });
        } catch (error) {
            res.status(500).json({
                success: 'false',
                message: 'Internal server error',
            });
        }
    }
    // @route POST /api/auth/login
    // @desc Login user
    // @access public
    async login(req, res) {
        // const { username, password } = req.body;
        // // Simple validation
        // if (!username || !password) {
        //     return res.status(400).json({
        //         success: false,
        //         message: 'Missing username or password',
        //     });
        // }
        // try {
        //     // Check existing user
        // } catch (error) {
        //     res.status(500).json({
        //         success: 'false',
        //         message: 'Internal server error',
        //     });
        // }
    }
}

module.exports = new AuthController();
