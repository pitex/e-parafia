<<<<<<< HEAD
----   eParafia    ----

CREATE SEQUENCE ID_SEQ increment by 1 start with 1;


CREATE TABLE KAPLANI (
	pesel char(11) CONSTRAINT pk_kapl PRIMARY KEY,
	imie varchar(100),
	nazwisko varchar(100),
	funkcja varchar(20),
	urodzony date
);

CREATE TABLE PARAFIANIE (
	pesel char(11) CONSTRAINT pk_para PRIMARY KEY,
	imie varchar(100),
	nazwisko varchar(100),
	urodzony date,
	adres varchar(500)
);

CREATE TABLE MINISTRANCI (
);

CREATE TABLE LEKTORZY (
);

CREATE TABLE SZAFARZE (
);

CREATE TABLE WYDARZENIA (
);

CREATE TABLE CHRZTY (
	id numeric CONSTRAINT pk_chrz PRIMARY KEY,
	pesel_dziecka char(11) CONSTRAINT fk_para_dz REFERENCES parafianie(pesel),
	pesel_matki char(11),
	pesel_ojca char(11),
	pesel_matki_chrz char(11),
	pesel_ojca_chrz char(11),
	pesel_kaplana car(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel)
);

CREATE TABLE PIERWSZE_KOMUNIE (
	id numeric CONSTRAINT pk_komu PRIMARY KEY,
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel),
	data date
);

CREATE TABLE BIERZMOWANIA (
	id numeric CONSTRAINT pk_bierz PRIMARY KEY,
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel),
	pesel_swiadka cahr(11),
	data date
);

CREATE TABLE SLUBY (
	id numeric CONSTRAINT pk_malz PRIMARY KEY,
	pesel_zony char(11),
	pesel_meza char(11),
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	CONSTRAINT fk_para CHECK(pesel_zony REFERENCES parafianie(pesel) OR pesel_meza REFERENCES parafianie(pesel))
);

CREATE TABLE POGRZEBY (
	id numeric CONSTRAINT pk_pogrz PRIMARY KEY,
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel),
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date
);

CREATE TABLE WIZYTY_DUSZPASTERSKIE (
);