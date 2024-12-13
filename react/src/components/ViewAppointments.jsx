import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import {
  Container,
  Typography,
  Card,
  CardContent,
  List,
  ListItem,
  ListItemText,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  FormControl,
  Select,
  MenuItem,
  InputLabel,
} from '@mui/material';

const ViewAppointments = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { patientId } = location.state || {};
  const [appointments, setAppointments] = useState([]);
  const [filteredAppointments, setFilteredAppointments] = useState([]);
  const [specializations, setSpecializations] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [openEditModal, setOpenEditModal] = useState(false);
  const [confirmationModal, setConfirmationModal] = useState(false);
  const [cancellationModal, setCancellationModal] = useState(false);
  const [selectedAppointment, setSelectedAppointment] = useState(null);
  const [formData, setFormData] = useState({
    appointmentDateTime: "",
    specializationId: "",
    doctorId: "",
    appointmentStatus: "CONFIRMED",
    visitType: "IN_PERSON",
  });

  // FETCH DATA
  useEffect(() => {
    const fetchAppointments = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/appointments/patient?id=${patientId}`,
          { credentials: 'include' }
        );
        if (response.ok) {
          const data = await response.json();
          setAppointments(data);
          setFilteredAppointments(
            data.filter(
              (appointment) =>
                appointment.appointmentStatus === "CONFIRMED" ||
                appointment.appointmentStatus === "PENDING"
            )
          );
        } else {
          setError('Failed to fetch appointments.');
        }
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    const fetchSpecializations = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/specializations`);
        const data = await response.json();
        setSpecializations(data.specializations);
      } catch (err) {
        console.error('Failed to fetch specializations:', err);
      }
    };

    if (patientId) {
      fetchAppointments();
      fetchSpecializations();
    }
  }, [patientId]);

  useEffect(() => {
    if (formData.specializationId) {
      const fetchDoctors = async () => {
        try {
          const response = await fetch(
            `http://localhost:8080/api/doctors/specialization?id=${formData.specializationId}`
          );
          const data = await response.json();
          setDoctors(data);
        } catch (err) {
          console.error('Failed to fetch doctors:', err);
        }
      };

      fetchDoctors();
    }
  }, [formData.specializationId]);

  // VALIDATION
  const validateForm = () => {
    const { appointmentDateTime, specializationId, doctorId } = formData;
    if (!appointmentDateTime || !specializationId || !doctorId) {
      alert('All fields are required.');
      return false;
    }

    const appointmentDate = new Date(appointmentDateTime);
    if (appointmentDate <= new Date()) {
      alert('Appointment date and time must be in the future.');
      return false;
    }

    const sameDayAppointments = appointments.filter(
      (appointment) =>
        appointment.doctor.id === doctorId &&
        new Date(appointment.appointmentDateTime).toDateString() ===
          appointmentDate.toDateString()
    );

    if (sameDayAppointments.length > 0) {
      alert('You cannot book more than one appointment with the same doctor on the same day.');
      return false;
    }

    return true;
  };

  const handleOpenEditModal = (appointment) => {
    setSelectedAppointment(appointment);
    setFormData({
      appointmentDateTime: appointment.appointmentDateTime,
      specializationId: appointment.doctor.specialization.id,
      doctorId: appointment.doctor.id,
      appointmentStatus: appointment.appointmentStatus,
      visitType: appointment.visitType,
    });
    setOpenEditModal(true);
  };

  const handleCloseEditModal = () => {
    setOpenEditModal(false);
    setSelectedAppointment(null);
  };

  const handleEditAppointment = async () => {
    if (!validateForm()) return;

    setConfirmationModal(true);
  };

  const handleConfirmEdit = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/appointments/${selectedAppointment.id}`,
        {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(formData),
        }
      );

      if (response.ok) {
        setAppointments((prev) =>
          prev.map((appointment) =>
            appointment.id === selectedAppointment.id
              ? { ...appointment, ...formData }
              : appointment
          )
        );
        setFilteredAppointments((prev) =>
          prev
            .map((appointment) =>
              appointment.id === selectedAppointment.id
                ? { ...appointment, ...formData }
                : appointment
            )
            .filter(
              (appointment) =>
                appointment.appointmentStatus === "CONFIRMED" ||
                appointment.appointmentStatus === "PENDING"
            )
        );
        handleCloseEditModal();
        setConfirmationModal(false);
      } else {
        alert('Failed to update appointment.');
      }
    } catch (err) {
      console.error('Error occurred:', err);
      alert('An error occurred.');
    }
  };

  const handleCancelAppointment = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/appointments/${selectedAppointment.id}`,
        {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ appointmentStatus: "CANCELLED" }),
        }
      );

      if (response.ok) {
        setFilteredAppointments((prev) =>
          prev.filter((appointment) => appointment.id !== selectedAppointment.id)
        );
        setCancellationModal(false);
        //alert('Appointment was successfully cancelled.');
      } else {
        alert('Failed to cancel appointment.');
      }
    } catch (err) {
      console.error('Error occurred:', err);
      alert('An error occurred.');
    }
  };

  return (
    <Container maxWidth="md" style={{ marginTop: '20px' }}>
      <Card>
        <CardContent>
          <Typography variant="h5" gutterBottom>
            Your Appointments
          </Typography>
          {filteredAppointments.length > 0 ? (
            <List>
              {filteredAppointments.map((appointment) => (
               <ListItem key={appointment.id}>
               <ListItemText
                 primary={`Date: ${appointment.appointmentDateTime}`}
                 secondary={`Doctor: ${appointment.doctor.firstName} ${appointment.doctor.lastName} (${appointment.doctor.specialization.name}), Visit Type: ${appointment.visitType}, Status: ${appointment.appointmentStatus}`}
               />
               <Button
                 variant="contained"
                 color="primary"
                 size="small"
                 onClick={() => handleOpenEditModal(appointment)}
               >
                 Edit
               </Button>
               <Button
                 variant="contained"
                 color="secondary"
                 size="small"
                 onClick={() => {
                   setSelectedAppointment(appointment);
                   setCancellationModal(true);
                 }}
                 style={{ marginLeft: '10px' }}
               >
                 Cancel
               </Button>
             </ListItem>
              ))}
              <div>
              <Button
                variant="contained"
                color="primary"
                style={{ marginTop: '20px' }}
                onClick={() => navigate('/schedule-appointment', { state: { patientId } })}
                 >
                Schedule an Appointment
              </Button>
              </div>
            </List>
          ) : (
            <>
              <Typography>No CONFIRMED or PENDING appointments found.</Typography>
              <Button
                variant="contained"
                color="primary"
                style={{ marginTop: '20px' }}
                onClick={() => navigate('/schedule-appointment', { state: { patientId } })}
              >
                Schedule an Appointment
              </Button>
            </>
          )}
        </CardContent>
      </Card>

      {/* EDIT MODAL */}
      <Dialog open={openEditModal} onClose={handleCloseEditModal}>
        <DialogTitle>Edit Appointment</DialogTitle>
        <DialogContent>
          <TextField
            label="Appointment Date and Time"
            type="datetime-local"
            name="appointmentDateTime"
            fullWidth
            margin="normal"
            value={formData.appointmentDateTime}
            onChange={(e) =>
              setFormData({ ...formData, appointmentDateTime: e.target.value })
            }
          />
          <FormControl fullWidth margin="normal">
            <InputLabel>Specialization</InputLabel>
            <Select
              name="specializationId"
              value={formData.specializationId}
              onChange={(e) =>
                setFormData({ ...formData, specializationId: e.target.value, doctorId: "" })
              }
            >
              {specializations.map((spec) => (
                <MenuItem key={spec.id} value={spec.id}>
                  {spec.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          {formData.specializationId && (
            <FormControl fullWidth margin="normal">
              <InputLabel>Doctor</InputLabel>
              <Select
                name="doctorId"
                value={formData.doctorId}
                onChange={(e) =>
                  setFormData({ ...formData, doctorId: e.target.value })
                }
              >
                {doctors.map((doctor) => (
                  <MenuItem key={doctor.id} value={doctor.id}>
                    {doctor.firstName} {doctor.lastName}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          )}
          <FormControl fullWidth margin="normal">
            <InputLabel>Appointment Status</InputLabel>
            <Select
              name="appointmentStatus"
              value={formData.appointmentStatus}
              onChange={(e) =>
                setFormData({ ...formData, appointmentStatus: e.target.value })
              }
            >
              <MenuItem value="CONFIRMED">Confirmed</MenuItem>
              <MenuItem value="PENDING">Pending</MenuItem>
              <MenuItem value="CANCELLED">Cancelled</MenuItem>
            </Select>
          </FormControl>
          <FormControl fullWidth margin="normal">
            <InputLabel>Visit Type</InputLabel>
            <Select
              name="visitType"
              value={formData.visitType}
              onChange={(e) =>
                setFormData({ ...formData, visitType: e.target.value })
              }
            >
              <MenuItem value="IN_PERSON">In Person</MenuItem>
              <MenuItem value="TELEHEALTH">Telehealth</MenuItem>
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseEditModal} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleEditAppointment} color="primary">
            Save
          </Button>
        </DialogActions>
      </Dialog>

      {/* CONFIRMATION MODAL */}
      <Dialog open={confirmationModal} onClose={() => setConfirmationModal(false)}>
        <DialogTitle>Confirm Appointment Update</DialogTitle>
        <DialogContent>
          <Typography>
          <strong>Date and Time: </strong>{' '}{formData.appointmentDateTime}
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
          <Typography><strong>Status: </strong>{' '}{formData.appointmentStatus}</Typography>
          <Typography><strong>Visit Type: </strong>{' '}{formData.visitType}</Typography>
          {formData.visitType === "IN_PERSON" && (
            <Typography style={{ color: "red", marginTop: "10px" }}>
              Disclaimer: Please arrive 15 minutes before the scheduled time.
            </Typography>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setConfirmationModal(false)} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleConfirmEdit} color="primary">
            Confirm
          </Button>
        </DialogActions>
      </Dialog>

      {/* CANCELLATION MODAL */}
      <Dialog open={cancellationModal} onClose={() => setCancellationModal(false)}>
        <DialogTitle>Cancel Appointment</DialogTitle>
        <DialogContent>
          <Typography>
            Are you sure you want to cancel this appointment?
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setCancellationModal(false)} color="secondary">
            No
          </Button>
          <Button onClick={handleCancelAppointment} color="primary">
            Yes, Cancel
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default ViewAppointments;
