FROM openjdk:11

COPY build/libs/desafio-0.0.1-SNAPSHOT.jar /app/desafio-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/desafio-0.0.1-SNAPSHOT.jar"]

#docker build -t api-desafio-capgemini .
#docker run -p -rm 8080:8080 api-desafio-capgemini
#docker





