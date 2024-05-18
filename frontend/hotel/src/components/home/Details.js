import React, { useState, useEffect } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button } from '@mui/material';
import api from '../api/axiosConfig';

const UserReservations = ({  }) => {
  const [reservations, setReservations] = useState([]);
  const username = localStorage.getItem("user");


  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const response = await api.get(`/reservations/${username}`);
        setReservations(response.data);
      } catch (error) {
        console.error('Error fetching reservations:', error);
      }
    };

    fetchReservations();
  }, [username]);

  const cancelReservation = async (reservationId) => {
    try {
      await api.delete(`/reservations/${reservationId}`);
      setReservations(reservations.filter((reservation) => reservation.id !== reservationId));
    } catch (error) {
      console.error('Error canceling reservation:', error);
    }
  };

  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Reservation ID</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Hotel</TableCell>
            <TableCell>Room Number</TableCell>
            <TableCell>Type</TableCell>
            <TableCell>Price</TableCell>
            <TableCell>Date</TableCell>
            <TableCell>Action</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {reservations.map((reservation) => (
            <TableRow key={reservation.id}>
              <TableCell>{reservation.id}</TableCell>
              <TableCell>{reservation.status}</TableCell>
              <TableCell>{reservation.hotel}</TableCell>
              <TableCell>{reservation.roomNumber}</TableCell>
              <TableCell>{reservation.type}</TableCell>
              <TableCell>${reservation.price}</TableCell>
              <TableCell>{reservation.date}</TableCell>
              <TableCell>
              {reservation.status !== "canceled" ? (
                  <Button variant="contained" color="error" onClick={() => cancelReservation(reservation.id)}>
                    Cancel
                  </Button>
                ) : (
                  <span>No action possible</span>
                )}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default UserReservations;
