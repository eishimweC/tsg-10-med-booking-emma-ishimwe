import React, { useState, useEffect } from 'react';
import {
  Container,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  Typography,
  CircularProgress,
  Alert,
  TablePagination,
} from '@mui/material';

const DoctorManagement = () => {
  const [doctors, setDoctors] = useState([]);
  const [specializations, setSpecializations] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [openModal, setOpenModal] = useState(false);
  const [modalMode, setModalMode] = useState("add");
  const [selectedDoctor, setSelectedDoctor] = useState(null);
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    specializationId: "",
  });
  const [validationError, setValidationError] = useState("");

  // Pagination states
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  // Fetch doctors and specializations on component mount
  useEffect(() => {
    fetchDoctors();
    fetchSpecializations();
  }, []);

  const fetchDoctors = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch("http://localhost:8080/api/doctors");
      if (!response.ok) {
        throw new Error("Failed to fetch doctors");
      }
      const data = await response.json();
      setDoctors(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const fetchSpecializations = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch("http://localhost:8080/api/specializations");
      if (!response.ok) {
        throw new Error("Failed to fetch specializations");
      }
      const data = await response.json();
      setSpecializations(data.specializations);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleOpenModal = (mode, doctor = null) => {
    setModalMode(mode);
    setSelectedDoctor(doctor);
    setValidationError("");
    setFormData(
      doctor
        ? {
            firstName: doctor.firstName,
            lastName: doctor.lastName,
            specializationId: doctor.specialization.id,
          }
        : {
            firstName: "",
            lastName: "",
            specializationId: "",
          }
    );
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
    setSelectedDoctor(null);
  };

  const validateForm = () => {
    if (!formData.firstName.trim() || !formData.lastName.trim() || !formData.specializationId) {
      setValidationError("All fields are required.");
      return false;
    }
    return true;
  };

  const handleSubmit = async () => {
    if (!validateForm()) return;

    setLoading(true);
    setError(null);
    try {
      const url =
        modalMode === "add"
          ? "http://localhost:8080/api/doctors/create"
          : `http://localhost:8080/api/doctors/${selectedDoctor.id}`;
      const method = modalMode === "add" ? "POST" : "PUT";

      const response = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        throw new Error("Failed to save doctor");
      }

      fetchDoctors();
      handleCloseModal();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this doctor?")) return;

    setLoading(true);
    setError(null);
    try {
      const response = await fetch(`http://localhost:8080/api/doctors/del/${id}`, {
        method: "DELETE",
      });

      if (!response.ok) {
        throw new Error("Failed to delete doctor");
      }

      fetchDoctors();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Doctor Management
      </Typography>
      {loading && <CircularProgress style={{ marginBottom: "20px" }} />}
      {error && (
        <Alert severity="error" style={{ marginBottom: "20px" }}>
          {error}
        </Alert>
      )}
      <Button
        variant="contained"
        color="primary"
        onClick={() => handleOpenModal("add")}
        style={{ marginBottom: "20px" }}
      >
        Add Doctor
      </Button>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>#</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Specialization</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {doctors
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((doctor, index) => (
                <TableRow key={doctor.id}>
                  <TableCell>{page * rowsPerPage + index + 1}</TableCell>
                  <TableCell>
                    {doctor.firstName} {doctor.lastName}
                  </TableCell>
                  <TableCell>{doctor.specialization.name}</TableCell>
                  <TableCell>
                    <Button
                      variant="outlined"
                      color="primary"
                      onClick={() => handleOpenModal("edit", doctor)}
                    >
                      Edit
                    </Button>
                    <Button
                      variant="outlined"
                      color="secondary"
                      style={{ marginLeft: "10px" }}
                      onClick={() => handleDelete(doctor.id)}
                    >
                      Delete
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[5, 10, 15]}
        component="div"
        count={doctors.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
      <Dialog open={openModal} onClose={handleCloseModal}>
        <DialogTitle>
          {modalMode === "add" ? "Add Doctor" : "Edit Doctor"}
        </DialogTitle>
        <DialogContent>
          {validationError && (
            <Alert severity="error" style={{ marginBottom: "10px" }}>
              {validationError}
            </Alert>
          )}
          <TextField
            label="First Name"
            name="firstName"
            fullWidth
            margin="normal"
            value={formData.firstName}
            onChange={handleInputChange}
          />
          <TextField
            label="Last Name"
            name="lastName"
            fullWidth
            margin="normal"
            value={formData.lastName}
            onChange={handleInputChange}
          />
          <FormControl fullWidth margin="normal">
            <InputLabel>Specialization</InputLabel>
            <Select
              name="specializationId"
              value={formData.specializationId}
              onChange={handleInputChange}
            >
              {specializations.map((spec) => (
                <MenuItem key={spec.id} value={spec.id}>
                  {spec.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseModal} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleSubmit} color="primary">
            Save
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default DoctorManagement;
