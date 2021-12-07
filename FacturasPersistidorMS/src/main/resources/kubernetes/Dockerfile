FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
RUN echo "Creando la imagen de la facturaspersistidorms"
ADD facturaspersistidorms.jar app.jar
RUN apk add -U tzdata
RUN cp /usr/share/zoneinfo/Europe/Madrid /etc/localtime
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
