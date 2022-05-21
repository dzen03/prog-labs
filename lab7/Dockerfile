FROM openjdk:18

EXPOSE 10000/udp

COPY /build/libs/lab7-1.0-SNAPSHOT.jar /usr/src/myapp/lab7-1.0-SNAPSHOT.jar

WORKDIR /usr/src/myapp
CMD ["java", "-jar", "lab7-1.0-SNAPSHOT.jar", "-s"]