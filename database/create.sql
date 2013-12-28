CREATE TABLE parafianie (
	pesel char(11) CONSTRAINT pk_para PRIMARY KEY,
	imie varchar(100),
	nazwisko varchar(100),
	urodzony date,
	adres varchar(500)
);

CREATE TABLE kaplani (
	pesel char(11) REFERENCES parafianie(pesel),
	funkcja varchar(20)
);

CREATE TABLE chrzty (
	id numeric CONSTRAINT pk_chrz PRIMARY KEY,
	pesel_dziecka char(11) CONSTRAINT fk_para_dz REFERENCES parafianie(pesel),
	pesel_matki char(11),
	pesel_ojca char(11),
	pesel_matki_chrz char(11),
	pesel_ojca_chrz char(11),
	pesel_kaplana car(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel)
);

CREATE TABLE komunie (
	id numeric CONSTRAINT pk_komu PRIMARY KEY,
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel),
	data date
);