drop
database if exists finalproject;

create
database finalproject;

use
finalproject;

create table customer
(
    idcustomer int auto_increment,
    lastname   varchar(45),
    firstname  varchar(45),
    afm        varchar(45),
    telephone  varchar(45),
    primary key (idcustomer)
);

create table inventory
(
    idinv       int auto_increment,
    category    varchar(45),
    description varchar(45),
    price       int,
    quantity    int,
    primary key (idinv)
);

create table orders
(
    idOrder  int auto_increment,
    custid   int,
    invid    int,
    quantity int,
    price    int,
    primary key (idOrder),
    constraint foreign key (custid) references customer (idcustomer),
    constraint foreign key (invid) references inventory (idinv)
);

insert into inventory
values (null, 'Laptop', 'Dell Vostro i5', 400, 1);
insert into inventory
values (null,'Laptop', 'HP', 500, 2);
insert into inventory
values (null,'Keyboard', 'magic', 200, 3);
insert into inventory
values (null,'SSD', 'Kingston', 60, 4);
insert into inventory
values (null,'SSD', 'HP', 230, 5);


insert into customer
values (null,'georgiou', 'vasilis', '300234590', '6987909021');
insert into customer
values (null,'aivalis', 'costis', '300456789', '6987345678');
insert into customer
values (null,'papadimas', 'georgios', '500678945', '6934563213');
insert into customer
values (null,'vidakis', 'nicolas', '400987898', '6923457890');
insert into customer
values (null,'malamos', 'athanasios', '569123456', '6945459087');
