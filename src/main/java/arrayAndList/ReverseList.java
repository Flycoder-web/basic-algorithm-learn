package arrayAndList;

public class ReverseList {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode tail = reverse_clear(head);
        while(tail != null){
            System.out.println(tail);
            tail = tail.next;
        }
    }

    static ListNode reverse_obscure(ListNode head) {
        if(head == null)
            return null;
        ListNode last = null;
        ListNode temp = head.next;
        while (temp != null) {
            head.next = last;
            last = head;
            head = temp;
            temp = temp.next;
        }
        head.next = last;
        return head;
    }

    static ListNode reverse_clear(ListNode head) {
        if(head == null) return null;
        ListNode first = null; // 反转后链表的头节点
        while (head != null) {
            ListNode temp = head.next; // 保存下一个节点
            head.next = first; // 当前节点指向已反转部分
            first = head; // 更新已反转的头节点
            head = temp; // 继续处理剩余部分
        }
        return first;
    }
}
