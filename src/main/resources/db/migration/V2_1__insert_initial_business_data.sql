-- Equipamentos
INSERT INTO equipments (serial, model, address, latitude, longitude, active)
VALUES
('ABC123', 'Radar 3000', 'Av. Brasil, 1000', -23.550520, -46.633308, true),
('DEF456', 'Detector-X', 'Rua das Flores, 200', -23.551000, -46.634000, false);

-- Infração de exemplo
INSERT INTO violations (
    type, occurrence_date_utc, measured_speed, considered_speed, regulated_speed, picture, equipment_id
)
VALUES (
    'VELOCITY',
    '2025-07-10T14:30:00',
    85.50,
    80.00,
    60.00,
    null,
    (SELECT id FROM equipments WHERE serial = 'ABC123')
);
