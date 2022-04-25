class MyListNode {
    private Body value;
    private MyListNode next;
    private MyListNode previous;

    public MyListNode(Body v, MyListNode n, MyListNode p) {
        value = v;
        next = n;
        previous = p;
    }

    public Body getValue() {
        return value;
    }

    public MyListNode getNext() {
        return next;
    }

    public MyListNode getPrevious() {
        return previous;
    }

    public void setValue(Body v) {
        this.value = v;
    }

    public void setNext(MyListNode n) {
        this.next = n;
    }

    public void setPrevious(MyListNode p) {
        this.previous = p;
    }

    public MyListNode removeSubList(int fromIndex, int toIndex, MyListNode current){
        MyListNode first = null, last = null;

        int index = 0;

        while (current != null) {
            if (index == fromIndex) {
                first = current;
            }
            else if (index == toIndex) {
                last = current;
                if (first.previous != null) {
                    last.previous = first.previous;
                    first.previous.next = last;
                    return null;
                } else {
                    last.previous = null;
                    return last;
                }
            }
            index++;
            current = current.next;
        }

        return null;
    }
}


// A list of bodies implemented as a linked list.
// The number of elements of the list is not limited.
public class BodyLinkedList {

    private MyListNode head;

    // Initializes 'this' as an empty list.
    public BodyLinkedList() {
    }

    // Initializes 'this' as an independent copy of the specified list 'list'.
    // Calling methods of this list will not affect the specified list 'list'
    // and vice versa.
    // Precondition: list != null.
    public BodyLinkedList(BodyLinkedList list) {
        //first element of copiedList is head of list
        MyListNode copiedList = list.head;

        while (copiedList != null) {
            //add element of list to tail of this list
            this.addLast(copiedList.getValue());
            //set next element of copiedList as new 'current' element of copiedList
            copiedList = copiedList.getNext();
        }
    }



    // Inserts the specified element 'body' at the beginning of this list.
    public void addFirst(Body body) {
        //set current head as next element and save body as new head of list
        head = new MyListNode(body, head, null);

        //set 'new' head as previous node of 'old head' (if exists)
        if (head.getNext() != null) {
            head.getNext().setPrevious(head);
        }
    }
    public void removeSubList(int fromIndex, int toIndex){
        if (fromIndex == 0 && toIndex >= size()) {
            head = null;
        }

        if (head != null) {
            MyListNode result = head.removeSubList(fromIndex, toIndex, head);

            if (result != null) {
                head = result;
            }
        }
    }
    public MyListNode getLastElement() {
        MyListNode lastElement = head;

        //loop through list till last element is found
        while (lastElement != null && lastElement.getNext() != null) {
            lastElement = lastElement.getNext();
        }
        return lastElement;
    }

    // Appends the specified element 'body' to the end of this list.
    public void addLast(Body body) {
        MyListNode lastElement = this.getLastElement();

        //append at end if lastElement exists
        if (lastElement != null) {
            lastElement.setNext(new MyListNode(body, null, lastElement));
        }
        //set body as head if list is empty
        else {
            head = new MyListNode(body, null, null);
        }
    }

    // Returns the last element in this list.
    // Returns 'null' if the list is empty.
    public Body getLast() {
        if (head != null) return getLastElement().getValue();
        else {
            return null;
        }
    }

    // Returns the first element in this list.
    // Returns 'null' if the list is empty.
    public Body getFirst() {
        if (head != null) return head.getValue();
        else {
            return null;
        }
    }

    // Retrieves and removes the first element in this list.
    // Returns 'null' if the list is empty.
    public Body pollFirst() {
        //save head as firstElement
        MyListNode firstElement = head;

        //return null if list is empty
        if (firstElement == null) return null;
        else {
            //set head as next element of head/firstElement
            head = firstElement.getNext();
            //delete previous reference from head (to old head/firstElement), if head is not null
            if (head != null) head.setPrevious(null);
        }

        //return value of first element
        return firstElement.getValue();
    }

    // Retrieves and removes the last element in this list.
    // Returns 'null' if the list is empty.
    public Body pollLast() {
        //get last element/node
        MyListNode lastElement = getLastElement();

        //return null if list is empty
        if (lastElement == null) return null;
        else {
            //delete next reference from previous element (to this element)
            lastElement.getPrevious().setNext(null);

            //delete previous reference from this element (to previous element), but basically not necessary
            lastElement.setPrevious(null);
        }

        //return value of last element
        return lastElement.getValue();
    }

    // Inserts the specified element 'body' at the specified position in this list.
    // Precondition: i >= 0 && i <= size().
    public void add(int i, Body body) {

        //add body at first index if list is empty
        if (head == null) {
            head = new MyListNode(body, null, null);
        }

        //add body at specified index
        else {
            MyListNode beforeInsert = head;

            //get element before wanted position/index
            for (int k = 0; k < i - 1; k++) {
                beforeInsert = beforeInsert.getNext();
            }

            //create new element at specified index with beforeInsert as previous and 'original next value' of
            //beforeInsert as next element
            MyListNode insertAtIndex = new MyListNode(body, beforeInsert.getNext(), beforeInsert);
            //set new element as next element of beforeInsert
            beforeInsert.setNext(insertAtIndex);

            //set new element at index as previous element of next value (if exists)
            if (insertAtIndex.getNext() != null) insertAtIndex.getNext().setPrevious(insertAtIndex);
        }
    }

    // Returns the element at the specified position in this list.
    // Precondition: i >= 0 && i < size().
    public Body get(int i) {
        //set indexElement as head
        MyListNode indexElement = head;

        //set indexElement as next element of indexElement till index is reached
        for (int k = 0; k < i; k++) {
            indexElement = indexElement.getNext();
        }

        //return value of element at specified index
        return indexElement.getValue();
    }

    // Returns the index of the first occurrence of the specified element in this list, or -1 if
    // this list does not contain the element.
    public int indexOf(Body body) {
        //start with head
        MyListNode wantedElement = head;
        int index = 0;

        //iterate through list as long as next element exists
        while (wantedElement != null) {
            //return index if first occurrence is found
            if (wantedElement.getValue() == body) return index;

            //save next element of wantedElement as 'new' wantedElement and increase index by 1
            else {
                wantedElement = wantedElement.getNext();
                index++;
            }
        }
        //return -1 if not found
        return -1;
    }

    // Removes all bodies of this list, which are colliding with the specified
    // body. Returns a list with all the removed bodies.
    public BodyLinkedList removeCollidingWith(Body body) {

        //create list of collided bodies
        BodyLinkedList collided = new BodyLinkedList();

        //start with head
        MyListNode observedBody = head;

        //iterate as long as observedBody is not null
        while(observedBody != null){
            //check if the observedBody and body are colliding
            boolean isColliding = body.distanceTo(observedBody.getValue())<body.radius() + observedBody.getValue().radius();

            //add observedBody to collided-list and delete references if they are colliding, and if they are not the same
            if(isColliding && observedBody.getValue() != body){
                collided.addLast(observedBody.getValue());
                if(observedBody.getPrevious() == null) head = observedBody.getNext();
                else {
                    observedBody.getPrevious().setNext(observedBody.getNext());
                }
            }
            //set next observedBody as 'new' observedBody
            observedBody = observedBody.getNext();
        }

        //return list
        return collided;
    }

    // Returns the number of bodies in this list.
    public int size() {
        //start with head
        MyListNode lastElement = head;
        int index = 0;

        //iterate through list as long as lastElement is not null
        while(lastElement !=null){
            //save next element of lastElement as 'new' lastElement and increase index by 1
            lastElement = lastElement.getNext();
            index++;
        }
        return index;
    }
}