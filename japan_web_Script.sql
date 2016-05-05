-- create and select the database
DROP DATABASE IF EXISTS mma;
CREATE DATABASE mma;
USE mma;

-- create the tables
CREATE TABLE PageBasics (
  TabTitle	VARCHAR(100) NOT NULL,
  Heading	VARCHAR(100) NOT NULL,
  TypeID	INT NOT NULL
);


CREATE TABLE Section (
  SectionID   INT            PRIMARY KEY  AUTO_INCREMENT UNIQUE,
  Name       VARCHAR(20)    NOT NULL     UNIQUE,
  Link    	 VARCHAR(255),
  Image      VARCHAR(255),
  TypeID	 INT 			NOT NULL
);

CREATE TABLE Paragraph (
  SectionID		INT				NOT NULL UNIQUE,
  Text	VARCHAR(2550)
);

CREATE TABLE ListItems (
  RowNum	INT		UNIQUE		NOT NULL  	AUTO_INCREMENT,
  SectionID 		INT		NOT NULL,
  Text	VARCHAR(255)
);

CREATE TABLE SectionType (
  TypeID	INT		NOT NULL 	PRIMARY KEY		AUTO_INCREMENT,
  TypeName	VARCHAR(30)
);

-- insert some rows into the tables
INSERT INTO PageBasics VALUES 
('My Best Memories of Japan', 'My Best Memories of Japan', 1),
('My Favorite Japanese Foods', 'My Favorite Japanese Foods', 2);

INSERT INTO SectionType VALUES
(1, 'Experiences'),
(2, 'Meals');

INSERT INTO Section VALUES
(1, 'Climbing Mt. Fuji', 'https://sayonarasara.wordpress.com/2015/08/29/climbing-fujisan/', 'https://sayonarasara.files.wordpress.com/2015/08/20150822-_mg_9763.jpg', 1),
(2, 'Ajisai Tea Ceremony', 'https://sayonarasara.wordpress.com', 'https://sayonarasara.files.wordpress.com/2016/05/11011203_10105381115069083_1859806333758155620_n.jpg', 1),
(3, 'Hanami in Kanazawa', 'https://sayonarasara.wordpress.com/2014/05/10/kanazawa/', 'https://sayonarasara.files.wordpress.com/2014/05/img_7939.jpg?w=470&h=313', 1),
(4, 'New Year in Okinawa', 'https://sayonarasara.wordpress.com', 'https://sayonarasara.files.wordpress.com/2014/01/img_4066.jpg', 1),
(5, 'Ryokan Hospitality', 'https://sayonarasara.wordpress.com/2015/07/28/tateyama-kurobe-alpine-route/', 'https://sayonarasara.files.wordpress.com/2015/07/img_2453.jpg', 1),
(6, 'Monjayaki', '', '', 2),
(7, 'Curry', '', '', 2),
(8, 'Pizza', '', '', 2),
(9, 'Melon pan', '', '', 2),
(10, 'Wagashi', '', '', 2);


INSERT INTO Paragraph VALUES
(1, ''),
(2, ''),
(3, ''),
(4, ''),
(5, ''),
(6, ''),
(7, ''),
(8, ''),
(9, ''),
(10, '');

INSERT INTO ListItems VALUES
(1, 1, ''),
(2, 2, ''),
(3, 3, ''),
(4, 4, ''),
(5, 5, ''),
(6, 6, ''),
(7, 7, ''),
(8, 8, ''),
(9, 9, ''),
(10, 10, ''),


-- create a user and grant privileges to that user
GRANT SELECT, INSERT, DELETE, UPDATE
ON mma.*
TO mma_user@localhost
IDENTIFIED BY 'sesame';