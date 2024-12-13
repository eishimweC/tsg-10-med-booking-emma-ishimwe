import React, { useState } from 'react';
import { Container, TextField, Button, Typography, Card, CardContent } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';

const HomePage = () => {
  const [email, setEmail] = useState('');
  const [patientData, setPatientData] = useState(null); // Store patient data
  const [error, setError] = useState(null); // Store error messages
  const [loading, setLoading] = useState(false); // Handle loading state
  const navigate = useNavigate(); // For navigation

  const handleInputChange = (e) => {
    setEmail(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);
    try {
      // Fetch patient by email
      const response = await fetch(`http://localhost:8080/api/patients/e?email=${email}`);
      if (response.ok) {
        const data = await response.json();
        setPatientData(data); // Store patient data
      } else {
        setPatientData(null);
        setError('No record found for this email.'); // Show error if patient is not found
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="sm" style={{ marginTop: '20px' }}>
      <Typography variant="h5" gutterBottom>
        Enter Your Email(Patient):
      </Typography>
      <form onSubmit={handleSubmit} style={{ marginBottom: '20px' }}>
        <TextField
          label="Email"
          variant="outlined"
          fullWidth
          value={email}
          onChange={handleInputChange}
          style={{ marginBottom: '15px' }}
        />
        <Button type="submit" variant="contained" color="primary" fullWidth disabled={loading}>
          {loading ? 'Searching...' : 'Search'}
        </Button>
      </form>
      {error && (
        <Typography color="error" gutterBottom>
          {error}
        </Typography>
      )}
      {patientData ? (
        // If patient is found, ask for next action
        <Card>
          <CardContent>
            <Typography variant="h5" gutterBottom>
              Welcome, {patientData.firstName} {patientData.lastName}
            </Typography>
            <Button
              variant="contained"
              color="primary"
              fullWidth
              style={{ marginBottom: '10px' }}
              onClick={() => navigate('/view-appointments', { state: { patientId: patientData.id } })}
            >
              View Appointments
            </Button>
            <Button
              variant="contained"
              color="secondary"
              fullWidth
              onClick={() => navigate('/schedule-appointment', { state: { patientId: patientData.id } })}
            >
              Schedule an Appointment
            </Button>

            
          </CardContent>
        </Card>
      ) : (
        // If no patient is found, provide a link to create a profile
        error && (
          <Button
            variant="contained"
            color="secondary"
            fullWidth
            component={Link}
            to="/create-profile"
            style={{ marginTop: '20px' }}
          >
            Create Profile
          </Button>
        )
      )}
    </Container>
  );
};

export default HomePage;
