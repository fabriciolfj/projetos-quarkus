curl -X POST http://localhost:8080/auth/realms/quarkus-realm/protocol/openid-connect/token \
 --user quarkus-client:c09ab027-948b-425a-b5f6-03051a5159cb \
 -H 'content-type: application/x-www-form-urlencoded' \
 -d 'username=admin&password=admin&grant_type=password'
