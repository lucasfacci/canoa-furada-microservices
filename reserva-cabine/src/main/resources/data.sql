DROP TABLE IF EXISTS tab_reserva_cabine;

CREATE TABLE tab_reserva_cabine (
	id_reserva_cabine INT AUTO_INCREMENT NOT NULL,
    id_cabine INT NOT NULL,
    data DATE NOT NULL,
    total_pessoas INT NOT NULL
);