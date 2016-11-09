var express = require('express');
var router = express.Router();
var fs = require('fs');

/* GET home page. */

router.get('/', function(req, res, next) {
	fs.readFile('./views/app.html', function (err, html) {
	    if (err) {
	        throw err; 
	    }       
        res.writeHeader(200, {"Content-Type": "text/html"});  
        res.write(html);  
        res.end();  
	}); 
});

router.get('/products/', function(req, res, next) {
	var data = 
		[{
            name: "Product #1", description: "A product",
            category: "Category #1", price: 100
        },
        {
            name: "Product #2", description: "A product",
            category: "Category #1", price: 110
        },
        {
            name: "Product #3", description: "A product",
            category: "Category #2", price: 210
        },
        {
            name: "Product #4", description: "A product",
            category: "Category #3", price: 202
        }];
    console.log(JSON.stringify(data));
  	res.end(JSON.stringify(data));
});

module.exports = router;
