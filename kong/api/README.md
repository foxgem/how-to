keytool -genseckey -keystore keystore.jceks -storetype jceks -storepass secret -keyalg HMacSHA256 -keysize 2048 -alias HS256 -keypass secret

curl -X POST -F "name=user1" -F "password=user" http://localhost:8080/login

curl -H "Authorization:Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidXNlcjEiLCJzdWIiOiIxIiwiYWRtaW4iOmZhbHNlLCJpYXQiOjE0NzA3NDcxODl9.1lerPaUWjE4zNJjoDvwTqda9G4iF5w_Ng-X1HkUKZiA=" http://localhost:8080/summary