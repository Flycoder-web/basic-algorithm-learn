package problem;

import utils.TimerUtil;

import java.io.File;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class IdNumberSort {
    public static void main(String[] args) throws IOException{
        //Scanner scanner = new Scanner(System.in);
        TimerUtil timer = new TimerUtil();
        Scanner scanner = new Scanner(new File("randomIds.txt"));
        int n = Integer.parseInt(scanner.nextLine());
        List<String> idCards = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            idCards.add(scanner.nextLine());
        }

        idCards.sort((id1, id2) -> {
            String birthDate1 = id1.substring(6, 14);
            String birthDate2 = id2.substring(6, 14);
            // 先按出生日期从大到小排序
            int cmp = birthDate2.compareTo(birthDate1);
            if (cmp != 0) {
                return cmp;
            }
            // 如果出生日期相同，则按身份证号码从小到大排序
            return id1.compareTo(id2);
        });

        for (String id : idCards) {
            System.out.println(id);
        }

        timer.end();
    }
}

class IDGenerator {

    public static void main(String[] args) {
        int n = 100000; // 生成身份证数量
        String outputFile = "randomIds.txt"; // 输出文件路径（可以改成 null 输出到控制台）

        generateIDs(n, outputFile);
    }

    public static void generateIDs(int n, String outputFile) {
        Random rand = new Random();
        String[] areaCodes = {"110105", "320101", "440103", "510104", "350102"}; // 常见地区码
        try {
            FileWriter writer = (outputFile != null) ? new FileWriter(outputFile) : null;

            if(writer != null)
                writer.write(n + "\n");
            else
                System.out.println(n);

            for (int i = 0; i < n; i++) {
                String areaCode = areaCodes[rand.nextInt(areaCodes.length)]; // 随机地区码
                String birthDate = randomBirthDate(rand); // 生成合法出生日期
                String seqCode = String.format("%03d", rand.nextInt(1000)); // 3 位顺序码
                char checkDigit = generateCheckDigit(areaCode + birthDate + seqCode); // 计算校验码

                String idCard = areaCode + birthDate + seqCode + checkDigit;

                if (writer != null) {
                    writer.write(idCard + "\n");
                } else {
                    System.out.println(idCard);
                }
            }

            if (writer != null) {
                writer.close();
                System.out.println("身份证号码已保存至: " + outputFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 生成随机出生日期（1980-01-01 ~ 2022-12-31）
    public static String randomBirthDate(Random rand) {
        int year = rand.nextInt(43) + 1980; // 1980 ~ 2022
        int month = rand.nextInt(12) + 1;
        int day;

        // 确保日期合法
        if (month == 2) {
            day = rand.nextInt(28) + 1; // 简化：不考虑闰年
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = rand.nextInt(30) + 1;
        } else {
            day = rand.nextInt(31) + 1;
        }

        return String.format("%04d%02d%02d", year, month, day);
    }

    // 计算身份证的最后一位校验码
    public static char generateCheckDigit(String id17) {
        int[] weight = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] checkCode = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int sum = 0;

        for (int i = 0; i < 17; i++) {
            sum += (id17.charAt(i) - '0') * weight[i];
        }

        return checkCode[sum % 11];
    }
}

