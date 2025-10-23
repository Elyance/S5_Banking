# Utilise une image de base avec Java 17 (compatible avec WildFly 37)
FROM eclipse-temurin:17-jre

# Télécharge et installe WildFly 37
RUN wget https://github.com/wildfly/wildfly/releases/download/37.0.1.Final/wildfly-37.0.1.Final.tar.gz \
    && tar -xzf wildfly-37.0.1.Final.tar.gz \
    && mv wildfly-37.0.1.Final /opt/wildfly \
    && rm wildfly-37.0.1.Final.tar.gz

# Copie le WAR dans le dossier de déploiement
COPY target/change.war /opt/wildfly/standalone/deployments/

# Expose les ports (6060 pour l'app, 9990 pour admin)
EXPOSE 6060 9990

# Démarre WildFly
CMD ["/opt/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-Djboss.http.port=6060"]