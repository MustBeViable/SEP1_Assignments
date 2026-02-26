FROM maven:latest
LABEL authors="eliasrinne"

WORKDIR /app

copy pom.xml .

COPY . /app
RUN mvn package
CMD ["java", "-jar", "target/laskin"]

