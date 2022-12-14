create table sensor(
    id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name varchar(30) NOT NULL UNIQUE
);

create table measurement(
    id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    value numeric(4,1) NOT NULL,
    raining varchar(10) NOT NULL,
    measured_at timestamp,
    sensor_name varchar(30) REFERENCES Sensor(name) ON DELETE SET NULL
);

insert into sensor(name) values ('Moscow_1');
insert into sensor(name) values ('Moscow_2');
insert into sensor(name) values ('Moscow_3');

insert into measurement(value, raining, measured_at, sensor_name) values (18.9, 'true', CURRENT_TIMESTAMP, 'Moscow_1');
insert into measurement(value, raining, measured_at, sensor_name) values (17.6, 'false', CURRENT_TIMESTAMP, 'Moscow_1');
insert into measurement(value, raining, measured_at, sensor_name) values (16.2, 'false', CURRENT_TIMESTAMP, 'Moscow_2');
insert into measurement(value, raining, measured_at, sensor_name) values (19.0, 'false', CURRENT_TIMESTAMP, 'Moscow_3');
insert into measurement(value, raining, measured_at, sensor_name) values (18.7, 'true', CURRENT_TIMESTAMP, 'Moscow_1');
insert into measurement(value, raining, measured_at, sensor_name) values (17.4, 'true', CURRENT_TIMESTAMP, 'Moscow_3');
insert into measurement(value, raining, measured_at, sensor_name) values (17.5, 'false', CURRENT_TIMESTAMP, 'Moscow_2');
insert into measurement(value, raining, measured_at, sensor_name) values (18.2, 'true', CURRENT_TIMESTAMP, 'Moscow_1');