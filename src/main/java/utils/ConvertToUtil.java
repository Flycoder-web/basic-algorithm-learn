package utils;

public interface ConvertToUtil {
    static int[] primitive(Integer[] in) {
        int[] result = new int[in.length];
        for(int i = 0; i < in.length; i++)
            result[i] = in[i];
        return result;
    }

    static Integer[] boxed(int[] in) {
        Integer[] result = new Integer[in.length];
        for(int i = 0; i < in.length; i++)
            result[i] = in[i];
        return result;
    }
}
