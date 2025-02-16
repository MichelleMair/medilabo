USE medilabo_patients;

CREATE TABLE patients (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    address VARCHAR(255),
    phone_number VARCHAR(20)
);

INSERT INTO patients (first_name, last_name, date_of_birth, gender, address, phone_number)
VALUES
('Test', 'TestNone', '1966-12-31', 'Female', '1 Brookside St', '100-222-3333'),
('Test', 'TestBorderline', '1945-6-24', 'Male', '2 High St', '200-333-4444'),
('Test', 'TestInDanger', '2004-6-18', 'Male', '3 Club Road', '300-444-5555'),
('Test', 'TestEarlyOnset', '2002-6-28', 'Female', '4 Valley Dr', '400-555-6666');