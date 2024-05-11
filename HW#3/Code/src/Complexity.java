/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class Complexity {
    private int nPower;
    private int logPower;

    /**
     * Class constructor.
     * @param nPower nPower as an <i>int</i>
     * @param logPower logPower as an <i>int</i>
     */
    public Complexity(int nPower, int logPower) {
        this.nPower = nPower;
        this.logPower = logPower;
    }

    /**
     * gets nPower variable.
     * @return <i>int</i> containing nPower.
     */
    public int getNPower() {
        return nPower;
    }

    /**
     * gets logPower variable.
     * @return <i>int</i> containing logPower.
     */
    public int getLogPower() {
        return logPower;
    }

    /**
     * set nPower variable.
     * @param nPower new <i>int</i> for nPower.
     */
    public void setNPower(int nPower) {
        this.nPower = nPower;
    }

    /**
     * set logPower variable.
     * @param logPower new <i>int</i> for logPower.
     */
    public void setLogPower(int logPower) {
        this.logPower = logPower;
    }

    /**
     * Creates human-readable Big-Oh notation in <i>String</i>.
     * @return a <i>String</i> of Big-Oh notation.
     */
    public String toString() {
        if (nPower == 0 && logPower == 00) {
            return "O(1)";
        } else if (nPower == 0) {
            if (logPower == 1) {
                return "O(log(n))";
            }
            return "O(log(n)^" + logPower + ")";
        } else if (logPower == 0) {
            if (nPower == 1) {
                return "O(n)";
            }
            return "O(n^" + nPower + ")";
        }

        String output = "O(";

        // Formulates the nPower part.
        if (nPower != 1) {
            if (nPower == 0) {
                output += "1 * ";
            } else {
                output += "n^" + nPower + " * ";
            }
        } else {
            output += "n * ";
        }

        // Formulates the logPower part.
        if (logPower != 1) {
            if (logPower == 0) {
                output += "1)";
            } else {
                output += "log(n)^" + logPower + ")";
            }
        } else {
            output += "log(n))";
        }

        return output;
    }
}
