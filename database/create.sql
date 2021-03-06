----   eParafia    ----

BEGIN;

CREATE SEQUENCE ID_SEQ increment by 1 start with 1;

CREATE TABLE KAPLANI (
	pesel char(11) CONSTRAINT pk_kapl PRIMARY KEY,
	imie varchar(100) NOT NULL,
	nazwisko varchar(100) NOT NULL,
	funkcja varchar(20) NOT NULL
);

CREATE TABLE POMOCNICY_FUNKCJE (
	nazwa varchar(100) CONSTRAINT pk_funkcja PRIMARY KEY
);

CREATE TABLE PARAFIANIE (
	pesel char(11) CONSTRAINT pk_para PRIMARY KEY,
	imie varchar(100) NOT NULL,
	drugie_imie varchar(100),
	trzecie_imie varchar(100),
	nazwisko varchar(100) NOT NULL,
	adres varchar(500),
	funkcja_pomocnika varchar(100) CONSTRAINT fk_para_fun REFERENCES POMOCNICY_FUNKCJE(nazwa),
	zyje boolean DEFAULT TRUE
);

CREATE TABLE CHRZTY (
	id numeric CONSTRAINT pk_chrz PRIMARY KEY DEFAULT nextval('ID_SEQ'),
	pesel_dziecka char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel) UNIQUE,
	pesel_matki char(11) NOT NULL,
	pesel_ojca char(11) NOT NULL,
	pesel_matki_chrz char(11) NOT NULL,
	pesel_ojca_chrz char(11) NOT NULL,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	ofiara numeric(6, 2),
	data date NOT NULL
);

CREATE TABLE PIERWSZE_KOMUNIE (
	id numeric CONSTRAINT pk_komu PRIMARY KEY DEFAULT nextval('ID_SEQ'),
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel) UNIQUE,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date NOT NULL
);

CREATE TABLE BIERZMOWANIA (
	id numeric CONSTRAINT pk_bierz PRIMARY KEY DEFAULT nextval('ID_SEQ'),
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel) UNIQUE,
	pesel_swiadka char(11) NOT NULL,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date NOT NULL
);

CREATE TABLE SLUBY (
	id numeric CONSTRAINT pk_slub PRIMARY KEY DEFAULT nextval('ID_SEQ'),
	pesel_zony char(11) NOT NULL UNIQUE,
	pesel_meza char(11) NOT NULL UNIQUE,
	pesel_swiadka_zony char(11) NOT NULL,
	pesel_swiadka_meza char(11) NOT NULL,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	ofiara numeric(6, 2),
	data date NOT NULL
);

CREATE TABLE POGRZEBY (
	id numeric CONSTRAINT pk_pogrz PRIMARY KEY DEFAULT nextval('ID_SEQ'),
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel) UNIQUE,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	ofiara numeric(6, 2),
	data date NOT NULL
);

CREATE TABLE WIZYTY_DUSZPASTERSKIE (
	id numeric CONSTRAINT pk_wiz PRIMARY KEY DEFAULT nextval('ID_SEQ'),
	adres varchar(500) NOT NULL,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	ofiara numeric(6, 2),
	data date NOT NULL
);

CREATE TABLE INTENCJE_MSZALNE (
	id numeric CONSTRAINT pk_inte PRIMARY KEY DEFAULT nextval('ID_SEQ'),
	opis varchar(1000),
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	ofiara numeric(6,2),
	data date NOT NULL
);

--------------------------------------------------	PERSPEKTYWY	--------------------------------------------------


CREATE VIEW zmarli AS SELECT pesel, imie, nazwisko, adres, data FROM parafianie NATURAL JOIN pogrzeby WHERE zyje = FALSE;

CREATE VIEW aktywnosci_kaplanow AS
	SELECT id, 'CHRZEST' AS typ, pesel_kapl, data FROM chrzty UNION ALL
	SELECT id, 'KOMUNIA' AS typ, pesel_kapl, data FROM pierwsze_komunie UNION ALL
	SELECT id, 'BIERZMOWANIE' AS typ, pesel_kapl, data FROM bierzmowania UNION ALL
	SELECT id, 'SLUB' AS typ, pesel_kapl, data FROM sluby UNION ALL
	SELECT id, 'POGRZEB' AS typ, pesel_kapl, data FROM pogrzeby UNION ALL
	SELECT id, 'WIZYTA' AS typ, pesel_kapl, data FROM wizyty_duszpasterskie UNION ALL
	SELECT id, 'INTENCJA' AS typ, pesel_kapl, data FROM intencje_mszalne
	ORDER BY id;

CREATE VIEW chrzty_szczegoly AS 
	SELECT id, pesel_dziecka, imie, drugie_imie, nazwisko, pesel_matki, pesel_ojca, pesel_matki_chrz, pesel_ojca_chrz, pesel_kapl, ofiara, data
	FROM chrzty INNER JOIN parafianie ON pesel_dziecka = pesel
	ORDER BY id;

CREATE VIEW bierzmowania_szczegoly AS
	SELECT id, pesel, trzecie_imie, pesel_swiadka, pesel_kapl, data 
	FROM bierzmowania NATURAL JOIN parafianie
	ORDER BY id;

CREATE VIEW ofiary AS
	SELECT ofiara, 'CHRZEST' AS typ, data FROM CHRZTY UNION ALL
	SELECT ofiara, 'SLUB' AS typ, data FROM SLUBY UNION ALL
	SELECT ofiara, 'POGRZEB' AS typ, data FROM POGRZEBY UNION ALL
	SELECT ofiara, 'WIZYTA' AS typ, data FROM WIZYTY_DUSZPASTERSKIE UNION ALL
	SELECT ofiara, 'INTENCJA' AS typ, data FROM INTENCJE_MSZALNE
	ORDER BY data;

CREATE VIEW pomocnicy AS
	SELECT * FROM PARAFIANIE
	where funkcja_pomocnika is not null;

CREATE VIEW ministranci AS
	SELECT * FROM PARAFIANIE
	where funkcja_pomocnika = 'MINISTRANT';

CREATE VIEW lektorzy AS
	SELECT * FROM PARAFIANIE
	where funkcja_pomocnika = 'LEKTOR';

CREATE VIEW szafarze AS
	SELECT * FROM PARAFIANIE
	where funkcja_pomocnika = 'SZAFARZ';

--------------------------------------------------	TRIGGERS	--------------------------------------------------


-- counts check sum based on pesel (should be 0)
CREATE OR REPLACE FUNCTION count_pesel_checksum(pesel parafianie.pesel%TYPE) RETURNS int AS $$
DECLARE
	chk_sum int;
BEGIN
	chk_sum = 0;
	chk_sum = chk_sum + TO_NUMBER(substr(pesel,1,1),'9');
	chk_sum = chk_sum + TO_NUMBER(substr(pesel,2,1),'9')*3;
	chk_sum = chk_sum + TO_NUMBER(substr(pesel,3,1),'9')*7;
	chk_sum = chk_sum + TO_NUMBER(substr(pesel,4,1),'9')*9;
	chk_sum = chk_sum + TO_NUMBER(substr(pesel,5,1),'9');
	chk_sum = chk_sum + TO_NUMBER(substr(pesel,6,1),'9')*3;
	chk_sum = chk_sum + TO_NUMBER(substr(pesel,7,1),'9')*7;
	chk_sum = chk_sum + TO_NUMBER(substr(pesel,8,1),'9')*9;
	chk_sum = chk_sum + TO_NUMBER(substr(pesel,9,1),'9');
	chk_sum = chk_sum + TO_NUMBER(substr(pesel,10,1),'9')*3;
	chk_sum = chk_sum + TO_NUMBER(substr(pesel,11,1),'9');

	RETURN MOD(chk_sum,10);
END;
$$
LANGUAGE plpgsql;

-- checks if column 'pesel' is set right
CREATE OR REPLACE FUNCTION check_pesel() RETURNS trigger AS $check_pesel$
BEGIN
	IF NEW.pesel IS NULL OR count_pesel_checksum(NEW.pesel) != 0 THEN
		RAISE EXCEPTION 'Nieprawidlowy pesel';
	END IF;

	RETURN NEW;
END;
$check_pesel$
LANGUAGE plpgsql;

-- checks if child is already in parafianie, checks if either of parents is in parafianie, checks god parents' pesels, updates parafianie with child's data if needed
CREATE OR REPLACE FUNCTION handle_chrzest() RETURNS trigger AS $handle_chrzest$
BEGIN
	IF 	count_pesel_checksum(NEW.pesel_ojca) != 0 OR count_pesel_checksum(NEW.pesel_matki) != 0 OR 
		count_pesel_checksum(NEW.pesel_matki_chrz) != 0 OR count_pesel_checksum(NEW.pesel_ojca_chrz) != 0 OR
		count_pesel_checksum(NEW.pesel_dziecka) != 0 THEN
		RAISE EXCEPTION 'Nieprawidlowy pesel';
	END IF;


    IF NEW.pesel_dziecka IN (SELECT pesel FROM parafianie) THEN
            INSERT INTO chrzty VALUES (NEW.id, NEW.pesel_dziecka, NEW.pesel_matki, NEW.pesel_ojca, NEW.pesel_matki_chrz, NEW.pesel_ojca_chrz, NEW.pesel_kapl, NEW.ofiara, NEW.data);
    		RETURN NEW;
    END IF;

	INSERT INTO parafianie (pesel, imie, drugie_imie, nazwisko) VALUES (NEW.pesel_dziecka, NEW.imie, NEW.drugie_imie, NEW.nazwisko);
    INSERT INTO chrzty VALUES (NEW.id, NEW.pesel_dziecka, NEW.pesel_matki, NEW.pesel_ojca, NEW.pesel_matki_chrz, NEW.pesel_ojca_chrz, NEW.pesel_kapl, NEW.ofiara, NEW.data);

	RETURN NEW;
END;
$handle_chrzest$
LANGUAGE plpgsql;

-- checks if person is alive, updates parafianie
CREATE OR REPLACE FUNCTION handle_pogrzeb() RETURNS trigger AS $handle_pogrzeb$
DECLARE
	nazw parafianie.nazwisko%TYPE;
BEGIN
	IF (SELECT zyje FROM parafianie WHERE pesel = OLD.pesel) = FALSE THEN
		RAISE EXCEPTION 'Ekshumacja?';
	END IF;

	UPDATE parafianie SET zyje = FALSE WHERE pesel = OLD.pesel;
	
	RETURN NEW;
END;
$handle_pogrzeb$
LANGUAGE plpgsql;

-- checks best men's pesels and if either of ppl in parafianie
CREATE OR REPLACE FUNCTION handle_slub() RETURNS trigger AS $handle_slub$
BEGIN
	IF count_pesel_checksum(NEW.pesel_swiadka_zony) != 0 OR count_pesel_checksum(NEW.pesel_swiadka_zony) !=0 THEN
		RAISE EXCEPTION 'Nieprawidlowy pesel swiadka';
	END IF;

	IF NEW.pesel_meza IN (SELECT pesel FROM parafianie) OR NEW.pesel_zony IN (SELECT pesel FROM parafianie) THEN
		RETURN NEW;
	END IF;

	RAISE EXCEPTION 'Zadne nie jest z parafii';
END;
$handle_slub$
LANGUAGE plpgsql;

-- checks pesel
CREATE OR REPLACE FUNCTION handle_bierzmowanie() RETURNS trigger AS $handle_bierzmowanie$
BEGIN
	IF count_pesel_checksum(NEW.pesel_swiadka) != 0 THEN
		RAISE EXCEPTION 'Nieprawidlowy pesel swiadka';
	END IF;

	RETURN NEW;
END;
$handle_bierzmowanie$
LANGUAGE plpgsql;

CREATE TRIGGER check_pesel_para BEFORE INSERT ON parafianie
FOR EACH ROW EXECUTE PROCEDURE check_pesel();
CREATE TRIGGER check_pesel_kapl BEFORE INSERT ON kaplani
FOR EACH ROW EXECUTE PROCEDURE check_pesel();

CREATE TRIGGER handle_chrzest INSTEAD OF INSERT ON chrzty_szczegoly
FOR EACH ROW EXECUTE PROCEDURE handle_chrzest();

CREATE TRIGGER handle_bierzmowanie BEFORE INSERT ON bierzmowania
FOR EACH ROW EXECUTE PROCEDURE handle_bierzmowanie();

CREATE TRIGGER handle_slub BEFORE INSERT ON sluby
FOR EACH ROW EXECUTE PROCEDURE handle_slub();

CREATE TRIGGER handle_pogrzeb BEFORE DELETE ON pogrzeby
FOR EACH ROW EXECUTE PROCEDURE handle_pogrzeb();


--------------------------------------------------	RULES	--------------------------------------------------


CREATE RULE update_trzecie_imie AS ON INSERT TO bierzmowania_szczegoly DO INSTEAD 
(
	UPDATE parafianie SET trzecie_imie = NEW.trzecie_imie WHERE pesel = NEW.pesel;
	INSERT INTO bierzmowania VALUES (NEW.id, NEW.pesel, NEW.pesel_swiadka, NEW.pesel_kapl, NEW.data);
);

--------------------------------------------------	OBLIGATORY & CONSTANT DATA	--------------------------------------------------

INSERT INTO POMOCNICY_FUNKCJE(nazwa) VALUES('MINISTRANT');
INSERT INTO POMOCNICY_FUNKCJE(nazwa) VALUES('LEKTOR');
INSERT INTO POMOCNICY_FUNKCJE(nazwa) VALUES('SZAFARZ');


--------------------------------------------------	SAMPLE DATA	--------------------------------------------------


--parafianie
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('44051418519', 'Adam', 'Adamski', 'Torfowa 15');
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('44072055603', 'Ada', 'Adamska', 'Torfowa 15');
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('70081162014', 'Zbigniew', 'Adamski', 'Torfowa 15');
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('86051317009', 'Zofia', 'Kowalska', 'Torfowa 16');
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('87022855212', 'Jan', 'Kowalski', 'Torfowa 16');
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('13282273710', 'Maciej', 'Kowalski', 'Torfowa 16');
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('11311185216', 'Piotr', 'Kowalski', 'Torfowa 16');

--kaplani
INSERT INTO KAPLANI VALUES('82031310309', 'Andrzej', 'Pezarski', 'wikary');
INSERT INTO KAPLANI VALUES('78071873913', 'Grzegorz', 'Matecki', 'proboszcz');

--pomocnicy
--INSERT INTO POMOCNICY VALUES('87022855212', 'SZAFARZ');
--INSERT INTO POMOCNICY VALUES('13282273710', 'LEKTOR');
--INSERT INTO POMOCNICY VALUES('11311185216', 'MINISTRANT');

--Ada i Adam biora slub
INSERT INTO SLUBY VALUES(nextval('ID_SEQ'), '44051418519', '44072055603', '86051317009', '87022855212', '78071873913', 1000.0, date '2001-10-05');

--Zbigniew umiera
INSERT INTO POGRZEBY VALUES(nextval('ID_SEQ'), '44051418519', '82031310309', 1000.0, date '2001-10-06');

--Adam i Ada chrzcza dziecko
INSERT INTO chrzty_szczegoly VALUES(nextval('ID_SEQ'), '12231327906', 'Ida', 'Anna', 'Adamska', '44072055603', '44051418519','86051317009', '87022855212', '78071873913', 100.0, date '2005-10-05');

--Piotr - komunia
INSERT INTO PIERWSZE_KOMUNIE VALUES(nextval('ID_SEQ'), '11311185216', '78071873913', date '2005-10-05');

--Maciej - bierzmowanie
INSERT INTO bierzmowania_szczegoly VALUES(nextval('ID_SEQ'), '13282273710', 'Igor', '44072055603', '78071873913', date '2005-12-05');

--wizyty duszpasterskie
INSERT INTO WIZYTY_DUSZPASTERSKIE VALUES(nextval('ID_SEQ'), 'Torfowa 16', '78071873913', 9999.99, date '2013-12-30');
INSERT INTO WIZYTY_DUSZPASTERSKIE VALUES(nextval('ID_SEQ'), 'Torfowa 15', '82031310309', 9999.99, date '2013-12-30');

COMMIT;