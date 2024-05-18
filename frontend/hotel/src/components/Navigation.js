import React from "react";
import { Link } from "react-router-dom";
import { AppBar, Toolbar, Typography, Button } from "@mui/material";

const Navigation = () => {
    const isLoggedIn = Boolean(localStorage.getItem("user"));
    const username = localStorage.getItem("user");

    const handleLogout = () => {
        localStorage.clear();
    };

    return (
        <AppBar position="static">
            <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
                <Typography variant="h6" component="div" sx={{ marginLeft: '16px' }}>
                    
                    {isLoggedIn ? (         <Link to="/reservations" style={{ textDecoration: 'none', color: 'inherit' }}>
                        <span>Hello, {username}</span></Link>)
                    : (
                        <Link to="/login" style={{ color: "white", textDecoration: "none" }}>
                          Hotel App
                        </Link>
                      )
                    }
                </Typography>
                {isLoggedIn ? (
                    <Button color="inherit" onClick={handleLogout}>
                        Logout
                    </Button>
                ) : (
                    <Link to="/login" style={{ color: "white", textDecoration: "none" }}>
                        <Button color="inherit">Login</Button>
                    </Link>
                )}
            </Toolbar>

        </AppBar>
    );
};

export default Navigation;
