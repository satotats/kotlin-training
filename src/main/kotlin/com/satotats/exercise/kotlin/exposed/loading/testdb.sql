CREATE TABLE parent
(
    id   INT          NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE child
(
    id        INT          NOT NULL AUTO_INCREMENT,
    parent_id INT          NOT NULL,
    name      VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (parent_id) REFERENCES parent
--     UNIQUE INDEX (parent_id)
);

