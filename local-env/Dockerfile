FROM mysql:8.3.0

ENV MYSQL_ROOT_PASSWORD password
ENV MYSQL_USER user
ENV MYSQL_PASSWORD password

COPY ./createSchema.sql /docker-entrypoint-initdb.d