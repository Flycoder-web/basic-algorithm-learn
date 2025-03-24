package stringAlg;

public class LongestPalindrome {
    public static void main(String[] args) {
        String s = "abdbdc";
        System.out.println(find_simple(s));
        System.out.println(find_spread(s));
        System.out.println(find(s));
    }

    public static boolean isMatch(String s) {
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != s.charAt(s.length() - 1 - i))
                return false;
        }
        return true;
    }

    // 三重循环的简单解法
    public static String find_simple(String str) {
        String result = "";
        int max = 0;
        int length = str.length();
        for(int i = 0; i < length; i++) {
            for(int j = i + 1; j <= length; j++) {
                String subStr = str.substring(i, j); // 注意不包含endIndex
                if(max < subStr.length() && isMatch(subStr)) {
                    // 记录回文串的最大长度
                    max = subStr.length();
                    result = subStr;
                }
            }
        }
        return result;
    }

    // 由中心向两边扩展的方法
    public static String find_spread(String str) {
        if(str == null || str.length() < 2)
            return str;
        int max = 1;
        String result = str.substring(0, 1);
        for(int i = 0; i < str.length() - 1; i++) {
            // 中心为 i 的奇数个回文串
            String odd = spread(str, i, i);
            // 中心为 i 和 i+1 的偶数个回文串
            String even = spread(str, i, i + 1);
            // 选择两者最长的
            String maxStr = odd.length() > even.length() ? odd : even;
            if(max < maxStr.length()) {
                // 更新最长回文串
                max = maxStr.length();
                result = maxStr;
            }
        }
        return result;
    }

    public static String spread(String str, int left, int right) {
        int len = str.length();
        while(left >= 0 && right < len) {
            if(str.charAt(left) == str.charAt(right)) {
                left--;
                right++;
            } else {
                break;
            }
        }
        return str.substring(left + 1, right);
    }

    // TODO 笔记待补充
    // 优化的中心扩展
    public static String find(String s) {
        if(s == null || s.length() < 2)
            return s;
        int start = 0, end = 1;
        for(int i = 0; i < s.length(); i++) {
            int odd = spread_optimize(s, i, i);
            int even = spread_optimize(s, i, i + 1);
            int longer = Math.max(odd, even);
            if(end - start + 1 < longer) {
                start = i - (longer - 1) / 2;
                end = i + longer / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private static int spread_optimize(String str, int left, int right) {
        int n = str.length();
        while(left >= 0 && right < n) {
            if(str.charAt(left) == str.charAt(right)) {
                left--;
                right++;
            } else {
                break;
            }
        }
        // 返回当前扩展后的回文长度
        return right - left - 1;
    }
}