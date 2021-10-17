const util = require('util');
const multer = require('multer');
const maxSize = 2 * 1024 * 1024;

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, __basedir + 'resources/static/assets/uploads/');
    },
    filename: (req, file, cb) => {
        cb(null, file.originalname);
    },
});

const upload = multer({
    storage: storage,
    limits: { fileSize: maxSize },
});

module.exports = upload;
