# Φάση 1 BUILD, αντιγραφή αρχείων, κώδικα και εξαρτήσεων για build και αντιγραγή του jar της εφαρμογής.

FROM maven:3.8.7-openjdk-17 AS builder
 
WORKDIR /workspace


COPY pom.xml .
COPY src ./src


RUN mvn -B -DskipTests package


# -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


# Φάση 2 RUN, χρησιμοποιηση μιας ελαφριά εικόνας για την εκτέλεση, δημιουργία μη ριζικού χρήστη,υλοποίηση ελάχιστου layer, αντιγραφή του εκτελέσιμου JAR, έκθεση θύρας υπηρεσίας, εκκίνηση εφαρμογής  


FROM eclipse-temurin:17-jre-jammy


RUN groupadd -r restgroup && useradd -r -g restgroup restuser
 
WORKDIR /app


WORKDIR /opt/app


COPY --from=builder /workspace/target/citizen-registry-service-1.0.0.jar app.jar


RUN chown restuser:restgroup app.jar
 

USER restuser


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.jar"]
