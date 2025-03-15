package stringAlg;

public class ReplaceSpaceSolution {

    public static void main(String[] args) {
        System.out.println(replaceSpace("Hello, World!"));
    }

    public static String replaceSpace(String str) {
        // 转换为字符数组
        char[] origin = str.toCharArray();
        int spaceNum = 0;
        // 计算空格个数
        for(char c : origin)
            if(c == ' ')
                spaceNum++;
        // 创建新字符数组
        char[] result = new char[origin.length + spaceNum * 3];
        int index = 0;
        for(char c : origin) {
            if(c == ' ') {
                // 空格需要复制几个
                result[index++] = 'c';
                result[index++] = 'o';
                result[index++] = 'd';
                result[index++] = 'e';
            } else {
                // 直接复制
                result[index++] = c;
            }
        }
        // 转换为字符串
        return new String(result);
    }
}
