# Use the official PostgreSQL image as the base image
FROM postgres:latest

# Set environment variables for the PostgreSQL database
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=medicaldb

# Copy initialization scripts to the Docker image
# These scripts will be executed when the container starts
COPY init.sql /docker-entrypoint-initdb.d/

# Expose PostgreSQL port
EXPOSE 5432