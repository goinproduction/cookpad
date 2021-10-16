'use strict';
const { Model } = require('sequelize');
module.exports = (sequelize, DataTypes) => {
    class User extends Model {
        /**
         * Helper method for defining associations.
         * This method is not a part of Sequelize lifecycle.
         * The `models/index` file will call this method automatically.
         */
        static associate(models) {
            // define association here
        }
    }
    User.init(
        {
            name: DataTypes.STRING(50),
            username: DataTypes.STRING(15),
            password: DataTypes.STRING(50),
            email: DataTypes.STRING(50),
            address: DataTypes.STRING(255),
            about: DataTypes.STRING(255),
            avatar: DataTypes.BLOB('long'),
            cookpadId: DataTypes.STRING(50),
            userType: DataTypes.BOOLEAN,
        },
        {
            sequelize,
            modelName: 'User',
        }
    );
    return User;
};
