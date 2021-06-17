
FROM adoptopenjdk/openjdk11:alpine-jre
 
ENV APP_HOME /home
RUN mkdir -p $APP_HOME
 
ADD build/libs/labeler-0.0.1.jar $APP_HOME/app.jar
 
WORKDIR $APP_HOME

COPY src/main/resources /home/conf

CMD ["java", "-jar", "/home/app.jar", "--spring.config.location=/home/conf/", "--server.port=8080"]
