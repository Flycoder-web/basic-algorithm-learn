package problem;

import java.util.HashMap;
import java.util.Map;

public class CharStatistics {
    public static String statistics_lf(String str, int L) {
        HashMap<String, Integer> countMap = new HashMap<>();
        int strLength = str.length();
        for(int i = 0; i + L <= strLength; i++) {
            int j = i + L;
            while(j < strLength) {
                String temp = str.substring(i, j);
                if(!countMap.containsKey(temp))
                    countMap.put(temp, 1);
                else
                    countMap.computeIfPresent(temp, (key, value)-> value + 1);
                j++;
            }
        }
        return countMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    public static String statistics_ref(String str, int L) {
        int length = str.length();
        Map<String, Integer> map = new HashMap<>();
        String temp = "";
        int max = 0;
        String maxStr = "";
        for(int i = 0; i + L <= length; i++) {
            for(int j = i + L; j < length; j++) {
                temp = str.substring(i, j);
                // 若map中已有该字符串
                if(map.containsKey(temp)) {
                    // 取出值
                    int value = map.get(temp);
                    value++;
                    if(value > max) {
                        max = value;
                        maxStr = temp;
                    } else if(value == max) {
                        if(temp.length() > maxStr.length()) {
                            maxStr = temp;
                        }
                    }
                    map.put(temp, value);
                } else {
                    // 否则加进去
                    map.put(temp, 0);
                }
            }
        }
        return maxStr;
    }

    public static void main(String[] args) {
        System.out.println(statistics_ref("bbaabbaaaaa", 2));
    }
}
