# Utilise une image de base avec Java 17 (compatible avec WildFly 37)
FROM eclipse-temurin:17-jre

# Télécharge et installe WildFly 31
RUN wget https://github.com/wildfly/wildfly/releases/download/31.0.0.Final/wildfly-31.0.0.Final.tar.gz \
    && tar -xzf wildfly-31.0.0.Final.tar.gz \
    && mv wildfly-31.0.0.Final /opt/wildfly \
    && rm wildfly-31.0.0.Final.tar.gz

# Copie le WAR dans le dossier de déploiement
COPY target/change.war /opt/wildfly/standalone/deployments/

# Expose les ports (8080 pour l'app, 9990 pour admin)
# Expose les ports (6060 pour l'app, 8080 pour remoting EJB, 9990 pour admin)
EXPOSE 8080 9990 4447   

# Démarre WildFly
CMD ["/opt/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-Djboss.http.port=8080", "-Djboss.management.http.port=9990"]