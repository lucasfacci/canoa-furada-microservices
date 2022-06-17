DROP TABLE IF EXISTS tab_cabine;

CREATE TABLE tab_cabine (
    id_cabine INT AUTO_INCREMENT NOT NULL,
    max_pessoas INT NOT NULL,
    preco_pessoa REAL NOT NULL
);