import React from 'react';
import { Grid, Typography } from '@mui/material';

const RoomCard = ({ room }) => {
  return (
    <Grid item key={room.id} xs={12} sm={6} md={4}>
      {room.available ? (
        <div className="room-card">
          <Typography variant="h6">Room number: {room.roomNumber}</Typography>
          <Typography variant="body1">Price: ${room.price}</Typography>
          <Typography variant="body2">Type: {room.type}</Typography>
        </div>
      ) : (
        <div className="room-card">
          <Typography variant="h6">The room {room.roomNumber} is not available</Typography>
        </div>
      )}
    </Grid>
  );
};

export default RoomCard;
