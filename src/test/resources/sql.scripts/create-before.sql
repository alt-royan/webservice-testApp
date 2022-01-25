delete from employee;
delete from department;

INSERT INTO department VALUES
                           (1,'ACCOUNTING'),
                           (2,'CUSTOMER'),
                           (3,'DEVELOPMENT'),
                           (4,'LEGAL');

insert into employee values
(1, 'Владимир', 'Костиков', '+78005694585', '2'),
(2, 'Андрей', 'Кузнецов', '+79995694585', '1'),
(3, 'Елена', 'Ларина', '+78082698956', null),
(4, 'Ольга', 'Панфилова', '+79996663322', null),
(5, 'Олег', 'Титов', '+75656665555', '2');

ALTER TABLE employee AUTO_INCREMENT = 6;

