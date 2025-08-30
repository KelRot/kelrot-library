# Makefile for FRC robot project

# Build robot code
build:
	./gradlew build --stacktrace

# Run unit tests
test:
	./gradlew test

# Check lint (checkstyle)
lint-check:
	./gradlew checkStyleMain
# You can see the errors on build/reports/checkstyle, open the html file on browser then you can fix it easily.