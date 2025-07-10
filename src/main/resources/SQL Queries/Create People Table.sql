#Make table of people
CREATE TABLE people (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    location VARCHAR(100),
    phone_number VARCHAR(15) UNIQUE
    password VARCHAR(100) NOT NULL
);
