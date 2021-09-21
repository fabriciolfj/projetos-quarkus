# Projetos feitos em quarkus
- Build imagem nativa
```
mvn package -Pnative -Dnative-image.docker-build=true
```
- Build no kubernetes
```
mvn clean package -Dquarkus.container-image.build=true -Dquarkus.kubernetes.deploy=true -Dquarkus.container-image.push=true
```
- Check health da aplicação
```
http://localhost:8080/q/health
http://localhost:8080/q/health/live
http://localhost:8080/q/health/ready
http://localhost:8080/q/metrics/application
```
#### Fault tolerance api
- Timeout: define um tempo de execução.
- Fallback: provem uma alternativa ou diante a uma chamada ao serviço com falha
- Retry: executa tentativas, diante uma estratégia
- Bulkhead: isola  parte com falha, enquanto o restante do serviço continua funcionando.
- Circuite breaker: define critérios de respostas a erro de forma rapida.
- Asynchronous: invoca uma operação assincrona.

#### Gerar um arquivo properties de exemplo, com todas as propriedades baseadas nas dependências do projeto
```
mvn quarkus:generate-config
```
