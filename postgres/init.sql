
-- Create the table for specializations
CREATE TABLE specializations (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
-- Insert data into the specializations table
INSERT INTO specializations (id, name) VALUES
    (1, 'Cardiology'),
    (2, 'Pediatrics'),
    (3, 'Dermatology'),
    (4, 'Orthopedics'),
    (5, 'Neurology');

CREATE TABLE IF NOT EXISTS patients (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    dob DATE NOT NULL
);

INSERT INTO patients (first_name, last_name, email, dob) VALUES
    ('Alice', 'Johnson', 'alice.johnson@example.com', '1990-01-10'),
    ('Bob', 'Williams', 'bob.williams@example.com', '1985-05-15');