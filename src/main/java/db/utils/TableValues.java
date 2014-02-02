package db.utils;

/**
 * @author Michał Piekarz
 */
public class TableValues
{
    public static enum Parafianie implements TableValue
    {
        PESEL, IMIE, DRUGIE_IMIE, TRZECIE_IMIE, NAZWISKO, ADRES, FUNKCJA_POMOCNIKA, ZYJE
    }

    public static enum Kaplani implements TableValue
    {
        PESEL, IMIE, NAZWISKO, FUNKCJA
    }

    public static enum PomocnicyFunkcje implements TableValue
    {
        NAZWA
    }

    public static enum Chrzty implements TableValue
    {
        ID, PESEL_DZIECKA, PESEL_MATKI, PESEL_OJCA, PESEL_MATKI_CHRZ, PESEL_OJCA_CHRZ, PESEL_KAPL, OFIARA, DATA
    }

    public static enum PierwszeKomunie implements TableValue
    {
        ID, PESEL, PESEL_KAPL, DATA
    }

    public static enum Bierzmowania implements TableValue
    {
        ID, PESEL, PESEL_SWIADKA, PESEL_KAPL, DATA
    }

    public static enum Sluby implements TableValue
    {
        ID, PESEL_ZONY, PESEL_MEZA, PESEL_SWIADKA_ZONY, PESEL_SWIADKA_MEZA, PESEL_KAPL, OFIARA, DATA
    }

    public static enum Pogrzeby implements TableValue
    {
        ID, PESEL, PESEL_KAPL, OFIARA, DATA
    }

    public static enum WizytyDuszpasterskie implements TableValue
    {
        ID, ADRES, PESEL_KAPL, OFIARA, DATA
    }

    public static enum IntencjeMszalne implements TableValue
    {
        ID, OPIS, PESEL_KAPL, OFIARA, DATA
    }

    public static interface TableValue
    {
    }
}
