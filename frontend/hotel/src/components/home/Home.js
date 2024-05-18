import React, { useState, useEffect } from 'react';
import { Carousel, Modal, Button, Form, FloatingLabel } from 'react-bootstrap';
import './Home.css';
import api from '../api/axiosConfig';
import RoomList from './RoomList';

const Home = ({ hotels }) => {
  const [userPosition, setUserPosition] = useState({ lat: null, lon: null });
  const [distanceFromUser, setDistanceFromUser] = useState([]);
  const [range, setRange] = useState(0);

  const [selectedHotel, setSelectedHotel] = useState(null);

  const [selectedRoom, setSelectedRoom] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(Boolean(localStorage.getItem("user")));

  const [showReservationModal, setShowReservationModal] = useState(false);
  const [reservationDate, setReservationDate] = useState('');
  const [reservationTime, setReservationTime] = useState('');

  const today = new Date().toISOString().split('T')[0];

  const toggleReservationModal = () => setShowReservationModal(!showReservationModal);

  // Haversine formula (from the internet)
  const calculateDistance = (lat1, lon1, lat2, lon2) => {
    const R = 6371; // Radius of the Earth in km
    const dLat = (lat2 - lat1) * (Math.PI / 180);
    const dLon = (lon2 - lon1) * (Math.PI / 180);
    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(lat1 * (Math.PI / 180)) *
      Math.cos(lat2 * (Math.PI / 180)) *
      Math.sin(dLon / 2) *
      Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const distance = R * c; // km
    return distance * 1000; // to meters
  };
  const filteredHotels =
    range > 0
      ? hotels.filter((hotel) => {
        if (distanceFromUser[hotel.id]) {
          return distanceFromUser[hotel.id].distance <= range;
        }
        return false;
      })
      : hotels;
  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          setUserPosition({ lat: pos.coords.latitude, lon: pos.coords.longitude });
        },
        (err) => {
          console.log(err);
        }
      );
    } else {
      console.warn('Geolocation is not supported by this browser.');
    }
  }, []);
  useEffect(() => {
    if (userPosition.lat && userPosition.lon) {
      const distances = {};
      hotels.forEach((hotel) => {
        const distance = calculateDistance(
          userPosition.lat,
          userPosition.lon,
          hotel.latitude,
          hotel.longitude
        );
        distances[hotel.id] = { id: hotel.id, distance };
      });
      setDistanceFromUser(distances);
    }
  }, [userPosition, hotels]);

  const handleReservation = async () => {
    if (!selectedRoom) return;
    if (!isLoggedIn) {
      alert("Please log in to make a reservation.");
      return;
    }

    try {
      const reservationRequest = {
        username: localStorage.getItem("user"),
        roomNumber: selectedRoom.roomNumber,
        type: selectedRoom.type,
        price: selectedRoom.price,
        date: reservationDate + 'T' + reservationTime,
      };

      const response = await api.post("/reservations", reservationRequest);
      console.log("Reservation created successfully:", response.data);
      toggleReservationModal();
    } catch (error) {
      console.error("Error creating reservation:", error);
      // Handle error
    }
  };
  const handleRoomSelect = (room) => {
    if (room.available) {
      setSelectedRoom(room);
      toggleReservationModal();
    } else {
      console.log("This room is not available for reservation.");
      alert("This room is not available for reservation.");

    }
  };

  return (
    <div>
      <header>
        {userPosition.lat && (
          <p>Your position: {userPosition.lat}, {userPosition.lon}</p>
        )}
        <input
          type="number"
          placeholder="Enter range in meters"
          value={range}
          onChange={(e) => setRange(parseInt(e.target.value))}
        />
      </header>
      <div className="hotel-carousel-container">
        {filteredHotels.length > 0 ? (
          <Carousel>
            {filteredHotels.map((hotel) => (
              <Carousel.Item key={hotel.id}>
                <div className="hotel-card-container">
                  <div className="hotel-card">
                    <div className="hotel-detail">
                      <div className="hotel-title">
                        <h3>{hotel.name}</h3>
                        {distanceFromUser[hotel.id] && (
                          <p>Distance from you: {distanceFromUser[hotel.id].distance.toFixed(0)} meters</p>
                        )}
                      </div>
                      <div className="hotel-buttons-container">
                        <div className="hotel-review-button-container">
                          <button className="btn btn-info" onClick={() => setSelectedHotel(hotel.id)} >Book a room</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </Carousel.Item>
            ))}
          </Carousel>
        ) : (
          <div className="no-hotels-message">
            <h2>Sorry, no hotel available this close</h2>
          </div>
        )}
        <div className="carousel-container">
          <Carousel controls={false}>
            {selectedHotel && <RoomList hotelId={selectedHotel} onRoomSelect={handleRoomSelect} />}
          </Carousel>
        </div>
      </div>
      <Modal show={showReservationModal} onHide={toggleReservationModal}>
        <Modal.Header closeButton>
          <Modal.Title>Make Reservation for Room {selectedRoom && selectedRoom.roomNumber}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <FloatingLabel controlId="reservationDate" label="Reservation Date">
              <Form.Control
                type="date"
                value={reservationDate}
                onChange={(e) => setReservationDate(e.target.value)}
                min={today}
              />
            </FloatingLabel>
            <FloatingLabel controlId="reservationTime" label="Reservation Time">
              <Form.Control
                type="time"
                value={reservationTime}
                onChange={(e) => setReservationTime(e.target.value)}
              />
            </FloatingLabel>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={toggleReservationModal}>Close</Button>
          <Button variant="primary" onClick={handleReservation}>Submit Reservation</Button>
        </Modal.Footer>
      </Modal>
    </div>
  );


}
export default Home;