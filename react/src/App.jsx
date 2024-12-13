import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import AdminPage from './pages/AdminPage';
import Navbar from './components/NavBar';
import ViewAppointments from './components/ViewAppointments';
import ScheduleAppointment from './components/ScheduleAppointment';

const App = () => {
  const [authenticated, setAuthenticated] = useState(false);
  const [user, setUser] = useState(null);

  useEffect(() => {
    fetch('http://localhost:8080/api/user', { credentials: 'include' })
      .then((response) => response.text())
      .then((body) => {
        if (body) {
          setUser(JSON.parse(body));
          setAuthenticated(true);
        } else {
          setAuthenticated(false);
        }
      });
  }, []);

  const login = () => {
    let port = window.location.port ? `:${window.location.port}` : '';
    if (port === ':3000') {
      port = ':8080';
    }
    window.location.href = `//${window.location.hostname}${port}/oauth2/authorization/okta`;
  };

  const logout = () => {
    //console.log(document.cookie);
    const csrfToken = document.cookie
      .split(';')
      .find((row) => row.startsWith('XSRF-TOKEN='))
      .split('=')[1];
    fetch('http://localhost:8080/api/logout', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
        'X-XSRF-Token': csrfToken,
      },
    })
      .then((res) => res.json())
      .then((response) => {
        window.location.href = `${response.logoutUrl}?id_token_hint=${response.idToken}&post_logout_redirect_uri=${window.location.origin}`;
      });
  };

  return (
    <Router>
      <Navbar authenticated={authenticated} user={user} onLogin={login} onLogout={logout} />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/view-appointments" element={<ViewAppointments />} />
        <Route path="/schedule-appointment" element={<ScheduleAppointment />} />
        <Route
          path="/admin"
          element={<AdminPage authenticated={authenticated} user={user} />}
        />
      </Routes>
    </Router>
  );
};

export default App;
