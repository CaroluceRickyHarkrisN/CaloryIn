const admin = require('../config/firebase-config');
module.exports = {
  verifyLogin: function(req, res, next){
    const authHeader = req.headers.authorization;
    if (authHeader) {
      const idToken = authHeader.split(" ")[1];
      admin
      .auth()
      .verifyIdToken(idToken)
      .then(function (decodedToken) {
        return next();
      })
      .catch(function (error) {
        console.log(error);
        return res.sendStatus(403);
      });
    } else {
      res.sendStatus(401);
    }
  },
}