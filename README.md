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
docker notes
###################################################
docker run --name mysql-container \
  -e MYSQL_ROOT_PASSWORD=admin123 \
  -p 3306:3306 \
  -d mysql:8.0


