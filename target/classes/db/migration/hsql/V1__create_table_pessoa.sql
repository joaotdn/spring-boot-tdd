CREATE SEQUENCE PESSOA_ID_SEQ AS BIGINT START WITH 1 INCREMENT BY 1;

CREATE TABLE PESSOA (
	ID BIGINT GENERATED BY DEFAULT AS SEQUENCE PESSOA_ID_SEQ,
	NOME VARCHAR(80) NOT NULL,
	CPF VARCHAR(11) NOT NULL,
	
	PRIMARY KEY (ID)
);