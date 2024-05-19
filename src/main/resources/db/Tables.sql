CREATE TABLE customer (
                       id INTEGER PRIMARY KEY auto_increment,
                       username NVARCHAR(255),
                       password NVARCHAR(255)
);

CREATE TABLE PLC (
                     id INT PRIMARY KEY,
                     Online BOOLEAN,
                     IPaddress VARCHAR(255),
                     OffsetID INT
);

CREATE TABLE terracotta (
                            id INTEGER PRIMARY KEY auto_increment,
                            name VARCHAR(50),
                            owner INT,
                            PLC INT,
                            plant INT,
                            planted_at TIMESTAMP
);

CREATE TABLE plantType (
                           id INTEGER PRIMARY KEY,
                           plantname VARCHAR(255),
                           growtimeindays INT
);

CREATE TABLE schedule (
                          id INT PRIMARY KEY auto_increment,
                          temp float,
                          light float,
                          humidity INT
);

CREATE TABLE cron (
                      id INT PRIMARY KEY auto_increment,
                      tracota INT,
                      Schedl INT,
                      start int,
                      endTime int
);

ALTER TABLE terracotta ADD FOREIGN KEY (owner) REFERENCES customer (id);

ALTER TABLE terracotta ADD FOREIGN KEY (PLC) REFERENCES PLC (id);

ALTER TABLE terracotta ADD FOREIGN KEY (plant) REFERENCES plantType (id);

ALTER TABLE cron ADD FOREIGN KEY (tracota) REFERENCES terracotta (id);

ALTER TABLE cron ADD FOREIGN KEY (Schedl) REFERENCES schedule (id);

insert into PLC (id, Online, IPaddress, OffsetID)  VALUES (1,true,'172.17.0.10',0);

insert into plantType (id, plantname, growtimeindays) VALUES (0,'Jalapenos',50);
insert into plantType (id, plantname, growtimeindays) VALUES (1,'Poblano',30);
insert into plantType (id, plantname, growtimeindays) VALUES (2,'Habareno',80);

insert into customer (username,password)  VALUES ('Filip','8d23cf6c86e834a7aa6eded54c26ce2bb2e74903538c61bdd5d2197997ab2f72');
insert into terracotta(name, owner, PLC, plant, planted_at) VALUES ('Testovaci',1,1,2,'2024-03-01');
insert into terracotta(name, owner, PLC, plant, planted_at) VALUES ('Testovaci2',1,1,0,'2024-04-27');

insert into schedule (temp, light, humidity) VALUES (30.0,1,0.6);
insert into schedule (temp, light, humidity) VALUES (25.0,0,0.4);
insert into schedule (temp, light, humidity) VALUES (31.0,1,0.6);
insert into schedule (temp, light, humidity) VALUES (26.0,0,0.4);

insert into cron (tracota, Schedl, start, endTime) VALUES (1,1,7,22);
insert into cron (tracota, Schedl, start, endTime) VALUES (1,2,23,6);
insert into cron (tracota, Schedl, start, endTime) VALUES (2,3,7,22);
insert into cron (tracota, Schedl, start, endTime) VALUES (2,4,23,6);

//create VIEW test as SELECT * FROM cron left join

//create trigger terracotta before DELETE