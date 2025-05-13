FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/blog-app.jar blog-app.jar
ENTRYPOINT [ "java","-jar","/blog-app.jar"]