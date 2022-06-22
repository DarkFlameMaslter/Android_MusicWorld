var db = require('./db');

var User = {
    getalluser: function(callback){
        return db.query("select * from USER", callback);
    },

    getUserByUserName: function(userLoginID,callback){
        return db.query("select * from USER where userLoginID=?",[userLoginID],callback);
    },

    newUser: function(user,callback){
        return db.query("insert into USER(username,userLoginID,userPassword,userPrivilege) values(?,?,?,2)",[user.username, user.userLoginID, user.userPassword], callback);
    },

    deleteUser: function(userLoginID, callback){
        return db.query("delete from USER where userLoginID =?",[userLoginID], callback);
    },

};
module.exports=User;