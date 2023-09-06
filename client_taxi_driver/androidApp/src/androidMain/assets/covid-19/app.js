// This application uses express as its web server
var express = require('express');
var path = require('path');
var cfenv = require('cfenv'); // cfenv provides access to your Cloud Foundry environment
const blockchain = require('./blockchain.js');
const bitcoin = new blockchain();
const sha256=require('sha256');
const fs = require('fs');
var cookieParser = require('cookie-parser');

var path = require('path');
var filePath = path.join(__dirname, 'chainFile.txt');

// create a new express server
var app = express();
app.use(cookieParser())
// static rendering from the homepage
app.use('/', express.static(path.join(__dirname,'public')));

// serve the blockchain app here from /app
app.get('/blocks', function(req,res) {
    fs.readFile(filePath, {encoding: 'utf-8'}, function(err,data){
        if (!err) {
            bitcoin.chain = JSON.parse(data);
            nextSteps();
        } else {
            console.log(err);
        }
    });
    const nextSteps = function(){
        res.send(JSON.stringify(bitcoin));
    }
});

// return the validity of the blockchain
app.get('/validity', function(req, res){
    fs.readFile(filePath, {encoding: 'utf-8'}, function(err,data){
        if (!err) {
            bitcoin.chain = JSON.parse(data);
            nextSteps();
        } else {
            console.log(err);
        }
    });
    const nextSteps = function(){
        res.send(bitcoin.chainIsValid(bitcoin.chain));
    }
});

// endpoint for creating ventilators
app.get('/createVentilator', function(req,res) {
    var userid=req.param('userid');
    if (req.cookies.sub==userid && req.cookies.sub!=undefined){
        console.log(userid)
        console.log(req.cookies.sub)
    fs.readFile(filePath, {encoding: 'utf-8'}, function(err,data){
        if (!err) {
            bitcoin.chain = JSON.parse(data);
            nextSteps();
            res.send("Success: Created Ventilator. Redirecting to login page. Please wait... <script type='text/javascript'>setTimeout(() => {  window.location.href='/hospital/login'; }, 3000);</script>");
        } else {
            console.log(err);
        }
    });
    var nextSteps = function(){
        
        var hospital = req.param('hospital');
        var hospitalname = req.param('hospitalname');
        var vacant = parseInt(req.param('vacant'));
        var occupied = parseInt(req.param('occupied'));
        var longi = parseFloat(req.param('longi'));
        var lati = parseFloat(req.param('lati'));
        var vbed = parseInt(req.param('vbed'));
        var obed = parseInt(req.param('obed'));
        
        var blockData = bitcoin.createVentilator(hospital,hospitalname,vacant,occupied,longi,lati,vbed,obed);
        var currentHash = bitcoin.hash(bitcoin.getLastBlock().hash,blockData,100);
        bitcoin.createBlock(102,bitcoin.getLastBlock().hash,currentHash);
        fs.writeFile("chainFile.txt", JSON.stringify(bitcoin.chain), function(err) {
        if(err) {
            return console.log(err);
        }
        }); 
        }
    }else {
        res.send("Invalid request. Only authorised users from hospitals are allowed to use their service.<script type='text/javascript'>setTimeout(() => {  window.location.href='/'; }, 3000);</script>")
    }
});

// serving the blockchain
app.get('/chainFile', function(req,res) {
    res.sendFile(path.join(__dirname,"chainFile.txt"));
});

// serving the login page for the hospitals
app.use('/hospital/login', express.static(path.join(__dirname,'public/hosp_login')));

app.use('/app', express.static(path.join(__dirname,'map')));

// returning page after successfull signup
app.get('/register-success', function(req,res){
    res.sendFile(path.join(__dirname, 'public/hosp_login/register-success.html'))
});


// #######################################
// # PLEASE DO NOT CHANGE ANYTHING BELOW #
// #######################################

// get the app environment from Cloud Foundry
var appEnv = cfenv.getAppEnv();

// start server on the specified port and binding host
app.listen(appEnv.port, '0.0.0.0', function() {
  // print a message when the server starts listening
  console.log("server starting on " + appEnv.url);
});