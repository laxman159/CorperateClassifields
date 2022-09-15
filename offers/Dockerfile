# FROM openjdk:8-jdk-alpine
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]


FROM --platform=linux/x86-64 amazoncorretto:11
EXPOSE 8000
ADD target/offerService-0.0.1.jar offerService.jar
CMD ["java","-jar","/offerService.jar"]