import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Login from "./components/Login";
import Details from './components/home/Details';
import Hotel from './components/home/AddHotel';
import Room from './components/home/AddRoom';



import { BrowserRouter, Route, Routes } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
    <Routes>
      <Route path='/' element={<App />}></Route>
      <Route path="/login" element={<Login/>}></Route>
      <Route path="/reservations" element={<Details />} />
      <Route path="/addhotel" element={<Hotel />} />
      <Route path="/addroom" element={<Room />} />



      <Route path="*" element={<h1>Page Not Found</h1>} />
    </Routes>
    </BrowserRouter>
  </React.StrictMode>
);