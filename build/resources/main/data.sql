DROP TABLE IF EXISTS usuario;


CREATE TABLE phone (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  number VARCHAR(10) NOT NULL,
  citycode VARCHAR(10) NOT NULL,
  contrycode VARCHAR(10) NOT NULL
);

INSERT INTO phone (number, citycode, contrycode) VALUES
  ('951252777', '1', '1');
  
CREATE TABLE usuario (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  password VARCHAR(250) DEFAULT NULL,
  phoneid INT NOT NULL
);

INSERT INTO usuario (name, email, password,phoneid) VALUES
  ('Aliko', 'aliko@gmail.com', '123456',1),
  ('Bill', 'bill@gmail.com', '123456',1),
  ('Folrunsho', 'folrunsho@gmail.com', '123456',1);