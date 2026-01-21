const express = require('express')
const app = express()
const port = 3000

const session = require('express-session');
const mysql = require('mysql2');
const bodyParser = require('body-parser');
const path = require('path');

const dotenv = require('dotenv');
dotenv.config({ path: './stack.env' });




app.set('view engine', 'pug');
app.set('views', path.join(__dirname, 'views'));

//le decimos a express que use body parser
app.use(bodyParser.urlencoded({ extended: true }));

app.get('/', (req, res) => {
  res.render('hola')
})
app.get('/login', (req, res) => {
  res.render('login')
})
app.post ('/login', (req, res) => {
  const { username, password } = req.body;
  res.send('has echo login con el usuario : ' + username + ' y la contraseña: ' + password);
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})
