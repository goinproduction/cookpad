const jwt = require('jsonwebtoken');
const User = require('../models/User');

class AuthController {
    // @route GET /api/auth
    // @desc Check if user is logged in
    // @access public
    async isUserLogged(req, res) {
        try {
            const user = await User.findById(req.userId).select('-password');
            if (!user)
                return res
                    .status(400)
                    .json({ success: false, message: 'User Not Found' });
            return res.json({ success: true, user });
        } catch (error) {
            console.log(error);
            res.status(500).json({
                success: 'false',
                message: 'Internal Server Error',
            });
        }
    }
    // @route POST /api/auth/register
    // @desc Register user
    // @access public
    async register(req, res) {
        const { username, password } = req.body;
        const photoURL = Math.floor(Math.random() * 5) + 1;
        // Simple validation
        if (!username || !password) {
            return res.status(400).json({
                success: false,
                message: 'Missing username or password',
            });
        }

        try {
            const user = await User.findOne({ username });

            if (user)
                return res
                    .status(400)
                    .json({ success: false, message: 'User already exists' });

            const hashedPassword = await argon2.hash(password);
            const newUser = new User({
                username,
                password: hashedPassword,
                photoURL,
            });
            await newUser.save();

            const accessToken = jwt.sign(
                { userId: newUser._id },
                process.env.ACCESS_TOKEN_SECRET
            );

            res.json({
                success: true,
                message: 'User has been created successfully',
                accessToken,
            });
        } catch (error) {
            console.log(error);
            res.status(500).json({
                success: 'false',
                message: 'Internal Server Error',
            });
        }
    }

    // @route POST /api/auth/login
    // @desc Login user
    // @access public
    async login(req, res) {
        const { username, password } = req.body;

        if (!username || !password) {
            return res.status(400).json({
                success: false,
                message: 'Missing username or password',
            });
        }

        try {
            const user = await User.findOne({ username });
            if (!user)
                return res.status(400).json({
                    success: false,
                    message: 'Incorrect username or password',
                });

            const validPassword = argon2.verify(user.password, password);
            if (!validPassword)
                return res.status(400).json({
                    success: false,
                    message: 'Incorrect username or password',
                });

            const accessToken = jwt.sign(
                { userId: user._id },
                process.env.ACCESS_TOKEN_SECRET
            );
            res.json({
                success: true,
                message: 'User has been logged in successfully',
                accessToken,
            });
        } catch (error) {
            console.log(error);
            res.status(500).json({
                success: 'false',
                message: 'Internal Server Error',
            });
        }
    }
}

module.exports = new AuthController();