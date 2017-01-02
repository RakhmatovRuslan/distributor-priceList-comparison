Distributor`s price list comparison
====================
..........

Run Local (Embedded Tomcat):
----------------
  - execute: *mvn tomcat7:run*
  - Then open the URL: http://localhost:8080/

Run inside tomcat container
-----------------------------
Package it with maven

  - execute: *mvn clean package*
  - execute: java -jar price-list.jar -httpPort 8081
  - Then open the URL: http://localhost:8081
