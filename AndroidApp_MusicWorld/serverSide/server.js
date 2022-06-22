
const express = require('express');

var bodyparser = require('body-parser');

const fileUpload = require('express-fileupload');

var db = require('./models/db');

const app = express();
app.use(bodyparser.urlencoded({extended: true}));
app.use(bodyparser.json());

app.use(fileUpload({
    createParentPath: true
}));


require('dotenv').config();


app.get('/HololiveCA/allUser', function(req,res){
    console.log("confirm Call All user");
    var temp = db.query("SELECT * FROM USER", function(err, result, rows){
        if(err) throw err;
        // console.log(result);
        res.json(result)
    });
})

app.post('/HololiveCA/user', function(req,res){
    console.log("check");
    console.log(req.query.userLoginId);
    console.log(req.query.userLoginPassword);
    db.query("SELECT * FROM USER WHERE userLoginId = ? AND userLoginPassword = ?", [req.query.userLoginId,req.query.userLoginPassword] , function(err,user,rows){
        if(err){
            console.log(err); 
            res.json('Error');
            console.log(err);
        } else{
            console.log("reached here")
            console.log(user.length)
            if(user.length == 0 ){
                res.json("no User Found");
            } else{
                    res.json(user);
                }
        }
    });
})


app.post('/upload', async(req,res)=>{
    console.log("here!!")
    try{
        if(!req.files){
            console.log('error no file')
            res.send({
                status: false,
                message: 'No File Uploaded'
            });
        }
        else {
            console.log('done here')
            let song = req.files.songUpload;
            song.mv('./upload/'+song.name);

            res.send({
                status: true,
                message: 'file-uploaded'
            });
        }
    } catch(err){
        res.status(500).send(err)
    }
})

// app.get('/download', async(req,res)=>{
//     console.log("down here !!")
//     var filepath = './upload/Look At The Sky - Porter Robinson.mp3';
//     var fileName = 'Look At The Sky.mp3';
//     res.download(filepath,fileName);
// })


app.get('/OnlineSongList', function(req,res){
    console.log('been here!');
    db.query("SELECT * FROM SongsUpdate", function(err,songs, rows){
        res.json(songs);
        console.log(songs);
    })
})

app.get('/download',async(req,res)=>{
    let url = req.query.title;
    // console.log(url);
    db.query("SELECT * FROM SongsUpdate WHERE title = ?", url, function(err,song, rows){
        let defilepath = song[0].path;
        let defilename = song[0].title;
        // console.log(song);
        console.log(defilepath);
        res.download(defilepath,defilename);
    })
})


const port = process.env.PORT;
const hoster = '192.168.1.25'
app.listen(port,hoster, ()=> {
    console.log('Listening on port: '+port);
})
