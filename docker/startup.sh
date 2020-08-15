#!/bin/bash

[ -f /entrypoint/extra-commands.sh ] && sh /entrypoint/extra-commands.sh


export PATH="$JAVA_HOME/bin:$PATH"
export JAVA_OPTS="-Xms256M -Xmx256M -Djava.awt.headless=true -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9090"

echo "Starting application: '${APP_JAR}' with app-args '${APP_ARGS}' and jvm args '$JAVA_OPTS'"
java $JAVA_OPTS -jar /opt/spring-application/${APP_JAR} $APP_ARGS