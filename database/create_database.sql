USE sys;

# ---------------------------------------------------------------------- #
# Target DBMS:           MySQL                                           #
# Project name:          dealership_api_db                               #
# ---------------------------------------------------------------------- #
DROP DATABASE IF EXISTS dealership_api_db;

CREATE DATABASE IF NOT EXISTS dealership_api_db;

USE dealership_api_db;

# ---------------------------------------------------------------------- #
# Tables                                                                 #
# ---------------------------------------------------------------------- #

CREATE TABLE dealerships (
    dealership_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    phone VARCHAR(12) NOT NULL,
    PRIMARY KEY (dealership_id)
);

CREATE TABLE vehicles (
    vin INT NOT NULL,
    year INT NOT NULL,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    vehicle_type VARCHAR(50) NOT NULL,
    color VARCHAR(20) NOT NULL,
    odometer INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    sold BOOLEAN NOT NULL DEFAULT 0,
    PRIMARY KEY (vin)
);

CREATE TABLE inventory (
    inventory_id INT NOT NULL AUTO_INCREMENT,
    dealership_id INT NOT NULL,
    vin INT NOT NULL,
    PRIMARY KEY (inventory_id),
    FOREIGN KEY (dealership_id) REFERENCES dealerships(dealership_id),
    FOREIGN KEY (vin) REFERENCES vehicles(vin)
);

CREATE TABLE sales_contracts (
    contract_id INT NOT NULL AUTO_INCREMENT,
    contract_date VARCHAR(10) NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    customer_email VARCHAR(100) NOT NULL,
    vin INT NOT NULL,
    sales_tax DECIMAL(10, 2),
    recording_fee DECIMAL(10, 2),
    processing_fee DECIMAL(10, 2),
    total_price DECIMAL(10, 2),
    finance_option VARCHAR(3),
    monthly_payment DECIMAL(10, 2),
    PRIMARY KEY (contract_id),
    FOREIGN KEY (vin) REFERENCES vehicles(vin)
);

CREATE TABLE lease_contracts (
    contract_id INT NOT NULL AUTO_INCREMENT,
    contract_date VARCHAR(10) NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    customer_email VARCHAR(100) NOT NULL,
    vin INT NOT NULL,
    sales_tax DECIMAL(10, 2),
    recording_fee DECIMAL(10, 2),
    processing_fee DECIMAL(10, 2),
    total_price DECIMAL(10, 2),
    monthly_payment DECIMAL(10, 2),
    PRIMARY KEY (contract_id),
    FOREIGN KEY (vin) REFERENCES vehicles(vin)
);

# ---------------------------------------------------------------------- #
# Insert Data                                                            #
# ---------------------------------------------------------------------- #

-- Insert into dealerships
INSERT INTO dealerships (name, address, phone)
VALUES ('A & B Autos', '123 Main St', '817-555-1111'),
       ('Best Cars', '456 Elm St', '817-555-2222'),
       ('Value Motors', '789 Oak St', '817-555-3333'),
       ('Premier Autos', '101 Pine St', '817-555-4444'),
       ('Luxury Rides', '202 Maple St', '817-555-5556'),
       ('Economy Wheels', '303 Cedar St', '817-555-7777'),
       ('Auto House', '404 Birch St', '817-555-8888'),
       ('City Cars', '505 Spruce St', '817-555-9999'),
       ('Family Autos', '606 Willow St', '817-555-0000'),
       ('Reliable Rides', '707 Fir St', '817-555-1212'),
       ('Trusted Autos', '808 Hickory St', '817-555-1313'),
       ('Speedy Cars', '909 Poplar St', '817-555-1414'),
       ('Elite Motors', '1010 Sycamore St', '817-555-1515'),
       ('Green Autos', '1111 Alder St', '817-555-1616'),
       ('Classic Cars', '1212 Juniper St', '817-555-1717'),
       ('Sporty Rides', '1313 Pinecone St', '817-555-1818'),
       ('Comfort Cars', '1414 Olive St', '817-555-1919'),
       ('Budget Autos', '1515 Palm St', '817-555-2020'),
       ('Safe Wheels', '1616 Beech St', '817-555-2121'),
       ('Luxury Motors', '1717 Maplewood St', '817-555-2222');

-- Insert into vehicles
INSERT INTO vehicles (vin, year, make, model, vehicle_type, color, odometer, price, sold)
VALUES (20123, 2015, 'Toyota', 'Camry', 'Sedan', 'Blue', 45000, 12000.00, 0),
       (30234, 2016, 'Honda', 'Accord', 'Sedan', 'Black', 35000, 15000.00, 0),
       (40345, 2017, 'Nissan', 'Altima', 'Sedan', 'White', 30000, 13000.00, 0),
       (50456, 2018, 'Chevrolet', 'Malibu', 'Sedan', 'Gray', 25000, 14000.00, 0),
       (60567, 2019, 'Ford', 'Fusion', 'Sedan', 'Red', 20000, 16000.00, 0),
       (70678, 2020, 'Hyundai', 'Sonata', 'Sedan', 'Silver', 15000, 17000.00, 0),
       (80789, 2021, 'Kia', 'Optima', 'Sedan', 'Blue', 10000, 18000.00, 0),
       (90890, 2022, 'Mazda', '6', 'Sedan', 'Black', 5000, 19000.00, 0),
       (10901, 2010, 'Volkswagen', 'Jetta', 'Sedan', 'White', 70000, 8000.00, 0),
       (20912, 2011, 'Subaru', 'Legacy', 'Sedan', 'Gray', 60000, 9000.00, 0),
       (30923, 2012, 'BMW', '3 Series', 'Sedan', 'Red', 50000, 20000.00, 0),
       (40934, 2013, 'Audi', 'A4', 'Sedan', 'Silver', 40000, 21000.00, 0),
       (50945, 2014, 'Mercedes-Benz', 'C-Class', 'Sedan', 'Blue', 30000, 22000.00, 0),
       (60956, 2015, 'Lexus', 'ES', 'Sedan', 'Black', 20000, 23000.00, 0),
       (70967, 2016, 'Cadillac', 'ATS', 'Sedan', 'White', 10000, 24000.00, 0),
       (80978, 2017, 'Jaguar', 'XE', 'Sedan', 'Gray', 5000, 25000.00, 0),
       (90989, 2018, 'Infiniti', 'Q50', 'Sedan', 'Red', 45000, 26000.00, 0),
       (10000, 2019, 'Acura', 'TLX', 'Sedan', 'Silver', 35000, 27000.00, 0),
       (11001, 2020, 'Genesis', 'G70', 'Sedan', 'Blue', 25000, 28000.00, 0),
       (12002, 2021, 'Tesla', 'Model 3', 'Sedan', 'Black', 15000, 35000.00, 0);

-- Insert into inventory
INSERT INTO inventory (dealership_id, vin)
VALUES (2, 20123),
       (2, 30234),
       (3, 40345),
       (3, 50456),
       (4, 60567),
       (4, 70678),
       (5, 80789),
       (5, 90890),
       (6, 10901),
       (6, 20912),
       (7, 30923),
       (7, 40934),
       (8, 50945),
       (8, 60956),
       (9, 70967),
       (9, 80978),
       (10, 90989),
       (10, 10000),
       (1, 11001),
       (1, 12002);

-- Insert into sales_contracts
INSERT INTO sales_contracts (contract_date, customer_name, customer_email, vin, sales_tax, recording_fee, processing_fee, total_price, finance_option, monthly_payment)
VALUES ('20220110', 'John Doe', 'john.doe@example.com', 20123, 600.00, 100.00, 295.00, 12995.00, 'YES', 290.00),
       ('20220115', 'Jane Smith', 'jane.smith@example.com', 30234, 750.00, 100.00, 295.00, 16040.00, 'YES', 320.00),
       ('20220201', 'Robert Brown', 'robert.brown@example.com', 40345, 650.00, 100.00, 295.00, 14040.00, 'NO', 0.00),
       ('20220215', 'Emily Johnson', 'emily.johnson@example.com', 50456, 700.00, 100.00, 295.00, 15295.00, 'YES', 310.00),
       ('20220301', 'Michael Lee', 'michael.lee@example.com', 60567, 800.00, 100.00, 295.00, 17295.00, 'YES', 340.00),
       ('20220315', 'Jessica Davis', 'jessica.davis@example.com', 70678, 850.00, 100.00, 295.00, 18295.00, 'NO', 0.00),
       ('20220401', 'David Miller', 'david.miller@example.com', 80789, 900.00, 100.00, 295.00, 19295.00, 'YES', 360.00),
       ('20220415', 'Sarah Wilson', 'sarah.wilson@example.com', 90890, 950.00, 100.00, 295.00, 20295.00, 'YES', 380.00),
       ('20220501', 'Daniel Martinez', 'daniel.martinez@example.com', 10901, 400.00, 100.00, 295.00, 9095.00, 'NO', 0.00),
       ('20220515', 'Laura Anderson', 'laura.anderson@example.com', 20912, 450.00, 100.00, 295.00, 9995.00, 'YES', 210.00),
       ('20220601', 'James Taylor', 'james.taylor@example.com', 30923, 1000.00, 100.00, 295.00, 21295.00, 'NO', 0.00),
       ('20220615', 'Elizabeth Harris', 'elizabeth.harris@example.com', 40934, 1050.00, 100.00, 295.00, 22295.00, 'YES', 410.00),
       ('20220701', 'Anthony Clark', 'anthony.clark@example.com', 50945, 1100.00, 100.00, 295.00, 23295.00, 'NO', 0.00),
       ('20220715', 'Jennifer Walker', 'jennifer.walker@example.com', 60956, 1150.00, 100.00, 295.00, 24295.00, 'YES', 430.00),
       ('20220801', 'Christopher Young', 'christopher.young@example.com', 70967, 1200.00, 100.00, 295.00, 25295.00, 'YES', 450.00),
       ('20220815', 'Amanda King', 'amanda.king@example.com', 80978, 1250.00, 100.00, 295.00, 26295.00, 'NO', 0.00),
       ('20220901', 'Matthew Wright', 'matthew.wright@example.com', 90989, 1300.00, 100.00, 295.00, 27295.00, 'YES', 470.00),
       ('20220915', 'Michelle Scott', 'michelle.scott@example.com', 10000, 1400.00, 100.00, 295.00, 29395.00, 'NO', 0.00),
       ('20221001', 'Joshua Green', 'joshua.green@example.com', 11001, 1500.00, 100.00, 295.00, 30395.00, 'YES', 490.00),
       ('20221015', 'Ashley Adams', 'ashley.adams@example.com', 12002, 1750.00, 100.00, 295.00, 37145.00, 'YES', 580.00);

-- Insert into lease_contracts
INSERT INTO lease_contracts (contract_date, customer_name, customer_email, vin, sales_tax, recording_fee, processing_fee, total_price, monthly_payment)
VALUES ('20220112', 'Brian Hall', 'brian.hall@example.com', 20123, 600.00, 100.00, 295.00, 13645.00, 350.00),
       ('20220118', 'Rebecca Allen', 'rebecca.allen@example.com', 30234, 750.00, 100.00, 295.00, 16840.00, 400.00),
       ('20220203', 'Steven Wright', 'steven.wright@example.com', 40345, 650.00, 100.00, 295.00, 15295.00, 370.00),
       ('20220218', 'Olivia Hill', 'olivia.hill@example.com', 50456, 700.00, 100.00, 295.00, 16295.00, 380.00),
       ('20220305', 'Ethan Green', 'ethan.green@example.com', 60567, 800.00, 100.00, 295.00, 17895.00, 420.00),
       ('20220320', 'Mia Lewis', 'mia.lewis@example.com', 70678, 850.00, 100.00, 295.00, 18895.00, 440.00),
       ('20220405', 'Lucas Robinson', 'lucas.robinson@example.com', 80789, 900.00, 100.00, 295.00, 19895.00, 460.00),
       ('20220420', 'Charlotte Martinez', 'charlotte.martinez@example.com', 90890, 950.00, 100.00, 295.00, 20895.00, 480.00),
       ('20220505', 'Jackson Hernandez', 'jackson.hernandez@example.com', 10901, 400.00, 100.00, 295.00, 9295.00, 250.00),
       ('20220520', 'Sophia Lopez', 'sophia.lopez@example.com', 20912, 450.00, 100.00, 295.00, 10295.00, 270.00),
       ('20220605', 'Sebastian Gonzalez', 'sebastian.gonzalez@example.com', 30923, 1000.00, 100.00, 295.00, 23295.00, 510.00),
       ('20220620', 'Harper Perez', 'harper.perez@example.com', 40934, 1050.00, 100.00, 295.00, 24295.00, 530.00),
       ('20220705', 'Liam Turner', 'liam.turner@example.com', 50945, 1100.00, 100.00, 295.00, 25295.00, 550.00),
       ('20220720', 'Aria Collins', 'aria.collins@example.com', 60956, 1150.00, 100.00, 295.00, 26295.00, 570.00),
       ('20220805', 'Mason Wilson', 'mason.wilson@example.com', 70967, 1200.00, 100.00, 295.00, 27295.00, 590.00),
       ('20220820', 'Ella Sanchez', 'ella.sanchez@example.com', 80978, 1250.00, 100.00, 295.00, 28295.00, 610.00),
       ('20220905', 'Logan Morris', 'logan.morris@example.com', 90989, 1300.00, 100.00, 295.00, 29295.00, 630.00),
       ('20220920', 'Avery Rogers', 'avery.rogers@example.com', 10000, 1400.00, 100.00, 295.00, 31395.00, 670.00),
       ('20221005', 'Grayson Bell', 'grayson.bell@example.com', 11001, 1500.00, 100.00, 295.00, 32395.00, 690.00),
       ('20221020', 'Layla Bailey', 'layla.bailey@example.com', 12002, 1750.00, 100.00, 295.00, 39145.00, 750.00);