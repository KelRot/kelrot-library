# Makefile for FRC robot project

# Build robot code
build:
	./gradlew build --stacktrace

# Run unit tests
test:
	./gradlew test

# Check lint (Spotless)
lint-check:
	./gradlew spotlessCheck

# (Optional) Auto-fix lint
lint-fix:
	./gradlew spotlessApply