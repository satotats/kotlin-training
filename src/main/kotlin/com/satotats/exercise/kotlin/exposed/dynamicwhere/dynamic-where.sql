CREATE TABLE persons
(
    id    INT         NOT NULL AUTO_INCREMENT,
    name  VARCHAR(50) NOT NULL,
    hobby VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO persons(name, hobby)
VALUES ('ビル・ゲイツ', '手芸'),
       ('スティーブ・ジョブズ', '猫の毛玉集め');


