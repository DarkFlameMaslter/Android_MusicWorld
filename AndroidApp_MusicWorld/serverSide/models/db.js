
var mysql = require('mysql');

require('dotenv').config();

var connection = mysql.createPool({
    host : process.env.DB_host,
    user : process.env.DB_userLogin,
    password : process.env.DB_password,
    database : process.env.DB_Name,
});

module.exports = connection;



