FROM openjdk:17-oracle

WORKDIR /opt

COPY target/EnsetKB-0.0.1-SNAPSHOT.jar /opt/corp.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "corp.jar" ]