Java EE 6 Web Profile with continuous deployment
-----

This clickstart is a full Java EE 6 Web Profile application, with a database and continuous deployment.
Run this clickstart and it will generate a database, app, source repo and build service for you. 

This showcases JPA and persistence configuration EE 6 style. To make changes, clone your generated repo. 
Any changes you then push will be build and deployed.

This is an example of the jboss container in action.

Following are instructions if you want to set this up manually: 


This example shows how to setup an app that uses  persistence.xml with a CloudBees database.

Requirements
-----

* Install Maven 3.0.4+
* Sign up for an account at www.cloudbees.com
* Install the CloudBees SDK (for bees commands)


Instructions
------------

Get the source

    git clone git://github.com/swashbuck1r/jboss-db-example.git

Build the WAR file

    mvn package

Deploy the WAR file

    bees app:deploy -t jboss -a MYAPP_ID target/jboss-db-example.war

Create a database for the app

    bees db:create -u DB_USER -p DB_PASSWORD DBNAME

Bind the database to the app (using datasource alias "ExampleDS" defined in persistence.xml)

    bees app:bind -db DBNAME -a MYAPP_ID -as ExampleDS

Restart the app (to inject the new database binding)

    bees app:restart MYAPPID
