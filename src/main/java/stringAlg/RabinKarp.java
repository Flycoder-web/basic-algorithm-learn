package stringAlg;

public class RabinKarp {
    public static boolean isMatch(String source, String pattern) {
        // 计算模式串的 hash
        long Phash = hash(pattern);
        int Plen = pattern.length();
        for(int i = 0; i + Plen <= source.length(); i++) {
            // 计算子串的 hash 值
            long tempHash = hash(source.substring(i, i + Plen));
            if(tempHash == Phash)
                return true;
        }
        return false;
    }

    public static long hash(String str) {
        long result = 0;
        for(int i = 0; i < str.length(); i++) {
            // 错将 = 写成 +=，导致滚动哈希时计算的tempHash越来越大
            result = 31 * result + str.charAt(i);
        }
        return result;
    }

    public static boolean isMatchRollHash(String source, String pattern) {
        long Phash = hash(pattern);
        int Plen = pattern.length();
        int Slen = source.length();

        long base = 1;
        for (int i = 1; i < Plen; i++) {
            base *= 31; // 计算 31^(Plen-1)
        }

        long tempHash = 0;
        if(Plen <= Slen) {
            tempHash = hash(source.substring(0, Plen));
        }
        for(int i = 0; i + Plen <= Slen; i++) {
            if(tempHash == Phash)
                return true;
            if(i + Plen < Slen)
                tempHash = (tempHash - source.charAt(i) * base) * 31 + source.charAt(i + Plen);
        }
        return false;
    }

    public static boolean isMatchGPT(String source, String pattern) {
        int Plen = pattern.length();
        int Slen = source.length();
        if (Plen > Slen) return false;

        long Phash = hash(pattern);
        long Shash = hash(source.substring(0, Plen)); // 计算文本初始窗口哈希

        long base = 1;
        for (int i = 1; i < Plen; i++) {
            base *= 31; // 计算 31^(Plen-1)
        }

        for (int i = 0; i <= Slen - Plen; i++) {
            if (Phash == Shash) { // 哈希匹配，可能是答案
                if (source.substring(i, i + Plen).equals(pattern)) {
                    return true;
                }
            }
            // 计算下一个窗口的哈希值
            if (i < Slen - Plen) {
                long temp4 = source.charAt(i);
                long temp5 = temp4 * base;
                long temp1 = Shash - temp5;
                long temp2 = temp1 * 31;
                long temp3 = source.charAt(i + Plen);
                Shash = temp2 + temp3;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isMatchRollHash("ABABCD", "BCD"));
    }
}
