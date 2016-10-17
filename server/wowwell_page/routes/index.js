var express = require('express');
var router = express.Router();
var ejs = require('ejs');

/* GET home page. */
router.get('/', function(req, res, next) {
  ejs.renderFile('./views/wowwell.html', function(err, data) {
		res.end(data);
	});
});

module.exports = router;
