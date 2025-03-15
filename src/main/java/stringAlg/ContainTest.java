package stringAlg;

public class ContainTest {
    public static void main(String[] args) {
        System.out.println(isContain("ABDABC", "DAB"));
    }

    public static boolean isContain(String source, String pattern) {
        char[] sources = source.toCharArray();
        char[] patterns = pattern.toCharArray();
        // 主串索引
        int i = 0;
        // 模式串索引
        int j = 0;
        while(i < sources.length && j < patterns.length) {
            // 两个字符相等，比较下一个
            if(sources[i] == patterns[j]) {
                i++;
                j++;
            } else {
                // 不匹配时回溯到开始匹配的下一个索引
                i = i - j + 1;
                // 模式串从头开始
                j = 0;
            }
        }
        return j == patterns.length;
    }
}
