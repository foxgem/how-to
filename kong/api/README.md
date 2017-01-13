keytool -genseckey -keystore keystore.jceks -storetype jceks -storepass secret -keyalg HMacSHA256 -keysize 2048 -alias HS256 -keypass secret

curl -X POST -F "name=user1" -F "password=user" http://localhost:8080/login

curl -H "Authorization:Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidXNlcjIiLCJzdWIiOiIyIiwiYWRtaW4iOmZhbHNlLCJleHAiOjE0NzA4MDEzMzgsImlhdCI6MTQ3MDgwMTEzOH0=.SR1GGFQPphL8PXdfNWe8MvTKm5-zkf8Ke2VAgTLIHR8=" http://localhost:8080/summary