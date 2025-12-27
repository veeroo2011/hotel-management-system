const BASE_URL = "http://localhost:8080";

// Create Hotel
function createHotel() {
    fetch(`${BASE_URL}/hotels`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            name: document.getElementById("hotelName").value,
            city: document.getElementById("hotelCity").value,
            rating: document.getElementById("hotelRating").value
        })
    }).then(res => alert("Hotel Created"));
}

// Create Room
function createRoom() {
    fetch(`${BASE_URL}/rooms`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            roomNumber: document.getElementById("roomNumber").value,
            type: document.getElementById("roomType").value,
            price: document.getElementById("roomPrice").value,
            hotel: {
                id: document.getElementById("hotelId").value
            }
        })
    }).then(res => alert("Room Created"));
}

// Check Availability
function checkAvailability() {
    const hotelId = document.getElementById("checkHotelId").value;
    const checkIn = document.getElementById("checkIn").value;
    const checkOut = document.getElementById("checkOut").value;

    fetch(`${BASE_URL}/rooms/hotel/${hotelId}/available?checkIn=${checkIn}&checkOut=${checkOut}`)
        .then(res => res.json())
        .then(data => {
            const list = document.getElementById("roomList");
            list.innerHTML = "";
            data.forEach(room => {
                const li = document.createElement("li");
                li.innerText = `Room ${room.roomNumber} - ${room.type} - ₹${room.price}`;
                list.appendChild(li);
            });
        });
}
