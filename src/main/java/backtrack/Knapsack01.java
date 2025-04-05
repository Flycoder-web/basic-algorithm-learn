package backtrack;

import java.util.ArrayList;
import java.util.List;

class Item {
    int weight;
    int value;

    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
}

public class Knapsack01 {
    private static int maxValue = 0;

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(10, 32));
        items.add(new Item(5, 12));
        items.add(new Item(8, 4));
        items.add(new Item(3, 32));
        int capacity = 13;
        System.out.println(findMaxValue(items, capacity));
    }

    public static int findMaxValue(List<Item> items, int c) {
        maxValue = 0;
        //backtrack_origin(items, c, 0, 0, 0, new boolean[items.size()]);
        backtrack(items, c, 0, 0, 0);
        return maxValue;
    }

    public static void backtrack_origin(List<Item> items, int c, int sumValue, int sumWeight, int i, boolean[] visited) {
        for(; i < items.size(); i++) {
            Item item = items.get(i);
            if(!visited[i]) {
                int currentWeight = sumWeight + item.weight;
                int currentValue = sumValue + item.value;
                if(currentWeight <= c) {
                    if(currentValue > maxValue)
                        maxValue = currentValue;
                } else continue; // 注意，必须得continue跳过当前，不能直接return跳过所有
                visited[i] = true;
                backtrack_origin(items, c, currentValue, currentWeight, i + 1, visited);
                visited[i] = false;
            }
        }
    }

    // 优化后的回溯法
    public static void backtrack(List<Item> items, int c, int sumValue, int sumWeight, int i) {
        if(sumWeight > c) return; // 剪枝：重量超过背包容量，立即返回
        // 更新最大价值
        if(sumValue > maxValue)
            maxValue = sumValue;

        // 从当前索引 i 开始尝试选取物品
        for(int j = i; j < items.size(); j++) {
            Item item = items.get(j);
            backtrack(items, c, sumValue + item.value, sumWeight + item.weight, j + 1);
        }
    }
}
