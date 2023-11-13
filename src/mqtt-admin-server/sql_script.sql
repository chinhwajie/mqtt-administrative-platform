create
    user 'dev'@'%' identified by 'dev';
grant all privileges on mqtt.* to
    'dev'@'%';