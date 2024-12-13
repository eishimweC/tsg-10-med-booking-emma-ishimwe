import React from 'react';
import Navbar from '../components/NavBar';

const GeneralPage = ({ authenticated, user, onLogin, onLogout }) => {
  return (
    <div>
      <Navbar authenticated={authenticated} user={user} onLogin={onLogin} onLogout={onLogout} />
      <div style={{ margin: '20px' }}>
        <h1>General Page !!!</h1>
      </div>
    </div>
  );
};

export default GeneralPage;
