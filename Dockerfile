# Usa imagem do OpenJDK 17
FROM openjdk:17-jdk-slim

# Cria um diretório dentro do container
WORKDIR /app

# Copia o arquivo JAR gerado para dentro do container
COPY target/myencurter-*.jar app.jar

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
