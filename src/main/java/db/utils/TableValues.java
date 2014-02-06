package db.utils;

import static db.utils.ColumnTypes.*;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class TableValues {
    public static enum Common implements TableValue {
        ALL;

        @Override
        public char getType() {
            return 0;
        }

        @Override
        public String toString() {
            return "*";
        }
    }

    public static enum Parafianie implements TableValue {
        PESEL, IMIE, DRUGIE_IMIE, TRZECIE_IMIE, NAZWISKO, ADRES, FUNKCJA_POMOCNIKA, ZYJE;

        @Override
        public char getType() {
            switch (this) {
                case PESEL:
                case IMIE:
                case DRUGIE_IMIE:
                case TRZECIE_IMIE:
                case NAZWISKO:
                case ADRES:
                case FUNKCJA_POMOCNIKA:
                    return STRING;
                case ZYJE:
                    return BOOLEAN;
            }

            return 0;
        }
    }

    public static enum Kaplani implements TableValue {
        PESEL, IMIE, NAZWISKO, FUNKCJA;

        @Override
        public char getType() {
            switch (this) {
                case PESEL:
                case IMIE:
                case NAZWISKO:
                case FUNKCJA:
                    return STRING;
            }

            return 0;
        }
    }

    public static enum PomocnicyFunkcje implements TableValue {
        NAZWA;

        @Override
        public char getType() {
            switch (this) {
                case NAZWA:
                    return STRING;
            }

            return 0;
        }
    }

    public static enum Chrzty implements TableValue {
        ID, PESEL_DZIECKA, PESEL_MATKI, PESEL_OJCA, PESEL_MATKI_CHRZ, PESEL_OJCA_CHRZ, PESEL_KAPL, OFIARA, DATA;

        @Override
        public char getType() {
            switch (this) {
                case ID:
                case OFIARA:
                    return INTEGER;
                case PESEL_DZIECKA:
                case PESEL_MATKI:
                case PESEL_OJCA:
                case PESEL_MATKI_CHRZ:
                case PESEL_OJCA_CHRZ:
                case PESEL_KAPL:
                    return STRING;
                case DATA:
                    return DATE;
            }

            return 0;
        }
    }

    public static enum PierwszeKomunie implements TableValue {
        ID, PESEL, PESEL_KAPL, DATA;

        @Override
        public char getType() {
            switch (this) {
                case ID:
                    return INTEGER;
                case PESEL:
                case PESEL_KAPL:
                    return STRING;
                case DATA:
                    return DATE;
            }

            return 0;
        }
    }

    public static enum Bierzmowania implements TableValue {
        ID, PESEL, PESEL_SWIADKA, PESEL_KAPL, DATA;

        @Override
        public char getType() {
            switch (this) {
                case ID:
                    return INTEGER;
                case PESEL:
                case PESEL_SWIADKA:
                case PESEL_KAPL:
                    return STRING;
                case DATA:
                    return DATE;
            }

            return 0;
        }
    }

    public static enum Sluby implements TableValue {
        ID, PESEL_ZONY, PESEL_MEZA, PESEL_SWIADKA_ZONY, PESEL_SWIADKA_MEZA, PESEL_KAPL, OFIARA, DATA;

        @Override
        public char getType() {
            switch (this) {
                case ID:
                case OFIARA:
                    return INTEGER;
                case PESEL_ZONY:
                case PESEL_MEZA:
                case PESEL_SWIADKA_ZONY:
                case PESEL_SWIADKA_MEZA:
                case PESEL_KAPL:
                    return STRING;
                case DATA:
                    return DATE;
            }

            return 0;
        }
    }

    public static enum Pogrzeby implements TableValue {
        ID, PESEL, PESEL_KAPL, OFIARA, DATA;

        @Override
        public char getType() {
            switch (this) {
                case ID:
                case OFIARA:
                    return INTEGER;
                case PESEL:
                case PESEL_KAPL:
                    return STRING;
                case DATA:
                    return DATE;
            }

            return 0;
        }
    }

    public static enum WizytyDuszpasterskie implements TableValue {
        ID, ADRES, PESEL_KAPL, OFIARA, DATA;

        @Override
        public char getType() {
            switch (this) {
                case ID:
                case OFIARA:
                    return INTEGER;
                case ADRES:
                case PESEL_KAPL:
                    return STRING;
                case DATA:
                    return DATE;
            }

            return 0;
        }
    }

    public static enum IntencjeMszalne implements TableValue {
        ID, OPIS, PESEL_KAPL, OFIARA, DATA;

        @Override
        public char getType() {
            switch (this) {
                case ID:
                case OFIARA:
                    return INTEGER;
                case OPIS:
                case PESEL_KAPL:
                    return STRING;
                case DATA:
                    return DATE;
            }

            return 0;
        }
    }

    public static interface TableValue {
        public char getType();
    }
}
