'use strict';
module.exports = {
    up: async (queryInterface, Sequelize) => {
        await queryInterface.createTable('Users', {
            id: {
                allowNull: false,
                autoIncrement: true,
                primaryKey: true,
                type: Sequelize.INTEGER,
            },
            name: {
                type: Sequelize.STRING(50),
            },
            username: {
                type: Sequelize.STRING(15),
            },
            password: {
                type: Sequelize.STRING(50),
            },
            email: {
                type: Sequelize.STRING(50),
            },
            address: {
                type: Sequelize.STRING(255),
            },
            about: {
                type: Sequelize.STRING(255),
            },
            avatar: {
                type: Sequelize.BLOB('long'),
            },
            cookpadId: {
                type: Sequelize.STRING(50),
            },
            userType: {
                type: Sequelize.BOOLEAN,
            },
            createdAt: {
                allowNull: false,
                type: Sequelize.DATE,
            },
            updatedAt: {
                allowNull: false,
                type: Sequelize.DATE,
            },
        });
    },
    down: async (queryInterface, Sequelize) => {
        await queryInterface.dropTable('Users');
    },
};
