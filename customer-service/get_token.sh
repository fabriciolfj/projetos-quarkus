curl -X POST http://localhost:8080/auth/realms/quarkus-realm/protocol/openid-connect/token \
 --user quarkus-client:0e3f6175-8155-4250-bf86-fdff6391d695 \
 -H 'content-type: application/x-www-form-urlencoded' \
 -d 'username=admin&password=admin&grant_type=password'
