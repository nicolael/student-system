To groupteacher :

I used postgres in this proect. There is one thing I want to mention, in order to pass the Junit tests you might have to run 'mvn jetty:run' before you run 'mvn clean install'. Otherwise you will run into errors like 'Error executing DDL via JDBC Statement' saying that is "Caused by: org.postgresql.util.PSQLException: ERROR: relation 'courses_students' does not exist”. 
This is because the database has not created the relation "courses_students" yet. 

Therefore run 'mvn jetty:run' first to create all the necessary tables! and then run 'mvn test'

The map loads slow sometimes, so be patient



This is how my database looks like :
after clicking on 'init database' in the GUI

inf5750=# \dt
             List of relations
 Schema |       Name       | Type  | Owner 
--------+------------------+-------+-------
 public | courses          | table | dhis
 public | course_students | table | dhis
 public | students         | table | dhis
(3 rows)

inf5750=# SELECT * from courses;
 course_id |                 name                  | coursecode 
-----------+---------------------------------------+------------
         3 | Open Source Development               | INF5750
         4 | Health management information systems | INF5761
(2 rows)

inf5750=# SELECT * from students;
 student_id |     name     |  longitude  |  latitude   
------------+--------------+-------------+-------------
          1 | John Clark | no location | no location
          2 | James Jampa  | no location | no location
(2 rows)

inf5750=# 
