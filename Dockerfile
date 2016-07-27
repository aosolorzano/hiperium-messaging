# Based on Ubuntu image
FROM jboss/wildfly
MAINTAINER "Andres Solorzano" andres.solorzano@hiperium.com

# Set the JBOSS_HOME env variable
ENV JBOSS_HOME /opt/jboss/wildfly

# Copy Wildfly messaging configuration
COPY opt/configure-jms.cli opt/install-standalone.cli $JBOSS_HOME/

# Copy hiperium service files into Wildfly
COPY resources/standalone-full-ha.xml $JBOSS_HOME/standalone/configuration/

# Creates the wildlfy user administrator
RUN $JBOSS_HOME/bin/add-user.sh admin admin123 --silent

# Expose the ports we're interested in
EXPOSE 8080 9990

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-full.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-Djboss.node.name=messaging-node1"]
