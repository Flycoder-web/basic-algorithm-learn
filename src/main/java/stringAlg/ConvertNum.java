package stringAlg;


public class ConvertNum {
    public static void main(String[] args) {
        int i = convert("");
        System.out.println(i);
    }

    public static int convert(String str) {
        if(str == null || str.isEmpty())
            return 0;
        int i = 0;
        while(i < str.length() && str.charAt(i) == ' ')
            i++;
        char symbol = '+';
        if(i < str.length() && str.charAt(i) == '-') {
            symbol = '-';
            i++;
        }
        if(i < str.length() && str.charAt(i) == '+') {
            i++;
        }
        int result = 0;
        while(i < str.length() && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            if(result > 214748364 || (result == 214748364 && str.charAt(i) > '6')){
                result = Integer.MAX_VALUE;
                break;
            }
            result = result * 10 + str.charAt(i) - '0';
            i++;
        }
        if(symbol == '+')
            return result;
        else
            return -result;
    }
}
