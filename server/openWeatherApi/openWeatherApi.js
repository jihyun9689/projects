var express = require('express');
var request = require('request');
var async = require('async');
var router = express.Router();


/* GET home page. */
router.get('/', function(req, res, next) {

});

router.get('/main/', function(req, res, next) {
  var data = req.cookies.data;
  if(data === undefined){
    res.render('main',{'name1':' ', 'name2':' ', 'temp': ' ', 'action' : 'first'});
  }else{
    var json = JSON.parse(data);
    console.log('json.name1 : ' + json.name1 + " json.temp : " + json.temp);
    res.render('main',{'name1':json.name1, 'name2':json.name2, 'temp': json.temp, 'action' : ''});

  }
});
router.get('/weatherAPI/', function(req, res, next) {
  console.log('weatherAPI');

  var lat = req.query.lat;
  var lon = req.query.lon;

  async.waterfall([
   function(callback){
      
     var weatherApikey = '';
     var weatherURL = 'http://api.openweathermap.org/data/2.5/weather?lat='+lat+'&lon='+lon+'&APPID='+weatherApikey+'&units=metric';
     request(weatherURL,function (error, response, body) {
         if (!error) {
           // body json 객체
            
           var obj = JSON.parse(body);
            
           var newObjet = new Object();
           var temp = obj.main.temp;
           var humidity = obj.main.humidity;
           var wind = obj.wind.speed;
           var description = obj.weather[0].description;
           console.log('main : ' + JSON.stringify(obj));
          
           callback(null, temp.toFixed(0)+'', humidity, wind, description);
         }else{
           console.log('error : ' + error); // Show the HTML for the Google homepage.    
         }
     });
   },
   function(temp, humidity, wind, description ,callback){

     console.log('lon : ' + lon + ' lat : ' + lat );
     var locationApikey = '';
       var locationURL= 'https://apis.daum.net/local/geo/coord2addr?apikey='+locationApikey+'&longitude='+lon+'&latitude='+lat+'&inputCoordSystem=WGS84&output=json';

     request(locationURL,function (error, response, body) {
         if (!error) {
           // body json 객체
           var obj = JSON.parse(body);
           var newObj = new Object();
           newObj.temp = temp;
           newObj.humidity = humidity;
           newObj.wind = wind;
           newObj.description = description;
           newObj.name1 = obj.name1;
           newObj.name2 = obj.name2;
           newObj.lat = lat;
           newObj.lon = lon;
           var stringJson = JSON.stringify(newObj);
           // 쿠키 저장
           res.cookie('data', stringJson);
           res.end(stringJson);
         }else{
           console.log('error : ' + error); // Show the HTML for the Google homepage.    
         }
     });
   }], function(err){
     if(err) console.log(err);
   }
  );
});

module.exports = router;
