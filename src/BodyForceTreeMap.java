import codedraw.CodeDraw;

//represents tree node, which has a left and a right child
//could be written in new class file
class MyTreeNode {
    private Vector3 value;
    private Body key;
    private MyTreeNode left, right;

    public MyTreeNode(Body k, Vector3 v) {
        this.key = k;
        this.value = v;
    }

    //check if key is contained in tree
    public boolean contains(Body k) {
        return this.key == k || this.left != null && this.left.contains(k) || this.right != null && this.right.contains(k);
    }

    public Vector3 addNode(Body k, Vector3 v) {
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
                if (this.right == null) this.right = new MyTreeNode(k, v);
                else {
                    //call addNode of right child if it is existing
                    oldValue = this.right.addNode(k, v);
                }

            }
            //if mass of 'new' key is smaller or equal --> go to left branch
            else {
                //create new child if left child is not existing
                if (this.left == null) this.left = new MyTreeNode(k, v);
                else {
                    //call addNode of left child if it is existing
                    oldValue = this.left.addNode(k, v);
                }
            }
        }
        //return oldValue; return null if key was not already existing in tree
        return oldValue;
    }

    public Vector3 find(Body k) {
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
        //add own key and value to the String
        output += "mass: " + this.key.mass() + " --> value = " + this.value.length() + " | ";
        //call createString of left child if exists
        if (this.left != null) output += this.left.createString();
        return output;
    }
}

// A map that associates a Body with a Vector3 (typically this is the force exerted on the body).
// The number of key-value pairs is not limited.
public class BodyForceTreeMap {
    //could also had been done without 'implementation' in MyTreeNode, but doesn't make a difference
    private MyTreeNode root;

    // Adds a new key-value association to this map. If the key already exists in this map,
    // the value is replaced and the old value is returned. Otherwise 'null' is returned.
    // Precondition: key != null.
    public Vector3 put(Body key, Vector3 value) {
        //set key and value as root, if root is not existing
        if (root == null) {
            root = new MyTreeNode(key, value);
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
    public Vector3 get(Body key) {
        //return null if root is not existing
        if (root == null) return null;
        else {
            //find key in tree
            return root.find(key);
        }
    }

    // Returns 'true' if this map contains a mapping for the specified key.
    public boolean containsKey(Body key) {
        if (root == null) return false;
        else {
            return root.contains(key);
        }
    }

    // Returns a readable representation of this map, in which key-value pairs are ordered
    // descending according to the mass of the bodies.
    public String toString() {
        //message when tree is empty
        if (root == null) return "!!!tree is empty!!!";
        else {
            //create String according to specification
            return root.createString();
        }
    }
}
