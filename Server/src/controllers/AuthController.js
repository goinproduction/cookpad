const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
const database = require('../models/index');

const saltRounds = 10;
const salt = bcrypt.genSaltSync(saltRounds);

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
        if (!name) {
            return res.status(400).json({
                success: false,
                message: 'Name is required',
            });
        } else if (!email) {
            return res.status(400).json({
                success: false,
                message: 'Email is required',
            });
        } else if (!userType) {
            return res.status(400).json({
                success: false,
                message: 'User type is required',
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
                message: 'User has been successfully created',
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
        const { username, password } = req.body;

        // Simple validation
        if (!username || !password) {
            return res.status(400).json({
                success: false,
                message: 'Missing username or password',
            });
        }

        try {
            // Check existing user
            const user = await database.User.findOne({ where: { username } });

            // Username check
            if (!user)
                return res.status(400).json({
                    success: false,
                    message: 'Incorrect username or password',
                });

            // Password check
            const validPassword = bcrypt.compareSync(password, user.password);
            console.log(validPassword);

            if (!validPassword)
                return res.status(400).json({
                    success: false,
                    message: 'Incorrect username or password',
                });

            // All good
            const accessToken = jwt.sign(
                { userId: user.id },
                process.env.ACCESS_TOKEN_SECRET
            );
            res.json({
                success: true,
                message: 'User has successfully logged in',
                accessToken,
            });
        } catch (error) {
            res.status(500).json({
                success: 'false',
                message: 'Internal server error',
            });
        }
    }
}

module.exports = new AuthController();
