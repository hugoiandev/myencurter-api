# Etapa 1: builder com Maven
FROM maven:3.9.4-eclipse-temurin-17 AS builder

WORKDIR /app

# Copia todos os arquivos da aplicação para o container
COPY . .

# Faz o build com Maven, ignorando os testes
RUN mvn clean package -DskipTests

# Etapa 2: imagem final com apenas o JAR e o JDK
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o JAR gerado da etapa anterior
COPY --from=builder /app/target/myencurter-*.jar app.jar

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

