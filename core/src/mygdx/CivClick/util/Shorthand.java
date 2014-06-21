package mygdx.CivClick.util;
/**
 *
 * @author Whiplash
 */
public class Shorthand {

    /**
     * Parses a double value into a string with a suffix replacing the trailing
     * end of the number. E.G 1,000,000 (1 million) would be 1.000Mil.
     *
     * @param number
     * @return the shorthand version of the double value.
     */
    public static String parse(double number) {
        String tmp = String.valueOf((long) number);
        char[] charar = tmp.toCharArray();
        char[] newcharar = new char[Math.min(charar.length, 4)];
        for (int i = 0; i < charar.length & i < 4; i++) {
            newcharar[i] = charar[i];
        }
        switch (charar.length) {
            case 0:
                throw new IllegalArgumentException("Invalid array.");

            case 1: // Ones.
                return new String(newcharar);

            case 2: // Tens.
                return new String(newcharar);

            case 3: // Hundreds.
                return new String(newcharar);

            case 4: // Thousands.
                StringBuilder thou = new StringBuilder();
                thou.append(newcharar[0]);
                thou.append(".");
                thou.append(newcharar[1]);
                thou.append(newcharar[2]);
                thou.append(newcharar[3]);
                thou.append("Th");
                return thou.toString();

            case 5: // Ten Thousands.
                StringBuilder tthou = new StringBuilder();
                tthou.append(newcharar[0]);
                tthou.append(newcharar[1]);
                tthou.append(".");
                tthou.append(newcharar[2]);
                tthou.append(newcharar[3]);
                tthou.append("Th");
                return tthou.toString();

            case 6: // Hundred Thousands.
                StringBuilder hthou = new StringBuilder();
                hthou.append(newcharar[0]);
                hthou.append(newcharar[1]);
                hthou.append(newcharar[2]);
                hthou.append(".");
                hthou.append(newcharar[3]);
                hthou.append("Th");
                return hthou.toString();

            case 7: // Millions.
                StringBuilder mil = new StringBuilder();
                mil.append(newcharar[0]);
                mil.append(".");
                mil.append(newcharar[1]);
                mil.append(newcharar[2]);
                mil.append(newcharar[3]);
                mil.append("Mil");
                return mil.toString();

            case 8: // Ten Millions.
                StringBuilder tmil = new StringBuilder();
                tmil.append(newcharar[0]);
                tmil.append(newcharar[1]);
                tmil.append(".");
                tmil.append(newcharar[2]);
                tmil.append(newcharar[3]);
                tmil.append("Mil");
                return tmil.toString();

            case 9: // Hundred Millions.
                StringBuilder hmil = new StringBuilder();
                hmil.append(newcharar[0]);
                hmil.append(newcharar[1]);
                hmil.append(newcharar[2]);
                hmil.append(".");
                hmil.append(newcharar[3]);
                hmil.append("Mil");
                return hmil.toString();

            case 10: // Billions.
                StringBuilder bil = new StringBuilder();
                bil.append(newcharar[0]);
                bil.append(".");
                bil.append(newcharar[1]);
                bil.append(newcharar[2]);
                bil.append(newcharar[3]);
                bil.append("Bil");
                return bil.toString();

            case 11: // Ten Billions.                
                StringBuilder tbil = new StringBuilder();
                tbil.append(newcharar[0]);
                tbil.append(newcharar[1]);
                tbil.append(".");
                tbil.append(newcharar[2]);
                tbil.append(newcharar[3]);
                tbil.append("Bil");
                return tbil.toString();

            case 12: // Hundred Billions.
                StringBuilder hbil = new StringBuilder();
                hbil.append(newcharar[0]);
                hbil.append(newcharar[1]);
                hbil.append(newcharar[2]);
                hbil.append(".");
                hbil.append(newcharar[3]);
                hbil.append("Bil");
                return hbil.toString();

            case 13: // Trillions.
                StringBuilder tril = new StringBuilder();
                tril.append(newcharar[0]);
                tril.append(".");
                tril.append(newcharar[1]);
                tril.append(newcharar[2]);
                tril.append(newcharar[3]);
                tril.append("Tril");
                return tril.toString();

            case 14: // Ten Trillions.
                StringBuilder ttril = new StringBuilder();
                ttril.append(newcharar[0]);
                ttril.append(newcharar[1]);
                ttril.append(".");
                ttril.append(newcharar[2]);
                ttril.append(newcharar[3]);
                ttril.append("Tril");
                return ttril.toString();

            case 15: // Hundred Trillions.
                StringBuilder htril = new StringBuilder();
                htril.append(newcharar[0]);
                htril.append(newcharar[1]);
                htril.append(newcharar[2]);
                htril.append(".");
                htril.append(newcharar[3]);
                htril.append("Tril");
                return htril.toString();

            case 16: // Quadrillions.
                StringBuilder quad = new StringBuilder();
                quad.append(newcharar[0]);
                quad.append(".");
                quad.append(newcharar[1]);
                quad.append(newcharar[2]);
                quad.append(newcharar[3]);
                quad.append("Quad");
                return quad.toString();

            case 17: // Ten Quadrillions.
                StringBuilder tquad = new StringBuilder();
                tquad.append(newcharar[0]);
                tquad.append(newcharar[1]);
                tquad.append(".");
                tquad.append(newcharar[2]);
                tquad.append(newcharar[3]);
                tquad.append("Quad");
                return tquad.toString();

            case 18: // Hundred Quadrillions.
                StringBuilder hquad = new StringBuilder();
                hquad.append(newcharar[0]);
                hquad.append(newcharar[1]);
                hquad.append(newcharar[2]);
                hquad.append(".");
                hquad.append(newcharar[3]);
                hquad.append("Quad");
                return hquad.toString();

            case 19: // Quintillion.
                StringBuilder quint = new StringBuilder();
                quint.append(newcharar[0]);
                quint.append(".");
                quint.append(newcharar[1]);
                quint.append(newcharar[2]);
                quint.append(newcharar[3]);
                quint.append("Quint");
                return quint.toString();
        }
        throw new IllegalArgumentException("Invalid array.");
    }
}
