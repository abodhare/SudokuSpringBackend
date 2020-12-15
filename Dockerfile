# select image
FROM maven AS build

WORKDIR /app

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies for offline use
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./src ./src

# COPY . .
# build for release
RUN mvn package

FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY --from=build /app/target/sudoku-0.0.1-SNAPSHOT.jar /opt/app
CMD ["java", "-jar", "/opt/app/sudoku-0.0.1-SNAPSHOT.jar"]