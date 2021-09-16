# Projetos feitos em quarkus
- Build imagem nativa
```
mvn package -Pnative -Dnative-image.docker-build=true
```
- Build no kubernetes
```
mvn clean package -Dquarkus.container-image.build=true -Dquarkus.kubernetes.deploy=true -Dquarkus.container-image.push=true
```
