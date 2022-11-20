FROM openjdk:latest
COPY . .
WORKDIR /src
RUN ["javac", "Main.java"]
ENTRYPOINT ["java","Main"]

