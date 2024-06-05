CREATE TABLE customer (
                        customer_id INTEGER PRIMARY KEY auto_increment,
                        username NVARCHAR(50),
                        password NVARCHAR(255),
                        token NVARCHAR(255)
);

CREATE TABLE PLC (
                     PLC_id INT PRIMARY KEY,
                     Online BOOLEAN,
                     IPaddress VARCHAR(255),
                     OffsetID INT
);

CREATE TABLE terracotta (
                            terracotta_id INTEGER PRIMARY KEY auto_increment,
                            name VARCHAR(50),
                            owner INT,
                            PLC INT,
                            plant INT,
                            planted_at TIMESTAMP
);

CREATE TABLE plantType (
                           plantType_id INTEGER PRIMARY KEY,
                           plantname VARCHAR(255),
                           growtimeindays INT
);

CREATE TABLE schedule (
                          schedule_id INT PRIMARY KEY auto_increment,
                          temp float,
                          light float,
                          humidity INT
);

CREATE TABLE cron (
                      cron_id INT PRIMARY KEY auto_increment,
                      tracota INT,
                      Schedl INT,
                      start int,
                      endTime int
);

ALTER TABLE terracotta ADD FOREIGN KEY (owner) REFERENCES customer (customer_id);

ALTER TABLE terracotta ADD FOREIGN KEY (PLC) REFERENCES PLC (PLC_id);

ALTER TABLE terracotta ADD FOREIGN KEY (plant) REFERENCES plantType (plantType_id);

ALTER TABLE cron ADD FOREIGN KEY (tracota) REFERENCES terracotta (terracotta_id);

ALTER TABLE cron ADD FOREIGN KEY (Schedl) REFERENCES schedule (schedule_id);

insert into PLC (PLC_id, Online, IPaddress, OffsetID)  VALUES (1,true,'172.17.0.10',0);

insert into plantType (plantType_id, plantname, growtimeindays) VALUES (0,'Jalapenos',50);
insert into plantType (plantType_id, plantname, growtimeindays) VALUES (1,'Poblano',30);
insert into plantType (plantType_id, plantname, growtimeindays) VALUES (2,'Habareno',80);

insert into customer (username,password)  VALUES ('Filip','8d23cf6c86e834a7aa6eded54c26ce2bb2e74903538c61bdd5d2197997ab2f72');
insert into terracotta(name, owner, PLC, plant, planted_at) VALUES ('Testovaci',1,1,2,'2024-04-05');
insert into terracotta(name, owner, PLC, plant, planted_at) VALUES ('Testovaci2',1,1,1,'2024-05-19');
insert into terracotta(name, owner, PLC, plant, planted_at) VALUES ('Testovaci3',1,1,0,'2024-05-20');

insert into schedule (temp, light, humidity) VALUES (30.0,1,0.6);
insert into schedule (temp, light, humidity) VALUES (25.0,0,0.4);
insert into schedule (temp, light, humidity) VALUES (31.0,1,0.6);
insert into schedule (temp, light, humidity) VALUES (26.0,0,0.4);
insert into schedule (temp, light, humidity) VALUES (26.0,1,1);

insert into cron (tracota, Schedl, start, endTime) VALUES (1,1,7,22);
insert into cron (tracota, Schedl, start, endTime) VALUES (1,2,23,6);
insert into cron (tracota, Schedl, start, endTime) VALUES (2,3,7,22);
insert into cron (tracota, Schedl, start, endTime) VALUES (2,4,23,6);
insert into cron (tracota, Schedl, start, endTime) VALUES (3,5,0,23);


create VIEW teracotaForHarvest as
    SELECT  name, plantname, growtimeindays,planted_at, terracotta_id, customer_id, username FROM plantType as pt
        left join terracotta as tc on pt.plantType_id = tc.plant
        left join customer as cs on tc.owner = cs.customer_id where customer_id>0 order by customer_id;

create VIEW loggedUsers as select username FROM customer where token is not null;

create VIEW VIP_Customers as SELECT username, COUNT(username) as TeracotaCount FROM customer left join terracotta on customer_id = owner
                            group by username order by COUNT(username)  desc;


CREATE TRIGGER DeleteTerracotta AFTER DELETE ON cron
    FOR EACH ROW call "com.example.triggers.CronDeleteTrigger";

CREATE TRIGGER CreateTeracotta AFTER INSERT On terracotta
    for each row call "com.example.triggers.TeracotaCreateTrigger";