Hiperium Project: Platform for the Internet of Things.
========================
Author: Andres Solorzano
Level: Advanced  
Technologies: CDI, RESTful, JPA, EJB, JTA, NoSQL
Summary: This project is the messaging service that gets and send JMS between the smart home and the Hiperium Cloud Platform.  
Target Product: Wildfly Application Server 10.
Source: <https://bitbucket.org/aosolorzano/hiperium-messaging>  

What is it?
-----------

The Hiperium Messaging is a service of the hiperium Project for the Internet of Things. This messaging project sends and receives JMS to or from the devices of the smart homes respectively. The Queues receive messages from a smart home and its sending again to the control microservice to operate the respective action. The Topics send the messages generated from the user through the control microservice and sends this message to the respective smart home.

System requirements
-------------------

The application this project produces is designed to be run on Wildfly Application Server 10.

All you need to build this project is Java 7.0 (Java SDK 1.7), Maven 3.0 or later, and Docker 1.10 or later.


Docker Image
-------------------

This repository contains the instructions needed to create a docker image based on the Hiperium Messaging Service.


Dependencies
-------------------

Docker Engine


Deploying
-------------------

Execute the following commands to run the docker image in your host computer:

* docker pull hiperium/hiperium-messaging
* docker run -it -d hiperium/hiperium-messaging


Access the application 
---------------------

The application will service many Queues and Topics to receive or send JMS.Queues receive messages from the smart home application through a Raspberry PI device that collects users activities. Topics send messages the respective smart home's Raspberry PI device.
