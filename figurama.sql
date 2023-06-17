DROP DATABASE IF EXISTS figurama;

CREATE DATABASE figurama;
USE figurama;


CREATE TABLE Serie (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
imagenUrl VARCHAR(100) NOT NULL,
nombre VARCHAR(70) NOT NULL,
esBaja INT NOT NULL
);

CREATE TABLE Material (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(50) NOT NULL,
esBaja INT NOT NULL
);

CREATE TABLE Proveedor (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(50) NOT NULL,
imagenUrl VARCHAR(100),
esBaja INT NOT NULL

);

CREATE TABLE Personaje (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(50) NOT NULL,
idSerie INT NOT NULL,
imagenUrl VARCHAR(100),
esBaja INT NOT NULL,
FOREIGN KEY (idSerie) REFERENCES Serie(id)
);

CREATE TABLE Figura (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(100) NOT NULL,
descripcion TEXT NOT NULL,
fechaSalida DATE NOT NULL,
precio DECIMAL(10,2) NOT NULL,
stock INT NOT NULL,
altura INT NOT NULL,
descuento INT NOT NULL,
idPersonaje INT NOT NULL,
idProveedor INT NOT NULL,
idMaterial INT NOT NULL,
esBaja INT NOT NULL,
FOREIGN KEY (idPersonaje) REFERENCES Personaje(id),
FOREIGN KEY (idProveedor) REFERENCES Proveedor(id),
FOREIGN KEY (idMaterial) REFERENCES Material(id)

);

CREATE TABLE Usuario (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(50) NOT NULL,
apellidos VARCHAR (100) NOT NULL,
contrasena VARCHAR(30) NOT NULL,
email VARCHAR(50) NOT NULL,
direccion VARCHAR(150) ,
telefono VARCHAR(20) NOT NULL,
rol VARCHAR(20) NOT NULL,
esBaja INT NOT NULL
);

CREATE TABLE Pedido (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fecha DATE NOT NULL,
estado VARCHAR(60),
idUsuario INT NOT NULL,
direccion VARCHAR(150) NOT NULL,
FOREIGN KEY (idUsuario) REFERENCES Usuario(id)
);

CREATE TABLE Cesta (
idUsuario INT NOT NULL,
idFigura INT NOT NULL,
cantidad INT NOT NULL,
PRIMARY KEY (idUsuario, idFigura),
FOREIGN KEY (idUsuario) REFERENCES Usuario(id),
FOREIGN KEY (idFigura) REFERENCES Figura(id)
);

CREATE TABLE ListaDeseos (
idUsuario INT NOT NULL,
idFigura INT NOT NULL,
PRIMARY KEY (idUsuario, idFigura),
FOREIGN KEY (idUsuario) REFERENCES Usuario(id),
FOREIGN KEY (idFigura) REFERENCES Figura(id)
);

CREATE TABLE Venta (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
cantidad INT NOT NULL,
precioUd DECIMAL(10,2) NOT NULL,
idPedido INT NOT NULL,
idFigura INT NOT NULL,
FOREIGN KEY (idPedido) REFERENCES Pedido(id),
FOREIGN KEY (idFigura) REFERENCES Figura(id)
);

CREATE TABLE Imagen (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  url VARCHAR(100) NOT NULL,
  idFigura INT NOT NULL,
  FOREIGN KEY (idFigura) REFERENCES Figura(id)
);



INSERT INTO `usuario`(`nombre`, `apellidos`, `contrasena`, `email`, `telefono`, `rol`) VALUES ('Anónimo', ' Anónimo', 'Anónimo', 'Anónimo', 777777777,'Anónimo');

INSERT INTO `usuario`(`nombre`, `apellidos`, `contrasena`, `email`, `telefono`, `rol`) VALUES ('Miguel', ' Ruiz', 'Miguel1', 'miguel@gmail.com', 677375777,'Común');

INSERT INTO `usuario`(`nombre`, `apellidos`, `contrasena`, `email`, `telefono`, `rol`) VALUES ('Pepe', ' Pérez', 'Pepe1', 'pepe@gmail.com', 772334777,'Común');

INSERT INTO `usuario`(`nombre`, `apellidos`, `contrasena`, `email`, `telefono`, `rol`) VALUES ('Guillermo', ' Hernández', 'Guillermo1', 'guillermo@gmail.com', 777777777,'Admin');

INSERT INTO `usuario`(`nombre`, `apellidos`, `contrasena`, `email`, `telefono`, `rol`) VALUES ('José', ' Fontana', 'Jose1', 'jose@gmail.com', 777777777, 'Común');

INSERT INTO `usuario`(`nombre`, `apellidos`, `contrasena`, `email`, `telefono`, `rol`) VALUES ('Francisco', 'Rivera', 'Francisco1', 'francisco@gmail.com', 777777777,'Admin');



INSERT INTO `serie`(`imagenUrl`, `nombre`) VALUES ('one_piece.png','One Piece');

INSERT INTO `serie`(`imagenUrl`, `nombre`) VALUES ('dragon_ball_z.png', 'Dragon Ball Z');

INSERT INTO `serie`(`imagenUrl`, `nombre`) VALUES ('kimetsu_no_yaiba.png', 'Kimetsu No Yaiba');




INSERT INTO `personaje`(`nombre`, `idSerie`, `imagenUrl`) VALUES ('Luffy', 1, 'luffy.png');

INSERT INTO `personaje`(`nombre`, `idSerie`, `imagenUrl`) VALUES ('Goku', 2, 'goku.png');

INSERT INTO `personaje`(`nombre`, `idSerie`, `imagenUrl`) VALUES ('Giyu', 3, 'giyu.png');

INSERT INTO `personaje`(`nombre`, `idSerie`, `imagenUrl`) VALUES ('Piccolo', 2, 'piccolo.png');

INSERT INTO `personaje`(`nombre`, `idSerie`, `imagenUrl`) VALUES ('Rengoku', 3, 'rengoku.png');

INSERT INTO `personaje`(`nombre`, `idSerie`, `imagenUrl`) VALUES ('Zoro', 1, 'zoro.png');

INSERT INTO `personaje`(`nombre`, `idSerie`, `imagenUrl`) VALUES ('Shinobu', 3, 'shinobu.png');

INSERT INTO `personaje`(`nombre`, `idSerie`, `imagenUrl`) VALUES ('Vegeta', 2, 'vegeta.png');

INSERT INTO `personaje`(`nombre`, `idSerie`, `imagenUrl`) VALUES ('Yamato', 1, 'yamato.png');




INSERT INTO `material`(`nombre`) VALUES ('PVC');

INSERT INTO `proveedor`(`nombre`,`imagenUrl`) VALUES ('Banpresto', 'banpresto.jpg');

INSERT INTO `proveedor`(`nombre`,`imagenUrl`) VALUES ('Kotobukiya', 'kotobukiya.jpg');

INSERT INTO `proveedor`(`nombre`,`imagenUrl`) VALUES ('Ichibansho', 'ichibansho.jpg');



INSERT INTO `figura`(`nombre`, `descripcion`, `fechaSalida`, `precio`, `stock`, `altura`, `idPersonaje`, `idProveedor`,`idMaterial`) VALUES ('Figura Monkey D. Luffy One Piece Banpresto Chronicle King Of Artist 18cm','¡Añade la figura de The Monkey D. Luffy de la colección Banpresto Chronicle King Of Artist de figuras de One Piece! Banpresto para la colección Banpresto Chronicle King Of Artist presenta la figura de The Monkey D. Luffy, del popular manga y anime “One Piece”. Esta figura está hecha en PVC mide unos 18 cm de alto e incluye una base soporte especial para exposición.','2023-05-12','59.99','50','18','1','1','1');

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Monkey_D._Luffy_One_Piece_Banpresto_Chronicle_King_Of_Artist_18cm_1.jpg','1');

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Monkey_D._Luffy_One_Piece_Banpresto_Chronicle_King_Of_Artist_18cm_2.jpg','1');
INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Monkey_D._Luffy_One_Piece_Banpresto_Chronicle_King_Of_Artist_18cm_3.jpg','1');


INSERT INTO `figura`(`nombre`, `descripcion`, `fechaSalida`, `precio`, `stock`, `altura`, `idPersonaje`, `idProveedor`, `idMaterial`) VALUES ('Figura Goku Super Saiyan Dragon Ball Super Super Zenkai Solid Vol. 1 19cm','¡Añade la figura de Goku Super Saiyan a tu colección de figuras de Dragon Ball Super!

Banpresto presenta la figura de Super Saiyan Son Goku de la popular serie de anime “Dragon Ball Super”. Esta figura está hecha en PVC, mide unos 19 cm de alto e incluye una base soporte para exposición.','2023-05-12',30.99,'40',19,2,1,1);

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Goku_Super_Saiyan_Dragon_Ball_Super_Super_Zenkai_Solid_Vol._1_19cm_1.jpg',2);
INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Goku_Super_Saiyan_Dragon_Ball_Super_Super_Zenkai_Solid_Vol._1_19cm_2.jpg',2);
INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Goku_Super_Saiyan_Dragon_Ball_Super_Super_Zenkai_Solid_Vol._1_19cm_3.jpg',2);




INSERT INTO `figura`(`nombre`, `descripcion`, `fechaSalida`, `precio`, `stock`, `altura`, `idPersonaje`, `idProveedor`, `idMaterial`) VALUES ('Figura Monkey D. Luffy One Piece Kotobukiya Chronicle King Of Artist 18cm','¡Añade la figura de The Monkey D. Luffy de la colección Banpresto Chronicle King Of Artist de figuras de One Piece! Banpresto para la colección Banpresto Chronicle King Of Artist presenta la figura de The Monkey D. Luffy, del popular manga y anime “One Piece”. Esta figura está hecha en PVC mide unos 18 cm de alto e incluye una base soporte especial para exposición.','2023-05-12','43.99','20','18','1','1',1);


INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Monkey_D._Luffy_One_Piece_Kotobukiya_Chronicle_King_Of_Artist_18cm_1.jpg','3');

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Monkey_D._Luffy_One_Piece_Kotobukiya_Chronicle_King_Of_Artist_18cm_2.jpg','3');
INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Monkey_D._Luffy_One_Piece_Kotobukiya_Chronicle_King_Of_Artist_18cm_3.jpg','3');

	




INSERT INTO `figura`(`nombre`, `descripcion`, `fechaSalida`, `precio`, `stock`, `altura`, `idPersonaje`, `idProveedor`, `idMaterial`) VALUES ('Figura Giyu Ichibansho Chronicle King Of Artist 18cm','¡Añade la figura de The Monkey D. Luffy de la colección Banpresto Chronicle King Of Artist de figuras de One Piece! Banpresto para la colección Banpresto Chronicle King Of Artist presenta la figura de The Monkey D. Luffy, del popular manga y anime “One Piece”. Esta figura está hecha en PVC mide unos 18 cm de alto e incluye una base soporte especial para exposición.','2023-05-12','45.99','20','18','3','2',1);


INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Giyu_Ichibansho_Chronicle_King_Of_Artist_18cm_1.jpg','4');

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Giyu_Ichibansho_Chronicle_King_Of_Artist_18cm_2.jpg','4');
INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Giyu_Ichibansho_Chronicle_King_Of_Artist_18cm_3.jpg','4');



INSERT INTO `figura`(`nombre`, `descripcion`, `fechaSalida`, `precio`, `stock`, `altura`, `idPersonaje`, `idProveedor`, `descuento`, `idMaterial`) VALUES ('Figura Piccolo King Of Artist 18cm','¡Añade la figura de The Monkey D. Luffy de la colección Banpresto Chronicle King Of Artist de figuras de One Piece! Banpresto para la colección Banpresto Chronicle King Of Artist presenta la figura de The Monkey D. Luffy, del popular manga y anime “One Piece”. Esta figura está hecha en PVC mide unos 18 cm de alto e incluye una base soporte especial para exposición.','2023-05-12','51.99','20','18','4','1',15,1);


INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Piccolo_King_Of_Artist_18cm_1.jpg','5');

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Piccolo_King_Of_Artist_18cm_2.jpg','5');
INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Piccolo_King_Of_Artist_18cm_3.jpg','5');




INSERT INTO `figura`(`nombre`, `descripcion`, `fechaSalida`, `precio`, `stock`, `altura`, `idPersonaje`, `idProveedor`, `descuento`, `idMaterial`) VALUES ('Figura Vegeta Ichibansho Chronicle King Of Artist 18cm','¡Añade la figura de The Monkey D. Luffy de la colección Banpresto Chronicle King Of Artist de figuras de One Piece! Banpresto para la colección Banpresto Chronicle King Of Artist presenta la figura de The Monkey D. Luffy, del popular manga y anime “One Piece”. Esta figura está hecha en PVC mide unos 18 cm de alto e incluye una base soporte especial para exposición.','2022-05-12','51.99','20','18','8','3','27',1);


INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Vegeta_Ichibansho_Chronicle_King_Of_Artist_18cm_1.jpg','6');
INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Vegeta_Ichibansho_Chronicle_King_Of_Artist_18cm_2.jpg','6');
INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Vegeta_Ichibansho_Chronicle_King_Of_Artist_18cm_3.jpg','6');




INSERT INTO `figura`(`nombre`, `descripcion`, `fechaSalida`, `precio`, `stock`, `altura`, `idPersonaje`, `idProveedor`, `descuento`, `idMaterial`) VALUES ('Figura Rengoku Ichibansho Chronicle King Of Artist 18cm','¡Añade la figura de The Monkey D. Luffy de la colección Banpresto Chronicle King Of Artist de figuras de One Piece!

Banpresto para la colección Banpresto Chronicle King Of Artist presenta la figura de The Monkey D. Luffy, del popular manga y anime “One Piece”. Esta figura está hecha en PVC mide unos 18 cm de alto e incluye una base soporte especial para exposición.','2023-05-12','51.99','20','18','5','2','25',1);


INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Rengoku_Ichibansho_Chronicle_King_Of_Artist_18cm_1.jpg','7');

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Rengoku_Ichibansho_Chronicle_King_Of_Artist_18cm_2.jpg','7');
INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Rengoku_Ichibansho_Chronicle_King_Of_Artist_18cm_3.jpg','7');




INSERT INTO `figura`(`nombre`, `descripcion`, `fechaSalida`, `precio`, `stock`, `altura`, `idPersonaje`, `idProveedor`,`idMaterial`) VALUES ('Figura Monkey D. Luffy One Piece Grandista Nero','¡Añade la figura de The Monkey D. Luffy de la colección Banpresto Chronicle King Of Artist de figuras de One Piece!

Banpresto para la colección Banpresto Chronicle King Of Artist presenta la figura de The Monkey D. Luffy, del popular manga y anime “One Piece”. Esta figura está hecha en PVC mide unos 18 cm de alto e incluye una base soporte especial para exposición.','2023-05-12','89.99','50','18','1','1','1');

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Monkey_D._Luffy_One_Piece_Grandista_Nero_1.jpg','8');

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Monkey_D._Luffy_One_Piece_Grandista_Nero_2.jpg','8');
INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Monkey_D._Luffy_One_Piece_Grandista_Nero_3.jpg','8');



INSERT INTO `figura`(`nombre`, `descripcion`, `fechaSalida`, `precio`, `stock`, `altura`, `idPersonaje`, `idProveedor`,`idMaterial`) VALUES ('Figura Luffy One Piece - Scultures Big Colloseum VI Vol.3 8cm','¡Añade la figura de The Monkey D. Luffy de la colección Banpresto Chronicle King Of Artist de figuras de One Piece!

Banpresto para la colección Banpresto Chronicle King Of Artist presenta la figura de The Monkey D. Luffy, del popular manga y anime “One Piece”. Esta figura está hecha en PVC mide unos 18 cm de alto e incluye una base soporte especial para exposición.','2023-05-12','89.99','50','18','1','1','1');

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Luffy_One_Piece_-_Scultures_Big_Colloseum_VI_Vol.3_8cm_1.jpg','9');

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Luffy_One_Piece_-_Scultures_Big_Colloseum_VI_Vol.3_8cm_2.jpg','9');
INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Luffy_One_Piece_-_Scultures_Big_Colloseum_VI_Vol.3_8cm_3.jpg','9');





INSERT INTO `figura`(`nombre`, `descripcion`, `fechaSalida`, `precio`, `stock`, `altura`, `idPersonaje`, `idProveedor`,`idMaterial`) VALUES ('Figura Monkey D Luffy II One Piece - Battle Record Collection 15cm','¡Añade la figura de The Monkey D. Luffy de la colección Banpresto Chronicle King Of Artist de figuras de One Piece!

Banpresto para la colección Banpresto Chronicle King Of Artist presenta la figura de The Monkey D. Luffy, del popular manga y anime “One Piece”. Esta figura está hecha en PVC mide unos 18 cm de alto e incluye una base soporte especial para exposición.','2023-05-12','89.99','50','18','1','1','1');

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Monkey_D_Luffy_II_One_Piece_-_Battle_Record_Collection_15cm_1.jpg','10');

INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Monkey_D_Luffy_II_One_Piece_-_Battle_Record_Collection_15cm_2.jpg','10');
INSERT INTO `imagen`(`url`, `idFigura`) VALUES ('Figura_Monkey_D_Luffy_II_One_Piece_-_Battle_Record_Collection_15cm_3.jpg','10');


















INSERT INTO `listadeseos` (`idUsuario`, `idFigura`) VALUES
(4, 4);



INSERT INTO `pedido` (`id`, `fecha`, `estado`, `idUsuario`, `direccion`) VALUES
(1, '2023-06-15', 'Enviado', 4, 'España - Sevilla - Guillena - Calle Málaga 12'),
(2, '2023-06-15', 'Cancelado', 4, 'España - Sevilla - Guillena - Calle Málaga 12'),
(3, '2023-06-15', 'Entregado', 1, 'España - Sevilla - Camas - Calle Málaga 12'),
(4, '2023-06-15', 'Pendiente de envío', 6, 'España - Sevilla - Camas - Calle Málaga 12'),
(5, '2023-06-15', 'Cancelado', 1, 'España - Sevilla - Camas - Avenida Andalucía 1'),
(6, '2023-06-15', 'Pendiente de envío', 2, 'España - Sevilla - Camas - Avenida Andalucía 1'),
(7, '2023-06-15', 'Pendiente de envío', 4, 'España - Sevilla - Camas - Avenida Andalucía 5'),
(8, '2023-06-15', 'Enviado', 4, 'España - Sevilla - Camas - Avenida Andalucía 5'),
(9, '2023-06-15', 'Pendiente de envío', 1, 'España - Sevilla - Camas - Avenida Andalucía 1'),
(10, '2023-06-15', 'Cancelado', 1, 'España - Sevilla - Camas - Avenida Andalucía 1'),
(11, '2023-06-15', 'Enviado', 1, 'España - Sevilla - Camas - Avenida Andalucía 1'),
(12, '2023-06-15', 'Pendiente de envío', 1, 'España - Sevilla - Camas - Avenida Andalucía 1'),
(13, '2023-06-15', 'Pendiente de envío', 1, 'España - Cádiz - Rota - Calle Alonso 33'),
(16, '2023-06-16', 'Pendiente de envío', 1, 'dfs - dsf - df - dfs 23');


INSERT INTO `venta` (`id`, `cantidad`, `precioUd`, `idPedido`, `idFigura`) VALUES
(1, 1, '55.64', 1, 4),
(2, 4, '72.58', 1, 1),
(3, 1, '72.58', 2, 1),
(4, 1, '72.58', 3, 1),
(5, 1, '34.86', 4, 2),
(6, 1, '47.17', 5, 7),
(7, 1, '37.96', 6, 6),
(8, 1, '72.58', 7, 1),
(9, 1, '53.45', 7, 5),
(10, 1, '37.48', 8, 2),
(11, 1, '55.64', 9, 4),
(12, 1, '47.17', 10, 7),
(13, 1, '37.48', 11, 2),
(14, 1, '55.64', 12, 4),
(15, 1, '37.48', 13, 2),
(18, 10, '37.48', 16, 2);



