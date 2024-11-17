------------------------------------------------------------------------------------------------------------------------
--- Споты --------------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS spot CASCADE;
CREATE TABLE spot(
                     id uuid DEFAULT gen_random_uuid() NOT NULL CONSTRAINT spot_pk PRIMARY KEY,
                     "name" varchar(50) NOT NULL,
                     lat DOUBLE PRECISION NOT NULL,
                     lon DOUBLE PRECISION NOT NULL,
                     accepted BOOLEAN NOT NULL DEFAULT FALSE,
                     inserted_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
                     updated_at TIMESTAMP DEFAULT current_timestamp,
                     description varchar(300) NOT NULL
);
------------------------------------------------------------------------------------------------------------------------
-- Генерация спотов ----------------------------------------------------------------------------------------------------
INSERT INTO spot ("name", lat, lon, description) VALUES
     ('Spot A', 40.7128, -74.0060, 'Description for Spot A'),
     ('Spot B', 34.0522, -118.2437, 'Description for Spot B'),
     ('Spot C', 51.5074, -0.1278, 'Description for Spot C'),
     ('Spot D', 48.8566, 2.3522, 'Description for Spot D'),
     ('Spot E', 35.6895, 139.6917, 'Description for Spot E'),
     ('Spot F', 55.7558, 37.6173, 'Description for Spot F'),
     ('Spot G', -33.8688, 151.2093, 'Description for Spot G'),
     ('Spot H', 1.3521, 103.8198, 'Description for Spot H'),
     ('Spot I', 52.5200, 13.4050, 'Description for Spot I'),
     ('Spot J', -22.9068, -43.1729, 'Description for Spot J');

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
INSERT INTO spot ("name", lat, lon, description) VALUES
    ('Скейтпарк в юбилейке', 40.7128, -74.0060, 'Красный скейтпарк для самокатеров');


