# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim
#FROM container-registry.oracle.com/java/jdk:17.0.13

# Set the working directory inside the container
WORKDIR /app

# Copy the current directory contents into the container
COPY target/deploy/* /app/jars/

# Compile the Java program
#RUN javac org/learnbyexample/HelloWorld.java

# Define the command to run your program
#CMD ["java", "-cp", "/app/jars/dockerk8s-1.0-SNAPSHOT.jar", "org.learnbyexample.HelloWorld"]
CMD ["sh", "-c", "java -cp /app/jars/dockerk8s-1.0-SNAPSHOT.jar $JAVA_OPTS org.learnbyexample.HelloWorld $APP_ARGS"]

#CMD java -cp /app/jars/dockerk8s-1.0-SNAPSHOT.jar $JAVA_OPTS org.learnbyexample.HelloWorld $APP_ARGS