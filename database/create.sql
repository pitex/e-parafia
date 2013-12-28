----   eParafia    ----

CREATE SEQUENCE ID_SEQ increment by 1 start with 1;

CREATE TABLE KAPLANI (
	pesel char(11) CONSTRAINT pk_kapl PRIMARY KEY,
	imie varchar(100) NOT NULL,
	nazwisko varchar(100) NOT NULL,
	funkcja varchar(20)
);

CREATE TABLE PARAFIANIE (
	pesel char(11) CONSTRAINT pk_para PRIMARY KEY,
	imie varchar(100) NOT NULL,
	nazwisko varchar(100) NOT NULL,
	adres varchar(500) NOT NULL,
	zyje boolean DEFAULT TRUE;
);

CREATE TABLE POMOCNICY (
	pesel REFERENCES parafianie(pesel),
	funkcja varchar(20) CONSTRAINT f_pomoc in('MINISTRANT', 'LEKTOR', 'SZAFARZ')
);

CREATE TABLE CHRZTY (
	id numeric CONSTRAINT pk_chrz PRIMARY KEY,
	pesel_dziecka char(11) CONSTRAINT fk_para_dz REFERENCES parafianie(pesel),
	--TO TRZEBA ZMIENIC - CHRZEST MA DODAWAC DZIECA DO PARAFIAN
	pesel_matki char(11),
	pesel_ojca char(11),
	pesel_matki_chrz char(11),
	pesel_ojca_chrz char(11),
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date NOT NULL
);

CREATE TABLE PIERWSZE_KOMUNIE (
	id numeric CONSTRAINT pk_komu PRIMARY KEY,
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel),
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date NOT NULL
);

CREATE TABLE BIERZMOWANIA (
	id numeric CONSTRAINT pk_bierz PRIMARY KEY,
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel),
	pesel_swiadka char(11) NOT NULL,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date NOT NULL
);

CREATE TABLE SLUBY (
	id numeric CONSTRAINT pk_slub PRIMARY KEY,
	pesel_zony char(11) NOT NULL,
	pesel_meza char(11) NOT NULL, --TODO trigger czy ktores jest z parafii
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date NOT NULL
);

CREATE TABLE POGRZEBY (
	id numeric CONSTRAINT pk_pogrz PRIMARY KEY,
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel),
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date NOT NULL
--dodad trigger usuwajacy parafianina
);

CREATE TABLE WIZYTY_DUSZPASTERSKIE (
	id numeric CONSTRAINT pk_wiz PRIMARY KEY,
	adres varchar(100) NOT NULL,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date NOT NULL
);

CREATE VIEW ZMARLI AS SELECT pesel, imie, nazwisko, adres, data FROM parafianie NATURAL JOIN pogrzeby WHERE zyje = 0;