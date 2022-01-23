const mongoose = require('mongoose');
const DB_URL = `mongodb+srv://${process.env.DB_USERNAME}:${process.env.DB_PASSWORD}@cnpm.7p9xj.mongodb.net/cookpad?retryWrites=true&w=majority`;

const ConnectToDatabase = async () => {
    try {
        await mongoose.connect(DB_URL, {
            useNewUrlParser: true,
            useUnifiedTopology: true,
        });

        console.log('Database connected');
    } catch (error) {
        console.log(error.message);
        process.exit(1);
    }
}

module.exports = ConnectToDatabase;