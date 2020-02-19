--<ScriptOptions statementTerminator=";"/>

CREATE TABLE CLIENTE (
		CPF NUMBER NOT NULL,
		NOME VARCHAR2(80) NOT NULL,
		EMAIL VARCHAR2(40) NOT NULL
	);

CREATE UNIQUE INDEX CLIENTE_PK ON CLIENTE (CPF ASC);

ALTER TABLE CLIENTE ADD CONSTRAINT CLIENTE_PK PRIMARY KEY (CPF);

