package db.utils;

import static db.utils.ColumnTypes.*;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class TableColumns {
    public static enum Common implements TableColumn {
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

    public static enum Parafianie implements TableColumn {
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

    public static enum Kaplani implements TableColumn {
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

    public static enum PomocnicyFunkcje implements TableColumn {
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

    public static enum Chrzty implements TableColumn {
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

    public static enum PierwszeKomunie implements TableColumn {
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

    public static enum Bierzmowania implements TableColumn {
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

    public static enum Sluby implements TableColumn {
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

    public static enum Pogrzeby implements TableColumn {
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

    public static enum WizytyDuszpasterskie implements TableColumn {
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

    public static enum IntencjeMszalne implements TableColumn {
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
    
    public static enum Zmarli implements TableColumn {
    	PESEL, IMIE, NAZWISKO, ADRES, DATA;

		@Override
		public char getType() {
			switch(this) {
				case PESEL:
				case IMIE:
				case NAZWISKO:
            	case ADRES:
            		return STRING;
            	case DATA:
            		return DATE;
			}
			return 0;
		}
    }

    public static enum AktywnosciKaplanow implements TableColumn {
    	ID, TYP, PESEL_KAPL, DATA;

		@Override
		public char getType() {
			switch(this) {
				case ID:
					return INTEGER;
				case PESEL_KAPL:
				case TYP:
					return STRING;
				case DATA:
					return DATE;
			}
			return 0;
		}
    }
    
    public static enum ChrztySzczegoly implements TableColumn {
    	ID, PESEL_DZIECKA, IMIE, DRUGIE_IMIE, NAZWISKO, PESEL_MATKI, PESEL_OJCA, PESEL_MATKI_CHRZ, PESEL_OJCA_CHRZ, PESEL_KAPL, DATA, OFIARA;

		@Override
		public char getType() {
			switch(this) {
				case ID:
				case OFIARA:
					return INTEGER;
				case PESEL_DZIECKA:
				case IMIE:
				case DRUGIE_IMIE:
				case NAZWISKO:
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
	
    public static enum BierzmowaniaSzczegoly implements TableColumn {
    	ID, PESEL, TRZECIE_IMIE, PESEL_SWIADKA, PESEL_KAPL, DATA;

		@Override
		public char getType() {
			switch(this) {
				case ID:
				case PESEL:
				case TRZECIE_IMIE:
				case PESEL_SWIADKA:
				case PESEL_KAPL:
					return STRING;
            	case DATA:
            		return DATE;

			}
			return 0;
		}
    }  
    
    public static enum Ofiary implements TableColumn {
    	OFIARA, TYP, DATA;

		@Override
		public char getType() {
			switch(this) {
				case OFIARA:
					return INTEGER;
				case TYP:
					return STRING;
            	case DATA:
            		return DATE;

			}
			return 0;
		}
    } 
    
    public static enum Pomocnicy implements TableColumn {
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
    
    public static enum Ministranci implements TableColumn {
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
    
    public static enum Lektorzy implements TableColumn {
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
    
    public static enum Szafarze implements TableColumn {
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
    
    public static interface TableColumn {
        public char getType();
    }
}
