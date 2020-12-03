FROM java:8
COPY . /
WORKDIR /
RUN javac Main.java
CMD ["java", "-classpath", "mysql-connector-java-8.0.22.jar:","Main"]
