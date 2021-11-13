require('dotenv').config();
const express = require('express');
const cors = require('cors');

const route = require('./routes');
const ConnectToDatabase = require('./configs/ConnectToDB');

ConnectToDatabase();

const app = express();
app.use(express.json());
app.use(cors());

route(app);

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Server started on port ${PORT}`));