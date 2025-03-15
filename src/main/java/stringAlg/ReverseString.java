package stringAlg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class ReverseString {
    public static void main(String[] args) {
        String str = "  this is   coding ";
        String[] splits = str.split(" ");
        System.out.println(Arrays.toString(splits));
        splits = splitUseStringTokenizer(str);
        System.out.println(Arrays.toString(splits));
        splits = split(str);
        System.out.println(Arrays.toString(splits));
        String s = reverse(str);
        System.out.println(s);
    }

    public static String reverse(String s) {
        // 按空格分隔
        String[] splits = split(s);
        int size = splits.length;
        // 根据轴对称进行交换
        for(int i = 0; i < size / 2; i++) {
            String temp = splits[i];
            splits[i] = splits[size - 1 - i];
            splits[size - 1 - i] = temp;
        }
        // 拼接字串
        StringBuilder sb = new StringBuilder();
        for(String str : splits) {
            // 去掉空字串
            if(!"".equals(str))
                sb.append(str).append(" ");
        }
        if(!sb.isEmpty())
            return sb.toString();
        return "";
    }

    // StringTokenizer 可以根据指定的分隔符来分割字符串，而且默认就是按空格分割的
    // 但遇到连续空格不会像split()那样，生成空字符串
    public static String[] splitUseStringTokenizer(String str) {
        List<String> result = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while(st.hasMoreElements())
            result.add(st.nextToken());
        return result.toArray(new String[0]);
    }

    public static String[] split(String str) {
        if(str == null || str.isEmpty())
            return new String[0];
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == ' ') {
                result.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(str.charAt(i));
            }
        }
        if(!sb.isEmpty())
            result.add(sb.toString());
        return result.toArray(new String[0]);
    }
}
