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
	drugie_imie varchar(100) DEFAULT NULL,
	trzecie_imie varchar(100) DEFAULT NULL,
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
	imie varchar(100) NOT NULL,
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
	adres varchar(500) NOT NULL,
	pesel_kapl char(11) CONSTRAINT fk_kapl REFERENCES kaplani(pesel),
	data date NOT NULL
);


--------------------------------------------------	PERSPEKTYWY	--------------------------------------------------


CREATE VIEW zmarli AS SELECT pesel, imie, nazwisko, adres, data FROM parafianie NATURAL JOIN pogrzeby WHERE zyje = 0;
CREATE VIEW aktywnosci_kaplanow AS
	SELECT id, 'CHRZEST' AS typ, pesel_kapl, data FROM chrzty UNION ALL
	SELECT id, 'KOMUNIA' AS typ, pesel_kapl, data FROM pierwsze_komunie UNION ALL
	SELECT id, 'BIERZMOWANIE' AS typ, pesel_kapl, data FROM bierzmowania UNION ALL
	SELECT id, 'SLUB' AS typ, pesel_kapl, data FROM sluby UNION ALL
	SELECT id, 'POGRZEB' AS typ, pesel_kapl, data FROM pogrzeby UNION ALL
	SELECT id, 'WIZYTA' AS typ, pesel_kapl, data FROM wizyty_duszpasterskie
	ORDER BY id;


--------------------------------------------------	TRIGGERS	--------------------------------------------------


CREATE OR REPLACE FUNCTION check_pesel() RETURNS trigger AS $check_pesel$
DECLARE
	chk_sum int;
BEGIN
	IF NEW.pesel IS NULL THEN
		RAISE EXCEPTION 'Trzeba wprowadzic pesel';
	END IF;

	chk_sum = 0;
	chk_sum = chk_sum + TO_NUMBER(substr(NEW.pesel,1,1),'9');
	chk_sum = chk_sum + TO_NUMBER(substr(NEW.pesel,2,1),'9')*3;
	chk_sum = chk_sum + TO_NUMBER(substr(NEW.pesel,3,1),'9')*7;
	chk_sum = chk_sum + TO_NUMBER(substr(NEW.pesel,4,1),'9')*9;
	chk_sum = chk_sum + TO_NUMBER(substr(NEW.pesel,5,1),'9');
	chk_sum = chk_sum + TO_NUMBER(substr(NEW.pesel,6,1),'9')*3;
	chk_sum = chk_sum + TO_NUMBER(substr(NEW.pesel,7,1),'9')*7;
	chk_sum = chk_sum + TO_NUMBER(substr(NEW.pesel,8,1),'9')*9;
	chk_sum = chk_sum + TO_NUMBER(substr(NEW.pesel,9,1),'9');
	chk_sum = chk_sum + TO_NUMBER(substr(NEW.pesel,10,1),'9')*3;
	chk_sum = chk_sum + TO_NUMBER(substr(NEW.pesel,11,1),'9');

	IF MOD(chk_sum,10) != 0 THEN
		RAISE EXCEPTION 'Zly pesel';
	END IF;

	RETURN NEW;
END;
$check_pesel$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION handle_chrzest() RETURNS trigger AS $handle_chrzest$
DECLARE
	nazw parafianie.nazwisko%TYPE;
BEGIN
	IF NEW.pesel IN (SELECT pesel FROM parafianie) THEN
		RETURN NEW;
	END IF;

	IF NEW.pesel_ojca IN (SELECT pesel FROM parafianie) THEN
		SELECT nazwisko, adres INTO nazw, adr FROM parafianie WHERE pesel = NEW.pesel_ojca;
	ELSIF NEW.pesel_matki IN (SELECT pesel FROM parafianie) THEN
		SELECT nazwisko, adres INTO nazw, adr FROM parafianie WHERE pesel = NEW.pesel_matki;
	ELSE
		RAISE EXCEPTION 'Nie znam nazwiska dziecka';
	END IF;

	INSERT INTO parafianie (pesel, imie, drugie_imie, nazwisko, adres) VALUES (NEW.pesel, NEW.imie, NEW.drugie_imie, nazw, adr);

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

CREATE TRIGGER check_pesel_para BEFORE INSERT ON parafianie
FOR EACH ROW EXECUTE PROCEDURE check_pesel();
CREATE TRIGGER check_pesel_kapl BEFORE INSERT ON kaplani
FOR EACH ROW EXECUTE PROCEDURE check_pesel();

CREATE TRIGGER handle_chrzest BEFORE INSERT ON chrzty
FOR EACH ROW EXECUTE PROCEDURE handle_chrzest();

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


--parafianie
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('12345678910', 'Adam', 'Adamski', 'Torfowa 15');
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('12345678911', 'Ada', 'Adamska', 'Torfowa 15');
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('12345678912', 'Zbigniew', 'Adamski', 'Torfowa 15');
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('12345678913', 'Zofia', 'Kowalska', 'Torfowa 16');
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('12345678914', 'Jan', 'Kowalski', 'Torfowa 16');
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('12345678915', 'Maciej', 'Kowalski', 'Torfowa 16');
INSERT INTO PARAFIANIE(pesel, imie, nazwisko, adres) VALUES('12345678916', 'Piotr', 'Kowalski', 'Torfowa 16');

--kaplani
INSERT INTO KAPLANI VALUES('22222222222', 'Andrzej', 'Pezarski', 'wikary');
INSERT INTO KAPLANI VALUES('22222222223', 'Grzegorz', 'Matecki', 'proboszcz');

--pomocnicy
INSERT INTO POMOCNICY VALUES('12345678914', 'szafarz');
INSERT INTO POMOCNICY VALUES('12345678915', 'lektor');
INSERT INTO POMOCNICY VALUES('12345678916', 'ministrant');

--Ada i Adam biora slub
INSERT INTO SLUBY VALUES(nextval('ID_SEQ'), '12345678910', '12345678911', '22222222223', date '2001-10-05');

--Zbigniew umiera
INSERT INTO POGRZEBY VALUES(nextval('ID_SEQ'), '12345678910', '22222222222', date '2001-10-06');

--Adam i Ada chrzcza dziecko
INSERT INTO CHRZTY VALUES(nextval('ID_SEQ'), '12345678920', 'Ida', 'Anna', '12345678911', '12345678910','12345678913', '12345678914', '22222222223', date '2005-10-05');

--Piotr - komunia
INSERT INTO PIERWSZE_KOMUNIE VALUES(nextval('ID_SEQ'), '12345678916', '22222222223', date '2005-10-05');

--Maciej - bierzmowanie
INSERT INTO BIERZMOWANIA VALUES(nextval('ID_SEQ'), '12345678915', 'Igor', '12345678911', '22222222223', date '2005-12-05');

--wizyty duszpasterskie
INSERT INTO WIZYTY_DUSZPASTERSKIE VALUES(nextval('ID_SEQ'), 'Torfowa 16', '22222222223', date '2013-12-30');
INSERT INTO WIZYTY_DUSZPASTERSKIE VALUES(nextval('ID_SEQ'), 'Torfowa 15', '22222222222', date '2013-12-30');