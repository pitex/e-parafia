CREATE TABLE parafianie (
	pesel char(11) PRIMARY KEY,
	imie varchar(100),
	nazwisko varchar(100),
	urodzony date,
	adres varchar(500)
);

CREATE TABLE kaplani (
	pesel char(11) REFERENCES parafianie(pesel),
	funkcja varchar(20)
);