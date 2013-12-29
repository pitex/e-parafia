DROP SEQUENCE IF EXISTS ID_SEQ;

DROP VIEW IF EXISTS aktywnosci_kaplanow;
DROP VIEW IF EXISTS zmarli;
DROP VIEW IF EXISTS chrzty_szczegoly;
DROP VIEW IF EXISTS bierzmowania_szczegoly;

DROP TABLE IF EXISTS wizyty_duszpasterskie;
DROP TABLE IF EXISTS pogrzeby;
DROP TABLE IF EXISTS sluby;
DROP TABLE IF EXISTS bierzmowania;
DROP TABLE IF EXISTS pierwsze_komunie;
DROP TABLE IF EXISTS chrzty;
DROP TABLE IF EXISTS pomocnicy;
DROP TABLE IF EXISTS kaplani;
DROP TABLE IF EXISTS parafianie;

DROP FUNCTION IF EXISTS check_pesel();
DROP FUNCTION IF EXISTS handle_chrzest();
DROP FUNCTION IF EXISTS handle_pogrzeb();
DROP FUNCTION IF EXISTS give_id();
DROP FUNCTION IF EXISTS handle_slub();
DROP FUNCTION IF EXISTS handle_bierzmowanie();
DROP FUNCTION IF EXISTS count_pesel_checksum(pesel char(11));