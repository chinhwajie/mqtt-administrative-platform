FROM nginx:1.18

# Set the working directory for the nginx configurations
WORKDIR /etc/nginx/sites-available

# Copy the nginx site configuration
COPY vertical .

# Create a symbolic link to enable the site
WORKDIR /etc/nginx/sites-enabled
RUN ln -s /etc/nginx/sites-available/vertical .

# Set the working directory for the application files
WORKDIR /var/www

# Copy the application files to the container
COPY dist/mqtt-admin-ui /var/www/mqtt-admin-ui

# Expose the port used by your application
EXPOSE 4200

# Start Nginx in the background
CMD ["nginx", "-g", "daemon off;"]
