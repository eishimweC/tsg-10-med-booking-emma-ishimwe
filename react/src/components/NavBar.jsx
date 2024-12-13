import React from 'react';
import { Link } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const Navbar = ({ authenticated, user, onLogin, onLogout }) => {

return (
  <AppBar position="static" color="primary">
  <Toolbar>
    <Typography variant="h6" style={{ flexGrow: 1 }}>
      Medical Booking
    </Typography>
    <Button color="inherit" component={Link} to="/">
      Home
    </Button>
    
    {authenticated && (
      <>
        <Button color="inherit" onClick={onLogout}>
          Logout
        </Button>
      </>
    )} {!authenticated && (
      <Button color="inherit" onClick={onLogin}>
        Admin Login
      </Button>
    )}
  </Toolbar>
</AppBar>
);

};

export default Navbar;
