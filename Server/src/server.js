require('dotenv').config();
import express from 'express';
import initWebRoutes from './route/web';
import connectDB from './config/connectDB';

let app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

initWebRoutes(app);

connectDB();

let PORT = process.env.PORT || 5001;

app.listen(PORT, () => {
    console.log(`Server is running on PORT ${PORT}`);
});
