package arrayAndList;

import java.util.HashSet;

public class CycleLink {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = head.next;
        System.out.println(isContainCycle_DualPointer(head));
        System.out.println(findCycleStartNode(head));
        System.out.println(findCycleSize(head));
    }

    static boolean isContainCycle_naive(ListNode head) {
        HashSet<ListNode> set = new HashSet<>();
        while(head != null) {
            if(!set.add(head))
                return true;
            head = head.next;
        }
        return false;
    }

    static boolean isContainCycle_DualPointer(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        while(slow != fast) {
            if(fast == null || fast.next == null)
                return false;
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    static ListNode findCycleStartNode(ListNode head) {
        if(head == null || head.next == null)
            return null;
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        // 先找到相遇节点
        while (slow != fast) {
            if(fast == null || fast.next == null)
                return null;
            slow = slow.next;
            fast = fast.next.next;
        }
        // 让一个指针回到表头，两个指针相同速度走
        // 下一个相遇节点就是环的入口
        fast = head;
        while(fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    static int findCycleSize(ListNode head) {
        if(head == null || head.next == null)
            return 0;
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while (slow != fast) {
            if(fast == null || fast.next == null)
                return 0;
            slow = slow.next;
            fast = fast.next.next;
        }
        int count = 0;
        do {
            fast = fast.next;
            count++;
        } while (slow != fast);
        return count;
    }
}
