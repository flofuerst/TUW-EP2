// A queue of bodies. A collection designed for holding bodies prior to processing.
// The bodies of the queue can be accessed in a FIFO (first-in-first-out) manner,
// i.e., the body that was first inserted by 'add' is retrieved first by 'poll'.
// The number of elements of the queue is not limited.
//
public class BodyQueue {

    private Body[] bq;
    private BodyQueue bq_copy;
    private int size;

    // Initializes this queue with an initial capacity.
    // Precondition: initialCapacity > 0.
    public BodyQueue(int initialCapacity) {
        this.size = initialCapacity;
        this.bq = new Body[initialCapacity];
    }

    // Initializes this queue as an independent copy of the specified queue.
    // Calling methods of this queue will not affect the specified queue
    // and vice versa.
    // Precondition: q != null.
    public BodyQueue(BodyQueue q) {
        this.size = q.size;
        this.bq = new Body[this.size];
        for (int i = 0; i < q.size; i++) {
            this.bq[i] = q.bq[i];
        }
    }

    // Adds the specified body 'b' to this queue.
    public void add(Body b) {
        //check if last element has value ==> check if bq is full
        //if full, make 'bq' doubled size
        if (this.bq[this.size - 1] != null) {
            size *= 2;
            //create temp-array
            Body[] temp = new Body[size];
            for (int i = 0; i < this.bq.length; i++) {
                temp[i] = this.bq[i];
            }
            //give bq reference of temp-array
            this.bq = temp;
        }
        //if queue is empty, simply add element to queue
        if (this.bq[0] == null) {
            this.bq[0] = b;
        } else {
            //if queue not empty, shift entries and add element to first index
            int numberOfElements = this.size();
            Body temp;
            for (int entry = numberOfElements; entry>0; entry--) {
                this.bq[entry] = this.bq[entry-1];
            }
            this.bq[0] = b;
        }
    }

    // Retrieves and removes the head of this queue, or returns 'null'
    // if this queue is empty.
    public Body poll() {
        Body saved;
        //check if first element is null ==> if yes, whole queue is null
        if (this.bq[0] == null) return null;
        else {
            //check which element is null and get element before(=head)
            for (int i = 1; i < this.size; i++) {
                if (this.bq[i] == null) {
                    saved = this.bq[i - 1];
                    this.bq[i - 1] = null;
                    return saved;
                }
            }
            //if last element != null ==> last element is head
            saved = this.bq[this.size - 1];
            this.bq[this.size - 1] = null;
            return saved;

        }
    }

    // Returns the number of bodies in this queue.
    public int size() {
        if (this.bq[0] == null) return 0;
        else {
            for (int i = 1; i < this.bq.length; i++) {
                if (this.bq[i] == null) return i;
            }
            //breaking out of for loop (without returning) means, that last element was not null
            return this.bq.length;
        }
    }
}
