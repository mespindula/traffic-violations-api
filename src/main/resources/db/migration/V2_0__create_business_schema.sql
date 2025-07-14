-- Tabela de equipamentos
CREATE TABLE IF NOT EXISTS equipments (
    id SERIAL PRIMARY KEY,
    serial VARCHAR(255) NOT NULL UNIQUE,
    model VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    latitude DECIMAL(10, 6),
    longitude DECIMAL(10, 6),
    active BOOLEAN NOT NULL
);

-- Tabela de infrações
CREATE TABLE IF NOT EXISTS violations (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    occurrence_date_utc TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    measured_speed DECIMAL(10, 2),
    considered_speed DECIMAL(10, 2),
    regulated_speed DECIMAL(10, 2),
    picture BYTEA,
    equipment_id INT NOT NULL,
    FOREIGN KEY (equipment_id) REFERENCES equipments(id) ON DELETE CASCADE
);
