# ── Stage 1: build ──────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /workspace

# Cache dependency downloads separately from source changes
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 \
    mvn -f pom.xml dependency:go-offline -B 2>/dev/null || true

COPY . .
RUN --mount=type=cache,target=/root/.m2 \
    mvn -f pom.xml package -DskipTests -B

# ── Stage 2: runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=build /workspace/target/*.jar app.jar

# Render sets PORT; the app reads it via server.port=${PORT:8080}
EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
