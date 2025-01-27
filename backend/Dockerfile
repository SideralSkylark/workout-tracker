# Use a lightweight base image
FROM debian:bullseye-slim

# Install required tools (curl, Maven, JDK)
RUN apt-get update && apt-get install -y \
    curl \
    maven \
    gnupg2 \
    ca-certificates \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN curl -fsSL https://download.oracle.com/java/23/latest/jdk-23_linux-x64_bin.deb -o /tmp/jdk-23.deb \
&& dpkg -i /tmp/jdk-23.deb \
# Find the installed JDK directory dynamically and rename it
&& JDK_DIR=$(find /usr/lib/jvm -type d -name "jdk-23*" | head -n 1) \
&& mv "$JDK_DIR" /usr/lib/jvm/jdk-23 \
&& rm /tmp/jdk-23.deb

ENV JAVA_HOME=/usr/lib/jvm/jdk-23
ENV PATH="$JAVA_HOME/bin:$PATH"

# Set working directory
WORKDIR /app

# Copy application files
COPY pom.xml .         
COPY src ./src         

# Build the application
RUN mvn clean package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Copy the built JAR to the final container image
RUN cp target/*.jar app.jar

# Run the application
CMD ["java", "-jar", "app.jar"]
