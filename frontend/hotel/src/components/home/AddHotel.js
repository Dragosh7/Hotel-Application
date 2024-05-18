import React, { useState } from 'react';
import { TextField, Button, Container, Typography, Box } from '@mui/material';
import api from '../api/axiosConfig';

const AddHotelForm = () => {
  const [hotelData, setHotelData] = useState({
    name: '',
    latitude: '',
    longitude: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setHotelData({
      ...hotelData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const userRole = localStorage.getItem("role");
      if (userRole !== 'STAFF' && userRole !== 'ADMIN') {
        alert("Only staff or admin can add hotels.");
        return;
      }

      const response = await api.post('/hotels', hotelData);
      console.log('Hotel added successfully:', response.data);
      alert("Hotel added successfully");

      setHotelData({
        name: '',
        latitude: '',
        longitude: ''
      });
    } catch (error) {
      console.error('Error adding hotel:', error);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box component="form" onSubmit={handleSubmit} sx={{ mt: 5 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Add New Hotel
        </Typography>
        <TextField
          label="Hotel Name"
          name="name"
          value={hotelData.name}
          onChange={handleChange}
          fullWidth
          margin="normal"
          required
        />
        <TextField
          label="Latitude"
          name="latitude"
          value={hotelData.latitude}
          onChange={handleChange}
          fullWidth
          margin="normal"
          required
        />
        <TextField
          label="Longitude"
          name="longitude"
          value={hotelData.longitude}
          onChange={handleChange}
          fullWidth
          margin="normal"
          required
        />
        <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
          Add Hotel
        </Button>
      </Box>
    </Container>
  );
};

export default AddHotelForm;
