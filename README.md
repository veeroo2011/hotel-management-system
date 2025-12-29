####################################################
# api details
####################################################
create hotel 
curl -X POST http://localhost:8080/hotels \
 -H "Content-Type: application/json" \
 -d '{"name":"Taj Hotel","city":"Mumbai","rating":5.0}'


get all hotel
curl http://localhost:8080/hotels


Create Room under Hotel
curl -X POST http://localhost:8080/rooms \
 -H "Content-Type: application/json" \
 -d '{"roomNumber":"101","type":"Deluxe","price":2500,"hotel":{"id":1}}'

list all room
curl http://localhost:8080/rooms


Get Rooms for Hotel ID 1
curl http://localhost:8080/rooms/hotel/1

Count Rooms in Hotel ID 1
curl http://localhost:8080/rooms/hotel/1/count

Check Room Availability
curl "http://localhost:8080/rooms/hotel/1/available?checkIn=2025-12-01&checkOut=2025-12-02"


Create a Booking
You must pass room ID:
curl -X POST http://localhost:8080/bookings \
 -H "Content-Type: application/json" \
 -d '{
        "customerName":"Rahul",
        "checkIn":"2025-12-01",
        "checkOut":"2025-12-03",
        "room":{"id":1}
     }'


list all bookings
curl http://localhost:8080/bookings

###################################################
# database validation                             #
###################################################



SHOW DATABASES;
CREATE DATABASE hoteldb;


Validate Database
check database
use hoteldb;
SELECT * FROM hotel;
SELECT * FROM room;
SELECT * FROM booking;
delete from hotel where id in (1,2);

run mysql-container and set root password as admin123
######################################################
# run mysql and java-app in same network             #
######################################################
docker network create -d bridge mynet 
docker run -itd --net mynet --name mysql-container   -e MYSQL_ROOT_PASSWORD=admin123   -p 3306:3306 mysql:8.0

docker run -itd --net mynet --name java-app -p 8080:8080 veeroo2011/hotel-mgmt:1.1

docker exec mysql_container mysqldump -u root -p hoteldb > db.sql


#######################################################
# set user and pasword in mysql and take database dump
#######################################################
login to container 
docker exec -it mysql-container sh

create config file 
vi /root/.my.cnf
[client]
user=root
password=admin123

or 

cat > /root/.my.cnf <<EOF
[client]
user=root
password=admin123
EOF

chmod 600 /root/.my.cnf

taking dump and save to /tmp
mysqldump hoteldb > /tmp/db.sql

copy dump to host machine

docker cp mysql-container:/tmp/db.sql db.sql


docker run --name mysql-container \
  -e MYSQL_ROOT_PASSWORD=admin123 \
  -p 3306:3306 \
  -d mysql:8.0



veeroo2011/hotel-mgmt:1.8 ==>  application.properties is removed and docker
veeroo2011/hotel-mgmt:1.10 ==> application.properties is put but used as template so need to pass environemnt at runtime


how to provide these value to container

Method 1. create application.properties and springboot will automatically look for these file and load 

spring.datasource.url=jdbc:mysql://mysql-container:3306/hoteldb?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=admin123
spring.jpa.hibernate.ddl-auto=update
server.port=8080

Method-2 remove application.properties file and provide these value as environemnt variable to container 

docker run -itd \
  --net mynet \
  --name java-app \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://mysql-container:3306/hoteldb?useSSL=false&allowPublicKeyRetrieval=true" \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=admin123 \
  -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
  -e SERVER_PORT=8080 \
  -p 8080:8080 \
  veeroo2011/hotel-mgmt:1.8

Method-3 keep application.properties as template but provide these value from environemnt variable passing in docker container

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
server.port=${APP_PORT}



docker run -itd \
 --net mynet \
 -e DB_URL="jdbc:mysql://mysql-container:3306/hoteldb?useSSL=false&allowPublicKeyRetrieval=true" \
 -e DB_USER=root \
 -e DB_PASSWORD=admin123 \
 -e APP_PORT=8080 \
 --name java-app \
 -p 8080:8080 \
 veeroo2011/hotel-mgmt:1.9
 
Method-4ethod-4 create application.properties in local machine and attach this file to container as docker volume
 
docker run -itd \
 --net mynet \
 --name java-app \
 -p 8080:8080 \
 -v /home/ec2-user/application.properties:/config/application.properties
 veeroo2011/hotel-mgmt:1.8

docker-compose will work for image version 1.10
