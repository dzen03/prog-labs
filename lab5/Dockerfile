FROM openjdk:16



COPY /build/libs/lab5-1.0-SNAPSHOT.jar /usr/src/myapp/lab5-1.0-SNAPSHOT.jar
COPY /input.csv /usr/src/myapp/input.csv
WORKDIR /usr/src/myapp
CMD ["java", "-jar", "lab5-1.0-SNAPSHOT.jar", "input.csv"]