DROP TABLE Greeting IF EXISTS;
CREATE TABLE Greeting(
    id NUMERIC GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL,
    text VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);