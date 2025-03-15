package arrayAndList;

class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
    }

    @Override public String toString() {
        return Integer.toString(val);
    }
}

public class LastKNode {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        System.out.println(findKNode(head, 4));
        System.out.println(findLastKNode(head, 1));
    }

    static ListNode findKNode(ListNode head, int k) {
        ListNode node = head;
        while(k > 1) {
            if(node != null)
                node = node.next;
            k--;
        }
        return node;
    }

    static ListNode findLastKNode(ListNode head, int k) {
        if(head == null || k < 0)
            return null;
        ListNode first = head, last = head;
        // 第一个指针先走 k 步
        while(k > 0) {
            if(first != null)
                first = first.next;
            k--;
        }
        // 两个指针一起走
        while(first != null) {
            first = first.next;
            last = last.next;
        }
        return last;
    }
}
