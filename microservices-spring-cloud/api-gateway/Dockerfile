# to build this go to the folder api-gateway and run docker build -t apigateway-dockerfile .
# "." at the end means "current folder" "-t" stands for tag

# base docker image
FROM openjdk:17
# copy target folder into the container
COPY target/*.jar app.jar
# whenever the container is starting up it will run command java -jar and it will point to "app.jar"
ENTRYPOINT ["java", "-jar", "/app.jar"]