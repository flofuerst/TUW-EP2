// A map that associates an object of 'Massive' with a Vector3. The number of key-value pairs
// is not limited.
//

import codedraw.CodeDraw;

import java.util.Iterator;

class MyMassiveTreeNode {
    private Vector3 value;
    private Massive key;
    private MyMassiveTreeNode left, right;

    public MyMassiveTreeNode(Massive k, Vector3 v) {
        this.key = k;
        this.value = v;
    }

    public Massive getKey() {
        return this.key;
    }

    public Vector3 getValue(){
        return this.value;
    }

    public MyMassiveTreeNode getLeft() {
        return this.left;
    }

    public MyMassiveTreeNode getRight() {
        return this.right;
    }

    public MyMassiveIterator keyIterator() {
        return new MyMassiveIterator(this);
    }

    //check if key is contained in tree
    public boolean contains(Massive k) {
        return this.key.equals(k) || this.left != null && this.left.contains(k) || this.right != null && this.right.contains(k);
    }

    public Vector3 addNode(Massive k, Vector3 v) {
        Vector3 oldValue = null;

        //update value and return old value if key already exists
        if (this.key == k) {
            oldValue = this.value;
            this.value = v;
        }
        //go through childs
        else {
            //if mass of 'new' key is bigger --> go to right branch
            if (k.mass() > this.key.mass()) {
                //create new child if right child is not existing
                if (this.right == null) this.right = new MyMassiveTreeNode(k, v);
                else {
                    //call addNode of right child if it is existing
                    oldValue = this.right.addNode(k, v);
                }

            }
            //if mass of 'new' key is smaller or equal --> go to left branch
            else {
                //create new child if left child is not existing
                if (this.left == null) this.left = new MyMassiveTreeNode(k, v);
                else {
                    //call addNode of left child if it is existing
                    oldValue = this.left.addNode(k, v);
                }
            }
        }
        //return oldValue; return null if key was not already existing in tree
        return oldValue;
    }

    public Vector3 find(Massive k) {
        Vector3 foundValue = null;

        //return value if key is found
        if (this.key == k) {
            foundValue = this.value;
        } else {
            //'search' in right branch if mass of key is bigger than this key
            if (k.mass() > this.key.mass()) {
                //return null if right child is not existing
                if (this.right == null) foundValue = null;
                else {
                    //call find method of right child if it is existing
                    foundValue = this.right.find(k);
                }
            }
            //'search' in left branch if mass of key is smaller than or equal this key
            else {
                //return null if left child is not existing
                if (this.left == null) foundValue = null;
                else {
                    //call find method of left child if it is existing
                    foundValue = this.left.find(k);
                }
            }
        }
        //return foundValue; return null if key was not found
        return foundValue;
    }

    public String createString() {
        String output = "";
        //call createString of right child if exists
        if (this.right != null) output += this.right.createString();

        //represent current massive
        output += this.key + ", mass: " + this.key.getMass() + " --> value = " + this.value.length() + " | ";

        //call createString of left child if exists
        if (this.left != null) output += this.left.createString();
        return output;
    }

    public int countNodes() {
        //count this node
        int count = 1;

        //count child nodes
        if (right != null) count += right.countNodes();
        if (left != null) count += left.countNodes();

        return count;
    }

    //remove Node and repair remaining tree
    public void removeNode(Massive element) {
        MyMassiveTreeNode newNode;
        if (key.equals(element)) {
            if(right != null) {
                newNode = repair();

                this.key = newNode.key;
                this.value = newNode.value;
            }
            else if(left!=null) key = left.key;
            else key = null;

            
        } else if (element.getMass() > key.getMass()) {
            right.removeNode(element);
            if(right.key == null) right = null;
        }
        else {
            left.removeNode(element);
            if(left.key == null) left = null;
        }
    }

    //method to repair remaining tree
    public MyMassiveTreeNode repair(){
        if(right == null) return null;

        MyMassiveTreeNode oldNode = this;
        MyMassiveTreeNode thisNode = right.repair();
        this.key = thisNode.key;
        this.value = thisNode.value;

        if(right.key == null) right = null;

        return  oldNode;
    }
}

public class MassiveForceTreeMap implements MassiveIterable {

    private MyMassiveTreeNode root;

    public MyMassiveTreeNode getRoot() {
        return root;
    }

    // Adds a new key-value association to this map. If the key already exists in this map,
    // the value is replaced and the old value is returned. Otherwise 'null' is returned.
    // Precondition: key != null.
    public Vector3 put(Massive key, Vector3 value) {
        //set key and value as root, if root is not existing
        if (root == null) {
            root = new MyMassiveTreeNode(key, value);
            return null;
        }
        //otherwise add node to tree
        else {
            return root.addNode(key, value);
        }
    }

    // Returns the value associated with the specified key, i.e. the method returns the force vector
    // associated with the specified key. Returns 'null' if the key is not contained in this map.
    // Precondition: key != null.
    public Vector3 get(Massive key) {
        //return null if root is not existing
        if (root == null) return null;
        else {
            //find key in tree
            return root.find(key);
        }
    }

    // Returns 'true' if this map contains a mapping for the specified key.
    //Precondition: key != null
    public boolean containsKey(Massive key) {
        if (root == null) return false;
        else {
            return root.contains(key);
        }
    }

    // Returns a readable representation of this map, in which key-value pairs are ordered
    // descending according to 'key.getMass()'.
    public String toString() {
        //message when tree is empty
        if (root == null) return "!!!tree is empty!!!";
        else {
            //create String according to specification
            return root.createString();
        }
    }

    // Returns a `MassiveSet` view of the keys contained in this tree map. Changing the
    // elements of the returned `MassiveSet` object also affects the keys in this tree map.
    public MassiveSet getKeys() {

        return new MyMassiveSet(this);
    }

    public int count() {
        if (root != null) return root.countNodes();
        else return 0;
    }

    public void clear() {
        root = null;
    }

    public void remove(Massive element) {
        if (root != null && root.contains(element)) root.removeNode(element);
    }

    @Override
    public MassiveIterator iterator() {
        return new MassiveIterator() {
            MyMassiveIterator iterator = root.keyIterator();

            @Override
            public Massive next() {
                return iterator.next();
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }
        };
    }
}

class MyMassiveSet implements MassiveSet {

    private MassiveForceTreeMap massiveTree;


    public MyMassiveSet(MassiveForceTreeMap massiveTree) {
        this.massiveTree = massiveTree;
    }

    // Returns 'true' if the set has the specified element (i.e., has an element equal to the
    // specified element).
    public boolean contains(Massive element) {
        return massiveTree.containsKey(element);
    }

    // Removes the specified element from the set.
    public void remove(Massive element) {
        massiveTree.remove(element);
    }

    // Removes all elements from the set.
    public void clear() {
        massiveTree.clear();
    }

    // Returns the number of elements in the set.
    public int size() {
        return massiveTree.count();
    }

    // Returns an object of 'MassiveLinkedList' with all elements of 'this'.
    public MassiveLinkedList toList() {
        MassiveLinkedList list = new MassiveLinkedList();

        for(Massive m : massiveTree){
            list.addLast(m);
        }

        return list;
    }

    public void draw(CodeDraw cd) {

    }

    // Returns an iterator over elements of 'Massive'.
    public MassiveIterator iterator() {
        return massiveTree.iterator();
    }
}