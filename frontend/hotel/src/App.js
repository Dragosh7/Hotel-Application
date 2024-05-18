import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import api from './components/api/axiosConfig';
import Layout from './components/Layout';
import { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './components/home/Home';
import Login from "./components/Login";
import Details from './components/home/Details';
import Hotel from './components/home/AddHotel';
import Room from './components/home/AddRoom';



import Navigation from './components/Navigation';

function App() {
  const [hotels, setHotels] = useState();

  const getHotels = async () => {
    try {

      const response = await api.get("/hotels")
      console.log(response.data);
      setHotels(response.data);

    }
    catch (err) {
      console.log(err);

    }
  }
  useEffect(() => {
    getHotels();
  }, [])
  const handleLogout = () => {
    localStorage.clear();
  };
  return (
    <div className="App">
      <Navigation onLogout={handleLogout}/>
      <Routes>
        <Route path="/" element={<Home hotels={hotels || []} />} />
        <Route path="/login" element={<Login />} />
        <Route path="/reservations" element={<Details />} />
        <Route path="/addhotel" element={<Hotel />} />
        <Route path="/addroom" element={<Room />} />



      </Routes>
    </div>
  );
}

export default App;
