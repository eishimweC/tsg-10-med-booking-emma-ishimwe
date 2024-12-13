import React, { useState, useEffect } from 'react';
import {
  Container,
  TextField,
  Button,
  Typography,
  MenuItem,
  FormControl,
  Select,
  InputLabel,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from '@mui/material';
import { useLocation, useNavigate } from 'react-router-dom';

const ScheduleAppointment = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [message, setMessage] = useState(null);
  const { patientId } = location.state || {};
  const [specializations, setSpecializations] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [appointments, setAppointments] = useState([]);
  const [formData, setFormData] = useState({
    email: '',
    appointmentDate: '',
    specializationId: '',
    doctorId: '',
    visitType: '',
    appointmentStatus: 'CONFIRMED',
  });
  const [selectedSpecialization, setSelectedSpecialization] = useState(null);
  const [showDateWarning, setShowDateWarning] = useState(false);
  const [confirmationModal, setConfirmationModal] = useState(false);

  // Redirect if patientId is missing
  useEffect(() => {
    if (!patientId) {
      setMessage('Patient ID is missing. Please select a patient first.');
      navigate('/');
    }
  }, [patientId, navigate]);

  // Fetch specializations and appointments on component load
  useEffect(() => {
    const fetchSpecializations = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/specializations');
        if (response.ok) {
          const data = await response.json();
          setSpecializations(data.specializations);
          setSelectedSpecialization(data.specializations[0]?.id);
        } else {
          setMessage('Failed to fetch specializations.');
        }
      } catch (err) {
        setMessage(err.message);
      }
    };

    const fetchAppointments = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/appointments/patient?id=${patientId}`
        );
        if (response.ok) {
          const data = await response.json();
          setAppointments(data);
        } else {
          setMessage('Failed to fetch appointments.');
        }
      } catch (err) {
        setMessage(err.message);
      }
    };

    if (patientId) {
      fetchSpecializations();
      fetchAppointments();
    }
  }, [patientId]);

  useEffect(() => {
    if (selectedSpecialization) {
      const fetchDoctors = async () => {
        try {
          const response = await fetch(
            `http://localhost:8080/api/doctors/specialization?id=${selectedSpecialization}`
          );
          if (response.ok) {
            const data = await response.json();
            setDoctors(data);
          } else {
            setMessage('Failed to fetch doctors.');
          }
        } catch (err) {
          setMessage(err.message);
        }
      };

      fetchDoctors();
    }
  }, [selectedSpecialization]);

  // Validation
  const validateForm = () => {
    const { email, appointmentDate, specializationId, doctorId } = formData;
    if (!email || !appointmentDate || !specializationId || !doctorId) {
      alert('All fields are required.');
      return false;
    }

    const appointmentDateTime = new Date(appointmentDate);
    if (appointmentDateTime <= new Date()) {
      setShowDateWarning(true);
      return false;
    }

    const sameDayAppointments = appointments.filter(
      (appointment) =>
        appointment.doctor.id === doctorId &&
        new Date(appointment.appointmentDateTime).toDateString() ===
          appointmentDateTime.toDateString()
    );

    if (sameDayAppointments.length > 0) {
      alert('You cannot book more than one appointment with the same doctor on the same day.');
      return false;
    }

    setShowDateWarning(false);
    return true;
  };

  const handleSpecializationChange = (e) => {
    setSelectedSpecialization(e.target.value);
    setFormData({ ...formData, specializationId: e.target.value });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleScheduleSubmit = (e) => {
    e.preventDefault();
    if (!validateForm()) return;
    setConfirmationModal(true);
  };

  const handleConfirmAppointment = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/appointments/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          patientId,
          doctorId: formData.doctorId,
          appointmentDateTime: formData.appointmentDate,
          visitType: formData.visitType,
          appointmentStatus: formData.appointmentStatus,
          email: formData.email,
        }),
      });
  
      if (response.ok) {
        setMessage('Appointment scheduled successfully!');
        setTimeout(() => {
          navigate('/');
        }, 5000); // Delay navigation for 5 seconds
      } else {
        setMessage('Failed to schedule the appointment.');
      }
    } catch (err) {
      setMessage(err.message);
    }
    setConfirmationModal(false);
  };
  

  return (
    <Container maxWidth="sm" style={{ marginTop: '20px' }}>
      <Typography variant="h5" gutterBottom>
        Schedule an Appointment
      </Typography>
      {message && (
        <Typography style={{ marginBottom: '20px', color: 'green' }}>{message}</Typography>
      )}
      <form onSubmit={handleScheduleSubmit}>
        <TextField
          label="Email"
          type="email"
          name="email"
          variant="outlined"
          fullWidth
          value={formData.email}
          onChange={handleInputChange}
          style={{ marginBottom: '15px' }}
          required
        />
        <TextField
          label="Appointment Date and Time"
          type="datetime-local"
          name="appointmentDate"
          variant="outlined"
          fullWidth
          value={formData.appointmentDate}
          onChange={handleInputChange}
          style={{ marginBottom: '15px' }}
          InputLabelProps={{ shrink: true }}
          required
        />
        {showDateWarning && (
          <Typography style={{ color: 'red', marginBottom: '10px' }}>
            Appointment date and time must be in the future.
          </Typography>
        )}
        <FormControl fullWidth style={{ marginBottom: '15px' }}>
          <InputLabel>Specialization</InputLabel>
          <Select
            name="specializationId"
            value={formData.specializationId}
            onChange={handleSpecializationChange}
            required
          >
            {specializations.map((spec) => (
              <MenuItem key={spec.id} value={spec.id}>
                {spec.name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <FormControl fullWidth style={{ marginBottom: '15px' }}>
          <InputLabel>Doctor</InputLabel>
          <Select
            name="doctorId"
            value={formData.doctorId}
            onChange={handleInputChange}
            required
          >
            {doctors.map((doctor) => (
              <MenuItem key={doctor.id} value={doctor.id}>
                {doctor.firstName} {doctor.lastName}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <FormControl fullWidth style={{ marginBottom: '15px' }}>
          <InputLabel>Visit Type</InputLabel>
          <Select
            name="visitType"
            value={formData.visitType}
            onChange={handleInputChange}
            required
          >
            <MenuItem value="IN_PERSON">In Person</MenuItem>
            <MenuItem value="TELEHEALTH">Telehealth</MenuItem>
          </Select>
        </FormControl>
        <FormControl fullWidth style={{ marginBottom: '15px' }}>
          <InputLabel>Appointment Status</InputLabel>
          <Select
            name="appointmentStatus"
            value={formData.appointmentStatus}
            onChange={handleInputChange}
            required
          >
            <MenuItem value="CONFIRMED">Confirmed</MenuItem>
            <MenuItem value="PENDING">Pending</MenuItem>
            <MenuItem value="CANCELLED">Cancelled</MenuItem>
          </Select>
        </FormControl>
        <Button type="submit" variant="contained" color="primary" fullWidth>
          Schedule Appointment
        </Button>
      </form>

      {/* Confirmation Modal */}
      <Dialog open={confirmationModal} onClose={() => setConfirmationModal(false)}>
        <DialogTitle>Confirm Appointment</DialogTitle>
        <DialogContent>
          <Typography>
            <strong>Date and Time:</strong> {formData.appointmentDate}
          </Typography>
          <Typography>
            <strong>Doctor:</strong>{' '}
            {(() => {
              const selectedDoctor = doctors.find((doc) => doc.id === formData.doctorId);
              const doctorName = selectedDoctor
                ? `${selectedDoctor.firstName} ${selectedDoctor.lastName}`
                : 'Unknown Doctor';

              const doctorSpecialization = specializations.find(
                (spec) => spec.id === selectedDoctor?.specialization?.id
              )?.name;

              return `${doctorName}${doctorSpecialization ? ` (${doctorSpecialization})` : ''}`;
            })()}
          </Typography>
          <Typography>
            <strong>Visit Type:</strong> {formData.visitType}
          </Typography>
          {formData.visitType === 'IN_PERSON' && (
            <Typography style={{ color: 'red', marginTop: '10px' }}>
              Disclaimer: Please arrive 15 minutes before the scheduled time.
            </Typography>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setConfirmationModal(false)} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleConfirmAppointment} color="primary">
            Confirm
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default ScheduleAppointment;
