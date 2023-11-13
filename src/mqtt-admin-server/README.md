In order to get access token:

```
curl \
-X POST \
"http://localhost:8080/realms/mqtt/protocol/openid-connect/token" \
-H "Content-Type: application/x-www-form-urlencoded" \
-d "username=chinhwajie&password=[password]&grant_type=password&client_id=mqtt-admin-ui"
```

