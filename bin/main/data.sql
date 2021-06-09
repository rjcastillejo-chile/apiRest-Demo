DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS phone;


  
CREATE TABLE usuario (
  id VARCHAR(200)  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  password VARCHAR(250) DEFAULT NULL,
  created DATE DEFAULT NULL,
  modified DATE DEFAULT NULL,
  isactive boolean DEFAULT true,
  last_login DATE DEFAULT NULL
);

INSERT INTO usuario (id,username, email, password,created,modified,isactive,last_login) VALUES
  ('1','Ronny', 'rjcastillejo@gmail.com', '$2a$10$D2/hTHyJ/RYOXfCxjaqm8ed2C5K1EJyGcwvNuFQvh31c5jYej.HQ.','2020-01-01','2020-01-01',true,'2020-01-01'),
  ('2','Bill', 'bill@gmail.com', '$2a$10$D2/hTHyJ/RYOXfCxjaqm8ed2C5K1EJyGcwvNuFQvh31c5jYej.HQ.','2020-01-01','2020-01-01',true,'2020-01-01'),
  ('3','Folrunsho', 'folrunsho@gmail.com', '$2a$10$D2/hTHyJ/RYOXfCxjaqm8ed2C5K1EJyGcwvNuFQvh31c5jYej.HQ.','2020-01-01','2020-01-01',true,'2020-01-01');
  
  CREATE TABLE phone (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  number VARCHAR(10) NOT NULL,
  citycode VARCHAR(10) NOT NULL,
  contrycode VARCHAR(10) NOT NULL,
  usuario_id VARCHAR(200) NOT NULL
);

INSERT INTO phone (number, citycode, contrycode,usuario_id) VALUES
  ('951252777', '1', '1','1');