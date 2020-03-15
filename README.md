# spring_react

Ongoing redesign of home site.

Project is a Spring boot application with a React front-end. 

The application uses 2-way SSL to authenticate users, and then provides authorization depending on the CN of the user's certificate, and what role that user has been provisioned in the DB. 

DB is just a simple mysql instance, with two tables: Users, and Images.

The site provides pages to control the state of: the front door, multiple lights throughout the apartment, two motion activated streams, user creation/deletion, logging, and seeking through previously recording motion events.

Jenkins runs remotely and triggers (via githook) a CICD pipeline to deploy to production when code merges to master. 

App runs on an EC2 t2-micro instance, with a Route53 domain name. S3 is used to store secrets, and pulled at runtime.
 
