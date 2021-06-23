FROM java:11
VOLUME /tmp
ADD target/foodrecipe.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]