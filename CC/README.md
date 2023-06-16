# test-api-capstone


## Getting Started
To run this project
```
npm install
npm start
```

## Endpoint
```
POST localhost:3000/register
GET localhost:3000/users/:uid
PUT localhost:3000/update/:uid

POST localhost:3000/records/:uid
GET localhost:3000/records/:uid
GET localhost:3000/records/:uid/:recordId
GET localhost:3000/records/:uid/date/:date
```

## ID Token
To make request you should have Firebase ID Token

To obtain Firebase ID Token, you need to login via client app

or

Obtain Firebase ID Token by POSTMAN via
```
Method : POST
url : https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key=WEB_API_KEY
Body : {"email":"emai you want to login","password":"password you want to login","returnSecureToken":true}
```
You can get WEB API Key firebase from Project Settings => General => Web API Key

## Request
After have an ID Token, make request throught endpoint above and also don't forget to include the ID Token by Bearer Token Authorization 

