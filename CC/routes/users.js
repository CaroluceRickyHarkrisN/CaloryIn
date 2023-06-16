var express = require('express')
var router = express.Router()
const admin = require('../config/firebase-config')
const auth = require('../middleware/auth')

/* GET users listing. */
router.get('/', auth.verifyLogin, function (req, res, next) {
  async function getUsers() {
    const snapshot = await admin.firestore().collection('users').get()
    const users = snapshot.docs.map((doc) => doc.data())
    return users
  }
  getUsers().then((users) => res.send(users))
})

// Create Document ID same as firebase auth UID
router.post('/register', async (req, res, next) => {
  async function createUser() {
    const today = new Date()
    const date = new Date(req.body.birthDate)
    const age = today.getFullYear() - date.getFullYear()
    const gender = req.body.gender
    const weight = req.body.weight
    const height = req.body.height
    if(gender=='male' || 'laki-laki' || 'pria'){
      if(age >= 10 && age <= 12){
        var caloryNeed = 2000
        var proteinNeed = 50
        var fatNeed = 65
        var carbohydrateNeed = 300
        var fiberNeed = 28
      } else if(age >= 13 && age <= 15){
        var caloryNeed = 2400
        var proteinNeed = 70
        var fatNeed = 80
        var carbohydrateNeed = 350
        var fiberNeed = 34
      } else if(age >= 16 && age <= 18){
        var caloryNeed = 2650
        var proteinNeed = 75
        var fatNeed = 85
        var carbohydrateNeed = 400
        var fiberNeed = 37
      } else if(age >= 19 && age <= 29){
        var caloryNeed = 2650
        var proteinNeed = 65
        var fatNeed = 75
        var carbohydrateNeed = 430
        var fiberNeed = 37
      } else if(age >= 30 && age <= 49){
        var caloryNeed = 2550
        var proteinNeed = 65
        var fatNeed = 70
        var carbohydrateNeed = 415
        var fiberNeed = 36
      } else if(age >= 50 && age <= 64){
        var caloryNeed = 2150
        var proteinNeed =65
        var fatNeed = 60
        var carbohydrateNeed = 340
        var fiberNeed = 30
      } else if(age >= 65 && age <= 80){
        var caloryNeed = 1800
        var proteinNeed = 64
        var fatNeed = 50
        var carbohydrateNeed = 275
        var fiberNeed = 25
      } else if(age > 80){
        var caloryNeed = 1600
        var proteinNeed = 64
        var fatNeed = 45
        var carbohydrateNeed = 235
        var fiberNeed = 22
      } 
    } else if (gender=='female' || 'perempuan' || 'wanita'){
      if(age >= 10 && age <= 12){
        var caloryNeed = 1900
        var proteinNeed = 55
        var fatNeed = 65
        var carbohydrateNeed = 280
        var fiberNeed = 27
      } else if(age >= 13 && age <= 15){
        var caloryNeed = 2050
        var proteinNeed = 65
        var fatNeed = 70
        var carbohydrateNeed = 300
        var fiberNeed = 29
      } else if(age >= 16 && age <= 18){
        var caloryNeed = 2100
        var proteinNeed = 65
        var fatNeed = 70
        var carbohydrateNeed = 300
        var fiberNeed = 29
      } else if(age >= 19 && age <= 29){
        var caloryNeed = 2250
        var proteinNeed = 60
        var fatNeed = 65
        var carbohydrateNeed = 360
        var fiberNeed = 32
      } else if(age >= 30 && age <= 49){
        var caloryNeed = 2150
        var proteinNeed = 60
        var fatNeed = 60
        var carbohydrateNeed = 340
        var fiberNeed = 30
      } else if(age >= 50 && age <= 64){
        var caloryNeed = 1800
        var proteinNeed =60
        var fatNeed = 50
        var carbohydrateNeed = 280
        var fiberNeed = 25
      } else if(age >= 65 && age <= 80){
        var caloryNeed = 1550
        var proteinNeed = 58
        var fatNeed = 45
        var carbohydrateNeed = 230
        var fiberNeed = 22
      } else if(age > 80){
        var caloryNeed = 1400
        var proteinNeed = 58
        var fatNeed = 40
        var carbohydrateNeed = 200
        var fiberNeed = 20
      }
    }

    const user = {
      uid: req.body.uid,
      name: req.body.name,
      email: req.body.email,
      birthDate: date,
      gender: gender,
      height: height,
      weight: weight,
      caloryNeed: caloryNeed,
      proteinNeed: proteinNeed,
      fatNeed: fatNeed,
      carbohydrateNeed: carbohydrateNeed,
      fiberNeed: fiberNeed,
    }
    
    await admin
      .firestore()
      .collection('users')
      .doc(req.body.uid)
      .set(user)
  }
  createUser().then((user) => res.send(user))
})

// Get user by UID
router.get('/:uid', auth.verifyLogin, async (req, res, next) => {
  async function getUser() {
    const snapshot = await admin
      .firestore()
      .collection('users')
      .doc(req.params.uid)
      .get()

      if(!snapshot.exists){
        res.status(404).send({message: 'User not found'})
      } else {
        const birthDate = snapshot.data().birthDate
        var epoch = new Date(0)
        epoch.setUTCSeconds(birthDate.seconds)
        var birthDateFormat = epoch.toISOString().slice(0, 10)

        const user = {
          uid: snapshot.data().uid,
          name: snapshot.data().name,
          email: snapshot.data().email,
          birthDate: birthDateFormat,
          gender: snapshot.data().gender,
          height: snapshot.data().height,
          weight: snapshot.data().weight,
          caloryNeed: snapshot.data().caloryNeed,
          proteinNeed: snapshot.data().proteinNeed,
          fatNeed: snapshot.data().fatNeed,
          carbohydrateNeed: snapshot.data().carbohydrateNeed,
          fiberNeed: snapshot.data().fiberNeed,
        }
        console.log(user)
      res.status(200).send(user)
      }

    
  }

  getUser().then((user) => res.send(user))
})

// Update user by UID
router.put('/update/:uid', async (req, res, next) => {
  async function updateUser() {
    const today = new Date()
    const date = new Date(req.body.birthDate)
    const age = today.getFullYear() - date.getFullYear()
    const gender = req.body.gender
    const weight = req.body.weight
    const height = req.body.height
    if(gender=='male' || 'laki-laki' || 'pria'){
      if(age >= 10 && age <= 12){
        var caloryNeed = 2000
        var proteinNeed = 50
        var fatNeed = 65
        var carbohydrateNeed = 300
        var fiberNeed = 28
      } else if(age >= 13 && age <= 15){
        var caloryNeed = 2400
        var proteinNeed = 70
        var fatNeed = 80
        var carbohydrateNeed = 350
        var fiberNeed = 34
      } else if(age >= 16 && age <= 18){
        var caloryNeed = 2650
        var proteinNeed = 75
        var fatNeed = 85
        var carbohydrateNeed = 400
        var fiberNeed = 37
      } else if(age >= 19 && age <= 29){
        var caloryNeed = 2650
        var proteinNeed = 65
        var fatNeed = 75
        var carbohydrateNeed = 430
        var fiberNeed = 37
      } else if(age >= 30 && age <= 49){
        var caloryNeed = 2550
        var proteinNeed = 65
        var fatNeed = 70
        var carbohydrateNeed = 415
        var fiberNeed = 36
      } else if(age >= 50 && age <= 64){
        var caloryNeed = 2150
        var proteinNeed =65
        var fatNeed = 60
        var carbohydrateNeed = 340
        var fiberNeed = 30
      } else if(age >= 65 && age <= 80){
        var caloryNeed = 1800
        var proteinNeed = 64
        var fatNeed = 50
        var carbohydrateNeed = 275
        var fiberNeed = 25
      } else if(age > 80){
        var caloryNeed = 1600
        var proteinNeed = 64
        var fatNeed = 45
        var carbohydrateNeed = 235
        var fiberNeed = 22
      } 
    } else if (gender=='female' || 'perempuan' || 'wanita'){
      if(age >= 10 && age <= 12){
        var caloryNeed = 1900
        var proteinNeed = 55
        var fatNeed = 65
        var carbohydrateNeed = 280
        var fiberNeed = 27
      } else if(age >= 13 && age <= 15){
        var caloryNeed = 2050
        var proteinNeed = 65
        var fatNeed = 70
        var carbohydrateNeed = 300
        var fiberNeed = 29
      } else if(age >= 16 && age <= 18){
        var caloryNeed = 2100
        var proteinNeed = 65
        var fatNeed = 70
        var carbohydrateNeed = 300
        var fiberNeed = 29
      } else if(age >= 19 && age <= 29){
        var caloryNeed = 2250
        var proteinNeed = 60
        var fatNeed = 65
        var carbohydrateNeed = 360
        var fiberNeed = 32
      } else if(age >= 30 && age <= 49){
        var caloryNeed = 2150
        var proteinNeed = 60
        var fatNeed = 60
        var carbohydrateNeed = 340
        var fiberNeed = 30
      } else if(age >= 50 && age <= 64){
        var caloryNeed = 1800
        var proteinNeed =60
        var fatNeed = 50
        var carbohydrateNeed = 280
        var fiberNeed = 25
      } else if(age >= 65 && age <= 80){
        var caloryNeed = 1550
        var proteinNeed = 58
        var fatNeed = 45
        var carbohydrateNeed = 230
        var fiberNeed = 22
      } else if(age > 80){
        var caloryNeed = 1400
        var proteinNeed = 58
        var fatNeed = 40
        var carbohydrateNeed = 200
        var fiberNeed = 20
      }
    }

    const user = {
      uid: req.params.uid,
      name: req.body.name,
      email: req.body.email,
      birthDate: date,
      gender: gender,
      height: height,
      weight: weight,
      caloryNeed: caloryNeed,
      proteinNeed: proteinNeed,
      fatNeed: fatNeed,
      carbohydrateNeed: carbohydrateNeed,
    }
    
    await admin
      .firestore()
      .collection('users')
      .doc(req.params.uid)
      .set(user)
  }
  updateUser().then((user) => res.send(user))
})

module.exports = router
