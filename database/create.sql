----   eParafia    ----

CREATE SEQUENCE ID_SEQ increment by 1 start with 1;

CREATE TABLE KAPLANI (
	pesel char(11) CONSTRAINT pk_kapl PRIMARY KEY,
	imie varchar(100),
	nazwisko varchar(100),
	funkcja varchar(20)
);

CREATE TABLE PARAFIANIE (
	pesel char(11) CONSTRAINT pk_para PRIMARY KEY,
	imie varchar(100),
	nazwisko varchar(100),
	adres varchar(500)
);

CREATE TABLE POMOCNICY (
	pesel REFERENCES parafianie(pesel),
	funkcja varchar(100) CONSTRAINT f_pomoc in('MINISTRANT', 'LEKTOR', 'SZAFARZ')
);

CREATE TABLE ZMARLI (
	pesel char(11) CONSTRAINT pk_zmar PRIMARY KEY,
	imie varchar(100),
	nazwisko varchar(100),
	zmarly date
);

CREATE TABLE CHRZTY (
	id numeric CONSTRAINT fk_chrz REFERENCES AKTYWNOSCI_KAPLANOW(id),
	pesel_dziecka char(11) CONSTRAINT fk_para_dz REFERENCES parafianie(pesel),
	--TO TRZEBA ZMIENIC - CHRZEST MA DODAWAC DZIECA DO PARAFIAN
	pesel_matki char(11),
	pesel_ojca char(11),
	pesel_matki_chrz char(11),
	pesel_ojca_chrz char(11),
	data date,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel)
);

CREATE TABLE PIERWSZE_KOMUNIE (
	id numeric CONSTRAINT fk_komu REFERENCES AKTYWNOSCI_KAPLANOW(id),
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel),
	data date,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel)
);

CREATE TABLE BIERZMOWANIA (
	id numeric CONSTRAINT fk_bierz REFERENCES AKTYWNOSCI_KAPLANOW(id),
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel),
	pesel_swiadka char(11),
	data date,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel)
);

CREATE TABLE SLUBY (
	id numeric CONSTRAINT fk_slub REFERENCES AKTYWNOSCI_KAPLANOW(id),
	pesel_zony char(11),
	pesel_meza char(11),
	CONSTRAINT fk_para CHECK(pesel_zony REFERENCES parafianie(pesel) OR pesel_meza REFERENCES parafianie(pesel)),
	data date,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel)
);

CREATE TABLE POGRZEBY (
	id numeric CONSTRAINT fk_pogrz REFERENCES AKTYWNOSCI_KAPLANOW(id),
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel),
	data date,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel)
--dodad trigger usuwajacy parafianina
);

CREATE TABLE WIZYTY_DUSZPASTERSKIE (
	id numeric CONSTRAINT fk_wiz REFERENCES AKTYWNOSCI_KAPLANOW(id),
	adres char(100),
	data date,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel)
);