const authRouter = require('./auth');

const route = (app) => {
    app.use('/api/auth', authRouter);
};

module.exports = route;
