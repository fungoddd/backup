FROM java:8
VOLUME /tmp
ADD demo-service.jar app.jar
ADD skywalking-agent skywalking-agent
# set timezone
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone
ENV JAVA_OPTIONS=''
ENV JAVA_ARGS=''
CMD java $JAVA_OPTIONS -jar /app.jar $JAVA_ARGS
