const express = require('express');
const path = require('path');

const app = express();


app.use(express.static(path.join(__dirname, '/static')));

app.get('*', (req,res) =>{
    res.sendFile(path.join(__dirname+'/static/index.html'));
});

const port = process.env.PORT || 5000;
app.listen(port);

console.log('App is listening on port ' + port);
