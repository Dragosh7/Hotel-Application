import React, { useState, useEffect } from 'react';
import { TextField, Button, Container, Typography, Box, FormControl, InputLabel, Select, MenuItem, Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import api from '../api/axiosConfig';
const AddRoomForm = () => {
  const [roomData, setRoomData] = useState({
    hotelId: '',
    roomNumber: 0,
    type: 1,
    price: '',
    isAvailable: true
  });
  const [hotels, setHotels] = useState([]);
  const [open, setOpen] = React.useState(false);
  useEffect(() => {
    const fetchHotels = async () => {
      try {
        const response = await api.get('/hotels');
        setHotels(response.data);
      } catch (error) {
        console.error('Error fetching hotels:', error);
      }
    };

fetchHotels();

  }, []);
  const handleChange = (e) => {
    const { name, value } = e.target;

if (name === 'type') {
  setRoomData({ ...roomData, type: parseInt(value) });
  return;
}

setRoomData({ ...roomData, [name]: value });

  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const userRole = localStorage.getItem("role");
    if (userRole !== 'STAFF' && userRole !== 'ADMIN') {
      alert("Only staff or admin can add hotels.");
      return;
    }

try {
  const response = await api.post('/rooms', roomData);
  console.log('Room added successfully:', response.data);
  alert("Room added successfully");

  setOpen(true);
} catch (error) {
  console.error('Error adding room:', error);
}

  };
  const handleClose = () => {
    setOpen(false);
  };
  return (
    <Container maxWidth="sm">
      <Box component="form" onSubmit={handleSubmit} sx={{ mt: 5 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Add New Room
        </Typography>
        <FormControl fullWidth margin="normal" required>
          <InputLabel id="hotelId-label">Select Hotel</InputLabel>
          <Select
            labelId="hotelId-label"
            id="hotelId"
            name="hotelId"
            value={roomData.hotelId}
            onChange={handleChange}
          >
            {hotels.map((hotel) => (
              <MenuItem key={hotel.id} value={hotel.id}>
                {hotel.name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <TextField
          label="Room Number"
          name="roomNumber"
          value={roomData.roomNumber}
          onChange={handleChange}
          fullWidth
          margin="normal"
          required
        />
        <FormControl fullWidth margin="normal" required>
          <InputLabel id="room-type-label">Room Type</InputLabel>
          <Select
            labelId="room-type-label"
            id="roomType"
            name="type"
            value={roomData.type}
            onChange={handleChange}
          >
            <MenuItem value={1}>Single Room</MenuItem>
            <MenuItem value={2}>Double Room</MenuItem>
            <MenuItem value={3}>Suite Room</MenuItem>
            <MenuItem value={4}>Matrimonial Room</MenuItem>
          </Select>
        </FormControl>
        <TextField
          label="Price"
          name="price"
          value={roomData.price}
          onChange={handleChange}
          fullWidth
          margin="normal"
          required
        />
        <Button
          type="submit"
          variant="contained"
          color="primary"
          sx={{ mt: 2 }}
        >
          Add Room
        </Button>
        <Dialog open={open} onClose={handleClose}>
          <DialogTitle>Room Added Successfully</DialogTitle>
          <DialogContent>
            <Typography variant="body1">The room has been added successfully!</Typography>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose} color="primary">Close</Button>
          </DialogActions>
        </Dialog>
      </Box>
    </Container>
  );
};
export default AddRoomForm;