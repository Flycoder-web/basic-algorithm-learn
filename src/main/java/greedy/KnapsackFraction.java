package greedy;

import java.util.ArrayList;
import java.util.List;

class Item {
    final int weight;
    final int value;
    final double ratio;
    Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
        ratio = (double) value / weight;
    }
    @Override
    public String toString() {
        return "(" + weight + ", " + value + ", " + ratio + ")";
    }
}

public class KnapsackFraction {
    public static void main(String[] args) {
        int[] weights = {10, 8, 5, 3, 4, 7, 3};
        int[] values = {2, 4, 12, 32, 33, 54, 6};
        int total = 15;
        double maxValue = greedy(weights, values, total);
        System.out.println(maxValue);
    }

    public static double greedy(int[] weights, int[] values, int W) {
        List<Item> items = new ArrayList<>();
        for(int i = 0; i < weights.length; i++) {
            items.add(new Item(weights[i], values[i]));
        }
        items.sort((i1, i2) -> (int)((i2.ratio - i1.ratio) * 100));
        double maxValue = 0;
        for (Item current : items) {
            if (W >= current.weight) {
                // 如果当前物品能完整放入
                W -= current.weight;
                maxValue += current.value;
            } else {
                // 放入部分物品，背包已满，直接退出
                maxValue += W * current.ratio;
                break;
            }
        }
        return maxValue;
    }
}
