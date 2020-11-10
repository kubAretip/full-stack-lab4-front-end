FROM gradle:jdk11 as builder

RUN git clone https://github.com/kubAretip/full-stack-lab4-front-end.git

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11.0-jre-slim

EXPOSE 8081

RUN mkdir /app

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/todo-web.jar

CMD ["java","-jar","/app/todo-web.jar"]