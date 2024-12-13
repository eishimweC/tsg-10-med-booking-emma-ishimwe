import React from 'react';
import DoctorManagement from '../components/DoctorManagement';

const AdminPage = ({ authenticated, user, onLogin, onLogout }) => {
  return (
    <div>
      <div style={{ margin: '20px' }}>
        <DoctorManagement/>
      </div>
    </div>
  );
};

export default AdminPage;
