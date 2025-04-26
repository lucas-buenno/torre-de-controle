# Estágio de build
FROM eclipse-temurin:21 AS build

WORKDIR /app

# Copia o Maven Wrapper e o pom.xml
COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .

# Dá permissão de execução ao mvnw
RUN chmod +x mvnw

# Copia o código-fonte
COPY src ./src

# Instala as dependências e faz o build
RUN ./mvnw clean package -DskipTests

# Estágio final
FROM eclipse-temurin:21

WORKDIR /app

# Copia o JAR do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Cria um usuário não-root
RUN useradd -m control_tower
USER 1234

# Expõe a porta
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]