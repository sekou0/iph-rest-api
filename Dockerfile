FROM openldap:latest
COPY ./target/rest-api*.jar ./app.jar
CMD java -jar ./app.jar
