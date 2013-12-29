----   eParafia    ----

CREATE SEQUENCE ID_SEQ increment by 1 start with 1;

CREATE TABLE KAPLANI (
	pesel char(11) CONSTRAINT pk_kapl PRIMARY KEY,
	imie varchar(100) NOT NULL,
	nazwisko varchar(100) NOT NULL,
	funkcja varchar(20) NOT NULL
);

CREATE TABLE PARAFIANIE (
	pesel char(11) CONSTRAINT pk_para PRIMARY KEY,
	imie varchar(100) NOT NULL,
	drugie_imie varchar(100),
	trzecie_imie varchar(100),
	nazwisko varchar(100) NOT NULL,
	adres varchar(500),
	zyje boolean DEFAULT TRUE
);

CREATE TABLE POMOCNICY (
	pesel char(11) REFERENCES parafianie(pesel),
	funkcja varchar(20) CONSTRAINT f_pomoc CHECK(funkcja in('MINISTRANT', 'LEKTOR', 'SZAFARZ'))
);

CREATE TABLE CHRZTY (
	id numeric CONSTRAINT pk_chrz PRIMARY KEY,
	pesel_dziecka char(11) CONSTRAINT fk_para_dz REFERENCES parafianie(pesel),
	imie varchar(100) NOT NULL,
	drugie_imie varchar(100),
	pesel_matki char(11) NOT NULL,
	pesel_ojca char(11) NOT NULL,
	pesel_matki_chrz char(11) NOT NULL,
	pesel_ojca_chrz char(11) NOT NULL,
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
	imie varchar(100) NOT NULL,
	pesel_swiadka char(11) NOT NULL,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date NOT NULL
);

CREATE TABLE SLUBY (
	id numeric CONSTRAINT pk_slub PRIMARY KEY,
	pesel_zony char(11) NOT NULL UNIQUE,
	pesel_meza char(11) NOT NULL UNIQUE,
	pesel_swiadka_zony char(11) NOT NULL,
	pesel_swiadka_meza char(11) NOT NULL,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date NOT NULL
);

CREATE TABLE POGRZEBY (
	id numeric CONSTRAINT pk_pogrz PRIMARY KEY,
	pesel char(11) CONSTRAINT fk_para REFERENCES parafianie(pesel),
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date NOT NULL
);

CREATE TABLE WIZYTY_DUSZPASTERSKIE (
	id numeric CONSTRAINT pk_wiz PRIMARY KEY,
	adres varchar(500) NOT NULL,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
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
	SELECT id, 'WIZYTA' AS typ, pesel_kapl, data FROM wizyty_duszpasterskie
	ORDER BY id;


--------------------------------------------------	TRIGGERS	--------------------------------------------------


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


CREATE OR REPLACE FUNCTION check_pesel() RETURNS trigger AS $check_pesel$
BEGIN
	IF NEW.pesel IS NULL OR count_pesel_checksum(NEW.pesel) != 0 THEN
		RAISE EXCEPTION 'Nieprawidlowy pesel';
	END IF;

	RETURN NEW;
END;
$check_pesel$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION handle_chrzest() RETURNS trigger AS $handle_chrzest$
DECLARE
	nazw parafianie.nazwisko%TYPE;
BEGIN
	IF NEW.pesel_dziecka IN (SELECT pesel FROM parafianie) THEN
		RETURN NEW;
	END IF;

	IF NEW.pesel_ojca IN (SELECT pesel FROM parafianie) THEN
		SELECT nazwisko INTO nazw FROM parafianie WHERE pesel = NEW.pesel_ojca;
	ELSIF NEW.pesel_matki IN (SELECT pesel FROM parafianie) THEN
		SELECT nazwisko INTO nazw FROM parafianie WHERE pesel = NEW.pesel_matki;
	ELSE
		RAISE EXCEPTION 'Zadne z rodzicow nie jest z parafii';
	END IF;

	IF 	count_pesel_checksum(NEW.pesel_ojca) != 0 OR count_pesel_checksum(NEW.pesel_matki) != 0 OR 
		count_pesel_checksum(NEW.pesel_matki_chrz) != 0 OR count_pesel_checksum(NEW.pesel_ojca_chrz) != 0 THEN
		RAISE EXCEPTION 'Nieprawidlowy pesel';
	END IF; 

	INSERT INTO parafianie (pesel, imie, drugie_imie, nazwisko) VALUES (NEW.pesel_dziecka, NEW.imie, NEW.drugie_imie, nazw);

	RETURN NEW;
END;
$handle_chrzest$
LANGUAGE plpgsql;

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

CREATE OR REPLACE FUNCTION give_id() RETURNS trigger AS $give_id$
BEGIN
	NEW.id = nextval('ID_SEQ');
	
	RETURN NEW;
END;
$give_id$
LANGUAGE plpgsql;

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

CREATE TRIGGER handle_chrzest BEFORE INSERT ON chrzty
FOR EACH ROW EXECUTE PROCEDURE handle_chrzest();

CREATE TRIGGER handle_bierzmowanie BEFORE INSERT ON bierzmowania
FOR EACH ROW EXECUTE PROCEDURE handle_bierzmowanie();

CREATE TRIGGER handle_slub BEFORE INSERT ON sluby
FOR EACH ROW EXECUTE PROCEDURE handle_slub();

CREATE TRIGGER handle_pogrzeb BEFORE DELETE ON pogrzeby
FOR EACH ROW EXECUTE PROCEDURE handle_pogrzeb();

CREATE TRIGGER give_id_chrzty BEFORE INSERT ON chrzty
FOR EACH ROW EXECUTE PROCEDURE give_id();
CREATE TRIGGER give_id_komunia BEFORE INSERT ON pierwsze_komunie
FOR EACH ROW EXECUTE PROCEDURE give_id();
CREATE TRIGGER give_id_bierz BEFORE INSERT ON bierzmowania
FOR EACH ROW EXECUTE PROCEDURE give_id();
CREATE TRIGGER give_id_sluby BEFORE INSERT ON sluby
FOR EACH ROW EXECUTE PROCEDURE give_id();
CREATE TRIGGER give_id_pogrzeby BEFORE INSERT ON pogrzeby
FOR EACH ROW EXECUTE PROCEDURE give_id();


--------------------------------------------------	SAMPLE DATA	--------------------------------------------------


CREATE RULE update_trzecie_imie AS ON INSERT TO bierzmowania DO ALSO UPDATE parafianie SET trzecie_imie = NEW.imie WHERE pesel = NEW.pesel;


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
INSERT INTO POMOCNICY VALUES('87022855212', 'SZAFARZ');
INSERT INTO POMOCNICY VALUES('13282273710', 'LEKTOR');
INSERT INTO POMOCNICY VALUES('11311185216', 'MINISTRANT');

--Ada i Adam biora slub
INSERT INTO SLUBY VALUES(nextval('ID_SEQ'), '44051418519', '44072055603', '86051317009', '87022855212', '78071873913', date '2001-10-05');

--Zbigniew umiera
INSERT INTO POGRZEBY VALUES(nextval('ID_SEQ'), '44051418519', '82031310309', date '2001-10-06');

--Adam i Ada chrzcza dziecko
INSERT INTO CHRZTY VALUES(nextval('ID_SEQ'), '12231327906', 'Ida', 'Anna', '44072055603', '44051418519','86051317009', '87022855212', '78071873913', date '2005-10-05');

--Piotr - komunia
INSERT INTO PIERWSZE_KOMUNIE VALUES(nextval('ID_SEQ'), '11311185216', '78071873913', date '2005-10-05');

--Maciej - bierzmowanie
INSERT INTO BIERZMOWANIA VALUES(nextval('ID_SEQ'), '13282273710', 'Igor', '44072055603', '78071873913', date '2005-12-05');

--wizyty duszpasterskie
INSERT INTO WIZYTY_DUSZPASTERSKIE VALUES(nextval('ID_SEQ'), 'Torfowa 16', '78071873913', date '2013-12-30');
INSERT INTO WIZYTY_DUSZPASTERSKIE VALUES(nextval('ID_SEQ'), 'Torfowa 15', '82031310309', date '2013-12-30');