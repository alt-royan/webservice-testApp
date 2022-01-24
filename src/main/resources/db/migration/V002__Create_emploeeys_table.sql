CREATE TABLE Employee (
    Id BIGINT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(30) NOT NULL,
    Surname VARCHAR(30) NOT NULL,
    Phone VARCHAR(15),
    Department_Id BIGINT,
    FOREIGN KEY (Department_Id) REFERENCES Department(Id),
    PRIMARY KEY (Id)
);