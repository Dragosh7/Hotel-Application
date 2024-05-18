import React, { useState, useEffect } from 'react';
import { Grid, Typography } from '@mui/material';
import api from '../api/axiosConfig'; 

const RoomList = ({ hotelId, onRoomSelect }) => {
  const [rooms, setRooms] = useState([]);

  useEffect(() => {
    const fetchRooms = async () => {
      try {
        const response = await api.get(`/hotels/${hotelId}`);
        setRooms(response.data.rooms);
      } catch (error) {
        console.error('Error fetching rooms:', error);
      }
    };

    fetchRooms();
  }, [hotelId]);

  if (!rooms || rooms.length === 0) {
    return <div>No rooms available</div>;
  }

  return (
    <Grid container spacing={3}>
      {rooms.map((room) => (
        <Grid item key={room.id} xs={12} sm={6} md={4}>
          <div className="room-card" onClick={() => onRoomSelect(room)}>
            {room.available ? (
              <>
                <Typography variant="h6">Room number: {room.roomNumber}</Typography>
                <Typography variant="body1">Price: ${room.price}</Typography>
                <Typography variant="body2">Type: {room.type}</Typography>
              </>
            ) : (
              <Typography variant="h6">This room {room.roomNumber} is not available</Typography>
            )}
          </div>
        </Grid>
      ))}
    </Grid>
  );
};

export default RoomList;
