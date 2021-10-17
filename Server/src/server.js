require('dotenv').config();
const express = require('express');
const cors = require('cors');

global.__basedir = __dirname;
var corsOptions = {
    origin: 'http://localhost:8081',
};

const route = require('./routes');
const connectToDatabase = require('./config/database.js');

connectToDatabase();

const app = express();
app.use(express.json());
app.use(cors(corsOptions));

route(app);

const PORT = process.env.PORT || 5001;

app.listen(PORT, () => {
    console.log(`Server is running on PORT ${PORT}`);
});
