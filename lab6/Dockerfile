FROM openjdk:18

EXPOSE 10000/udp

COPY /build/libs/lab6-1.0-SNAPSHOT.jar /usr/src/myapp/lab6-1.0-SNAPSHOT.jar
COPY /input.csv /usr/src/myapp/input.csv
COPY /test.txt /usr/src/myapp/test.txt

WORKDIR /usr/src/myapp
CMD ["java", "-jar", "lab6-1.0-SNAPSHOT.jar", "input.csv"]