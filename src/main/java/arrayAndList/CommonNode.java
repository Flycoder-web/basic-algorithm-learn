package arrayAndList;

import java.util.HashSet;

public class CommonNode {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(3);
        ListNode l2 = new ListNode(11);
        l2.next = new ListNode(22);
        // 公共节点
        l2.next.next = new ListNode(4);
        l1.next.next.next = l2.next.next;
        l2.next.next.next = new ListNode(5);

        System.out.println(findFirstCommonNode_hashset(l1, l2));
    }

    static ListNode findFirstCommonNode_hashset(ListNode l1, ListNode l2) {
        HashSet<ListNode> set = new HashSet<>();
        // 先把链表1的节点添加进去
        while (l1 != null) {
            set.add(l1);
            l1 = l1.next;
        }
        // 再遍历第二个链表
        while(l2 != null) {
            if(!set.add(l2))
                return l2;
            l2 = l2.next;
        }
        return null;
    }

    // 两指针步数相同，要么同时到达公共点，要么同时为 null
    static ListNode findFirstCommonNode_DualPointer(ListNode l1, ListNode l2) {
        ListNode p1 = l1, p2 = l2;
        while (p1 != p2){
            p1 = (p1 == null) ? l2 : p1.next;
            p2 = (p2 == null) ? l1 : p2.next;
        }
        return p1;
    }
}
