package exercises.atm.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AtmCurrency {

    public static List<Integer[]> banknotesCounter(double toWithdraw) throws Exception {
        int[] values = new int[]{50, 100, 500, 1000, 10, 20, 50, 100};
        return solutions(values, amountOfCurrencies(), new int[8], toWithdraw, 0);
    }

    private static List<Integer[]> solutions(int[] values, int[] amounts, int[] variation, double toWithdraw, int position) {
        List<Integer[]> list = new ArrayList<>();
        int value = compute(values, variation);
        if (value < toWithdraw) {
            for (int i = position; i < values.length; i++) {
                if (amounts[i] > variation[i]) {
                    int[] newVariation = variation.clone();
                    newVariation[i]++;
                    List<Integer[]> newList = solutions(values, amounts, newVariation, toWithdraw, i);
                    list.addAll(newList);
                }
            }
        } else if (value == toWithdraw) {
            list.add(copy(variation));
        }
        return list;
    }

    private static int compute(int[] values, int[] variation) {
        int ret = 0;
        for (int i = 0; i < variation.length; i++) {
            ret += values[i] * variation[i];
        }
        return ret;
    }

    private static Integer[] copy(int[] ar) {
        Integer[] ret = new Integer[ar.length];
        for (int i = 0; i < ar.length; i++) {
            ret[i] = ar[i];
        }
        return ret;
    }

    private static int[] amountOfCurrencies() throws Exception {
        Connection con = Database.getConnection();
        int[] amount = new int[8];
        Statement stmt = con.createStatement();
        stmt.executeQuery("SELECT fifties, hundreds, five_hundreds, thousands, "
                + "dol_tens, dol_twenties, dol_fifties, dol_hundreds "
                + "FROM cash_machine WHERE id = 1");
        ResultSet rs = stmt.getResultSet();
        if (rs.next()) {
            amount[0] = rs.getInt(1);
            amount[1] = rs.getInt(2);
            amount[2] = rs.getInt(3);
            amount[3] = rs.getInt(4);
            amount[4] = rs.getInt(5);
            amount[5] = rs.getInt(6);
            amount[6] = rs.getInt(7);
            amount[7] = rs.getInt(8);
        }
        return amount;
    }
}
