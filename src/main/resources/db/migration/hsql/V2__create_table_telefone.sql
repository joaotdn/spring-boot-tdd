CREATE SEQUENCE TELEFONE_ID_SEQ AS BIGINT START WITH 1 INCREMENT BY 1;

CREATE TABLE TELEFONE (
	ID BIGINT GENERATED BY DEFAULT AS SEQUENCE TELEFONE_ID_SEQ,
	DDD VARCHAR(2) NOT NULL,
	NUMERO VARCHAR(9) NOT NULL,
	ID_PESSOA BIGINT NOT NULL,
	
	PRIMARY KEY (ID),
	FOREIGN KEY (ID_PESSOA) REFERENCES PESSOA(ID)
);