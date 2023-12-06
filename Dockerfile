FROM node:21

# Install additional tools
RUN apt-get update && apt-get install -y \
    build-essential \
    git 

# Set the working directory
WORKDIR /app

# Copy the application code into the container
COPY . .

# Expose ports
EXPOSE 4200 8080 8081

WORKDIR /app/src/mqtt-admin-ui/
RUN npm install
CMD ["npm", "run", "start"]