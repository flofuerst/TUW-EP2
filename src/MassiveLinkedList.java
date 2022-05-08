// A list of massive objects implemented as a linked list.
// The number of elements of the list is not limited.
class MyMassiveListNode {
    private Massive m;
    private MyMassiveListNode next;
    private MyMassiveListNode prev;

    MyMassiveListNode(Massive m, MyMassiveListNode prev, MyMassiveListNode next) {
        this.m = m;
        this.prev = prev;
        this.next = next;
    }

    Massive getBody() {
        return m;
    }

    //Precondition: inner index
    void add(Massive m, int i) {
        if (i == 0) {
            //insert
            MyMassiveListNode newNode = new MyMassiveListNode(m, prev, this);
            prev.next = newNode;
        } else {
            next.add(m,i - 1);
        }
    }

    // Precondition: 'i' is a valid index.
    Massive get(int i) {
        if (i == 0) {
            return m;
        } else {
            return next.get(i - 1);
        }
    }

    int indexOf(Massive m, int i) {
        if (this.m.equals(m)) {
            return i;
        }
        if (next == null) {
            return -1;
        }
        return next.indexOf(m, i + 1);
    }

    int size() {
        if (next == null) {
            return 1;
        }
        return 1 + next.size();
    }

    public MyMassiveListNode getNext() {
        return next;
    }

    public MyMassiveListNode getPrev() {
        return prev;
    }

    void setNext(MyMassiveListNode node) {
        next = node;
    }

    void setPrev(MyMassiveListNode node) {
        prev = node;
    }
}

public class MassiveLinkedList{

    private MyMassiveListNode head;
    private MyMassiveListNode tail;

    // Initializes 'this' as an empty list.
    public MassiveLinkedList() {
    }

    // Initializes 'this' as an independent copy of the specified list 'list'.
    // Calling methods of this list will not affect the specified list 'list'
    // and vice versa.
    // Precondition: list != null.
    public MassiveLinkedList(MassiveLinkedList list) {
        for (int i = 0; i < list.size(); i++) {
            this.addLast(list.get(i));
        }
    }

    // Inserts the specified element 'body' at the beginning of this list.
    public void addFirst(Massive m) {
        if (head == null) {
            head = tail = new MyMassiveListNode(m, null, null);
        } else if (head == tail) {
            head = new MyMassiveListNode(m, null, head);
            tail.setPrev(head);
        } else {
            head = new MyMassiveListNode(m, null, head);
            head.getNext().setPrev(head);
        }
    }

    // Appends the specified element 'body' to the end of this list.
    public void addLast(Massive m) {
        if (head == null) {
            head = tail = new MyMassiveListNode(m, null, null);
        } else if (head == tail) {
            tail = new MyMassiveListNode(m, tail, null);
            head.setNext(tail);
        } else {
            tail = new MyMassiveListNode(m, tail, null);
            tail.getPrev().setNext(tail);
        }
    }

    // Returns the last element in this list.
    // Returns 'null' if the list is empty.
    public Massive getLast() {
        if (head == null) {
            return null;
        }
        return tail.getBody();
    }

    // Returns the first element in this list.
    // Returns 'null' if the list is empty.
    public Massive getFirst() {
        if (head == null) {
            return null;
        }
        return head.getBody();
    }

    // Retrieves and removes the first element in this list.
    // Returns 'null' if the list is empty.
    public Massive pollFirst() {
        if (head == null) {
            return null;
        }
        Massive toReturnBody = head.getBody();
        head = head.getNext();
        if (head == null) {
            tail = null;
        } else {
            head.setPrev(null);
        }
        return toReturnBody;
    }

    // Retrieves and removes the last element in this list.
    // Returns 'null' if the list is empty.
    public Massive pollLast() {
        if (head == null) {
            return null;
        }
        Massive toReturnBody = tail.getBody();
        tail = tail.getPrev();
        if (tail == null) {
            head = null;
        } else {
            tail.setNext(null);
        }
        return toReturnBody;
    }

    // Inserts the specified element at the specified position in this list.
    // Precondition: i >= 0 && i <= size().
    public void add(int i, Massive m) {
        if (i == 0) {
            addFirst(m);
        } else if (i == size()) {
            addLast(m);
        } else {
            head.add(m, i);
        }
    }

    // Returns the element at the specified position in this list.
    // Precondition: i >= 0 && i < size().
    public Massive get(int i) {
        return head.get(i);
    }

    // Returns the index of the first occurrence of the specified element in this list, or -1 if
    // this list does not contain the element.
    public int indexOf(Massive m) {
        if (head == null) {
            return -1;
        }
        return head.indexOf(m, 0);
    }

    // Returns the number of elements in this list.
    public int size() {
        if (head == null) {
            return 0;
        }
        return head.size();
    }
}
