FROM openjdk:22-ea-20-slim-bullseye

# Set the working directory to /app
WORKDIR /app/build

# Copy the current directory contents into the container at /app
COPY ./build /app/build

COPY start_resource.sh /app

EXPOSE 8081

WORKDIR /app

CMD [ "bash", "start_resource.sh" ]