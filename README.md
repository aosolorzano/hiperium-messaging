Hiperium Project: Platform for the Internet of Things.
========================
Author: Andres Solorzano
Level: Advanced  
Technologies: CDI, RESTful, JPA, EJB, JTA, NoSQL
Summary: This project is the messaging service that gets and send JMS between the smart home and the Hiperium Cloud Platform.  
Target Product: JBoss Application server 7.1.1
Source: <https://bitbucket.org/aosolorzano/hiperium-messaging>  

What is it?
-----------

The Hiperium Messaging is a service of the hiperium Project for the Internet of Things. This messaging project sends and receives JMS to or from the devices of the smart homes respectively. The Queues receive messages from a smart home and its sending again to the control microservice to operate the respective action. The Topics send the messages generated from the user through the control microservice and sends this message to the respective smart home.

System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Application server 7.1.1.

All you need to build this project is Java 7.0 (Java SDK 1.7), Maven 3.0 or later.


Start the JBoss Wildfly Server
-------------------------

1. Open a command prompt and navigate to the root of the JBoss Wildfly directory.
2. The following shows the command line to start the server:

        For Linux:   JBOSS_HOME/bin/standalone.sh --server-config=standalone-full.xml
        For Windows: JBOSS_HOME\bin\standalone.bat --server-config=standalone-full.xml


Access the application 
---------------------

The application will service many Queues and Topics to receive or send JMS.Queues receive messages from the smart home application through a Raspberry PI device that collects users activities. Topics send messages the respective smart home's Raspberry PI device.
