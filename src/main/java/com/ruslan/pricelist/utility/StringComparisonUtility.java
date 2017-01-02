package com.ruslan.pricelist.utility;

/**
 * Created by RUSLAN on 03.05.2016.
 */
public class StringComparisonUtility {

    public static int levenshteinDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    public static boolean isItemsSame(String nomenclatureName, String itemName) {
        //TODO make difference coefficient configurable
        Double diffCoefficient = 10.0;
        int levenchteinDistance = levenshteinDistance(nomenclatureName, itemName);
        double difference = 0.0;
        if (itemName.length() >= levenchteinDistance) {
            difference = 100 * levenchteinDistance / nomenclatureName.length();
        } else {
            difference = 100 * levenchteinDistance / itemName.length();
        }
        if (difference <= diffCoefficient)
            return true;

        return false;
    }
}