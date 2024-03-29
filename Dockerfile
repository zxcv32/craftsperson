# Stage that builds the application, a prerequisite for the running stage
FROM maven:3-openjdk-16-slim as build
RUN curl -sL https://deb.nodesource.com/setup_14.x | bash -
RUN apt-get update -qq && apt-get install -qq --no-install-recommends nodejs

# Stop running as root at this point
RUN useradd -m crafter
WORKDIR /usr/src/app/
RUN chown crafter:crafter /usr/src/app/
USER crafter

# Copy pom.xml and prefetch dependencies so a repeated build can continue from the next step with existing dependencies
COPY --chown=crafter pom.xml ./
RUN mvn dependency:go-offline -Pproduction

# Copy all needed project files to a folder
COPY --chown=crafter:crafter src src
COPY --chown=crafter:crafter frontend frontend
COPY --chown=crafter:crafter package.json ./

# Using * after the files that are autogenerated so that so build won't fail if they are not yet created
COPY --chown=crafter:crafter package-lock.json* pnpm-lock.yaml* webpack.config.js* ./


# Build the production package, assuming that we validated the version before so no need for running tests again
RUN mvn clean package -DskipTests -Pproduction

# Running stage: the part that is used for running the application
FROM openjdk:16-jdk-slim
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
RUN useradd -m crafter
USER crafter
EXPOSE 8080
CMD java -jar /usr/app/app.jar
