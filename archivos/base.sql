
insert into empleado (nombres,apellidos,cedula,correo,fecha_nacimiento,direccion,telefono,esta_vacunado) values 
('Juan', 'Perez','0107098287','juan.perez@gmail.com','1997-01-01','Av Loja y Don Bosco','4088569',false),
('Andres', 'Quinde','0107098288','andres.quinde@gmail.com','1995-02-01','Av Americas y Heroes 12','0985478956',false),
('Mateo', 'Perez','0107098289','mateo.perez@gmail.com','1998-03-04','Benigno Malo y Calle larga','4088567',false),
('Daniel', 'Guale','0107098290','daniel.guale@gmail.com','1990-06-25','Calle larga y Hermano Miguel','0985478954',false);

INSERT INTO vacuna (nombre_vacuna)
VALUES ('Sputnik'), ('AstraZeneca'), ('Pfizer'), ('Jhonson&Jhonson');

INSERT INTO rol (nombre_rol)
VALUES ('Administrador'), ('Empleado');

INSERT INTO empleado_rol (id_empleado,id_rol,user_name,password) VALUES 
(1,1,'juanperez','1234'), (2,1,'andresqui','1234'), (3,2,'mateope','admin'), (4,2,'danielgu','admin');

INSERT INTO empleado_vacuna (id_empleado, id_vacuna, fecha_vacunacion,numero_dosis) VALUES 
(1,1,'2021-01-05',1), (1,1,'2021-01-15',2),
(2,1,'2021-01-07',1),(2,2,'2021-01-17',2),
(3,3,'2021-01-18',1);
