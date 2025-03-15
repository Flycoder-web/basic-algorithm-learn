package arrayAndList;

public class MergeList {
    public static void main(String[] args) {
        // 1-->3-->4
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(3);
        head1.next.next = new ListNode(4);

        // 2-->6——>7
        ListNode head2 = new ListNode(2);
        head2.next = new ListNode(6);
        head2.next.next = new ListNode(7);

        ListNode res = merge(head1, head2);
        while(res != null){
            System.out.println(res);
            res = res.next;
        }
    }

    static ListNode merge(ListNode l1, ListNode l2) {
        // 创建-1头节点
        ListNode temp = new ListNode(-1);
        ListNode res = temp;
        while (l1 != null && l2 != null) {
            if(l1.val < l2.val) {
                temp.next = new ListNode(l1.val);
                l1 = l1.next;
            } else {
                temp.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            // 新链表指针后移
            temp = temp.next;
        }
        while(l1 != null) {
            temp.next = new ListNode(l1.val);
            l1 = l1.next;
            temp = temp.next;
        }
        while (l2 != null) {
            temp.next = new ListNode(l2.val);
            l2 = l2.next;
            temp = temp.next;
        }
        return res.next;
    }
}
