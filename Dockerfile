FROM gcr.io/google-appengine/openjdk
RUN mkdir /home/app
COPY build/libs/petsrego.jar /home/app
EXPOSE 8080
CMD ["java", "-jar", "/home/app/petsrego.jar"]