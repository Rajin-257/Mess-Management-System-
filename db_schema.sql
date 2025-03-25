-- Create the database
CREATE DATABASE IF NOT EXISTS hostel_management;
USE hostel_management;

-- Create the person table to store resident information
CREATE TABLE IF NOT EXISTS person (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    rent DOUBLE NOT NULL
);

-- Create the bazar table to store shopping expenses
CREATE TABLE IF NOT EXISTS bazar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    person_id INT NOT NULL,
    list VARCHAR(255) NOT NULL,
    cost DOUBLE NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);

-- Create the meal table to store meal records
CREATE TABLE IF NOT EXISTS meal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    person_id INT NOT NULL,
    meal_count DOUBLE NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);

-- Create the utility table to store utility costs
CREATE TABLE IF NOT EXISTS utility (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    house_utility INT NOT NULL,
    maid_bill INT NOT NULL,
    electricity_bill INT NOT NULL,
    clean_bill INT NOT NULL,
    wifi_bill INT NOT NULL
);