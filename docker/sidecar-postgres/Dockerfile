FROM picoded/ubuntu-openjdk-8-jdk:latest

RUN apt-get update && \
	apt-get install -y postgresql postgresql-contrib curl vim

#RUN /etc/init.d/postgresql start && \
#    su postgres -c "createuser -d -r -s demo -P demo123" &&\
#    createdb demo_db &&\
#    /etc/init.d/postgresql stop
RUN service postgresql start &&\
  sudo -u postgres psql -c "CREATE USER demo WITH SUPERUSER PASSWORD 'demo123';" &&\
  sudo -u postgres psql -c "CREATE DATABASE demo_db;" &&\
  service postgresql stop

RUN echo "host all  all    0.0.0.0/0  md5" >> /etc/postgresql/9.5/main/pg_hba.conf
RUN echo "listen_addresses='*'" >> /etc/postgresql/9.5/main/postgresql.conf

ADD ./startup.sh /entrypoint/primer.sh
RUN echo "/etc/init.d/postgresql start" > /entrypoint/extra-commands.sh

RUN chmod 755 /entrypoint/primer.sh
RUN chmod 755 /entrypoint/extra-commands.sh
