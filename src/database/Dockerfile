FROM mysql:8.0.35

ENV MYSQL_ROOT_PASSWORD=admin
ENV MYSQL_DATABASE=mqtt
ENV MYSQL_USER=dev
ENV MYSQL_PASSWORD=dev

RUN /bin/bash && \
    mysqld && \
    mysql -e "CREATE DATABASE keycloak;" && \
    mysql -e "GRANT ALL PRIVILEGES ON keycloak.* TO 'dev'@'localhost';"

