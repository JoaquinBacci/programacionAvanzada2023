<<<<<<< HEAD
INSERT INTO marca ( nombre, activo) VALUES ( 'Ford', true), ('Chevrolet', true), ( 'Toyota', true);
=======
-- encoding: UTF-8
INSERT INTO marca (id, nombre, activo) VALUES (1, 'Ford', true), (2, 'Chevrolet', true), (3, 'Toyota', true);
>>>>>>> desa-mauri
INSERT INTO modelo (nombre, activo, id_marca) VALUES ('Fiesta', true, 1), ('Mustang', true, 1), ('Camaro', true, 2), ('Corolla', true, 3);
INSERT INTO tecnico (dni, num_tel, legajo, nombre, apellido, direccion, email, activo) VALUES (30567890, '123-456-7890', 101, 'Juan', 'Pérez', 'Calle 123', 'juan.perez@email.com', true), (40876543, '987-654-3210', 102, 'María', 'Gómez', 'Avenida 456', 'maria.gomez@email.com', true), (55555555, '555-555-5555', 103, 'Carlos', 'López', 'Ruta 789', 'carlos.lopez@email.com', true);
INSERT INTO cliente (dni, num_tel, nombre, apellido, direccion, email, activo, licencia_conducir) VALUES (20123456, '123-456-7890', 'Luis', 'Martínez', 'Calle 567', 'luis.martinez@email.com', true, 'Licencia987654'), (30987654, '987-654-3210', 'Ana', 'Rodríguez', 'Avenida 890', 'ana.rodriguez@email.com', true, 'Licencia123987'), (42515785, '555-555-5555', 'Elena', 'Gutiérrez', 'Ruta 123', 'elena.gutierrez@email.com', true, 'Licencia555555');
INSERT INTO vehiculo (kilometraje, patente, id_marca, id_modelo, id_cliente, activo, anio) VALUES (50000, 'ABC123', 1, 1, 1, true, 2022), (70000, 'XYZ789', 2, 2, 2, true, 2020), (30000, 'DEF456', 3, 3, 3, true, 2021),(35000, 'ZEH476', 3, 4, 3, true, 2021);
INSERT INTO orden (activo, tecnico_id, vehiculo_id, fecha_ingreso, estado_actual, estado_anterior, descripcion) VALUES (true, 1, 1, '2023-12-19 20:02:55.93', 'creada', null, 'Descripción de la orden 1'), (true, 2, 2, '2023-12-19 20:02:55.93', 'creada', null, 'Descripción de la orden 2'), (true, 3, 3, '2023-12-19 20:02:55.93', 'creada', null, 'Descripción de la orden 3');
INSERT INTO servicio (nombre, precio, activo, descripcion,impuesto) VALUES ('Cambio de aceite', 50.00, true, 'Incluye cambio de filtro de aceite',21.0), ('Alineación y balanceo', 80.00, true, 'Servicio completo para mantener la estabilidad del vehículo',35.0), ('Reparación de frenos', 120.00, true, 'Revisión y reparación del sistema de frenos',0.00);
INSERT INTO detalle_orden (id_servicio, orden_id, cantidad, precio_individual, precio_total) VALUES (1, 1, 2, 25.00, 50.00), (1, 2, 1, 25.00, 25.00), (3, 3, 1, 60.00, 60.00);
INSERT INTO orden_detalles_orden (orden_id, detalles_orden_id) VALUES (1, 1), (2, 2), (3, 3);