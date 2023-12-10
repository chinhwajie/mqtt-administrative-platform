FROM nginx:1.18

WORKDIR /usr/mqtt

COPY ./dist/mqtt-admin-ui/ /usr/mqtt/
COPY ./default.conf /etc/nginx/conf.d/default.conf

RUN chmod -R +r /assets