import javax.imageio.metadata.IIOMetadataFormatImpl;

// A hash map that associates a 'Massive'-object with a Vector3 (typically this is the force
// exerted on the object). The number of key-value pairs is not limited.
//
public class MassiveForceHashMap {
    private Massive[] key_array = new Massive[65];
    private Vector3[] value_array = new Vector3[key_array.length];

    private int count = 0;

    // Initializes 'this' as an empty map.
    public MassiveForceHashMap() {
    }

    // Adds a new key-value association to this map. If the key already exists in this map,
    // the value is replaced and the old value is returned. Otherwise 'null' is returned.
    // Precondition: key != null.
    public Vector3 put(Massive key, Vector3 value) {
        if (key == null) return null;
        int index = find(key);
        Vector3 old_value = value_array[index];
        value_array[index] = value;

        if (key_array[index] == null) {
            key_array[index] = key;

            //extend arrays if number of hash map elements >= 75% of array capacity
            if (++count >= 0.75 * key_array.length) extendArrays(index);
        }
        return old_value;
    }

    //extends array-capacity/length
    private void extendArrays(int index) {
        Massive[] old_key_array = key_array;
        Vector3[] old_value_array = value_array;
        key_array = new Massive[(old_key_array.length << 1) - 1];
        value_array = new Vector3[(old_value_array.length << 1) - 1];
        for (int j = 0; j < old_key_array.length; j++) {
            if (old_key_array[j] != null) {
                key_array[index = find(old_key_array[j])] = old_key_array[j];
                value_array[index] = old_value_array[j];
            }
        }
    }

    //returns the index of the specific hash key if found or the next free index of this hash key
    private int find(Massive k) {
        if (k == null) return key_array.length - 1;

        //get original hash key index
        int index = k.hashCode() & (key_array.length - 2);

        //find new index like example from lecture notes
        while (key_array[index] != null && !key_array[index].equals(k)) {
            index = (index + 1) & (key_array.length - 2);
        }
        return index;
    }

    // Returns the value associated with the specified key, i.e. the method returns the force vector
    // associated with the specified key. Returns 'null' if the key is not contained in this map.
    // Precondition: key != null.
    public Vector3 get(Massive key) {
        int index = find(key);
        return key_array[index] != null && key_array[index].equals(key) ? value_array[index] : null;
    }

    // Returns 'true' if this map contains a mapping for the specified key.
    public boolean containsKey(Massive key) {
        int index = find(key);
        return key_array[index] != null && key_array[index].equals(key);
    }

    // Returns a readable representation of this map, with all key-value pairs. Their order is not
    // defined.
    public String toString() {
        String output = "";

        for (int i = 0; i < key_array.length - 1; i++) {
            if (key_array[i] != null) {
                output += output.isEmpty() ? "(" : ", (" + "key: " + key_array[i] + ", value: " + value_array[i] + ")";
            }
        }
        return output;
    }

    // Compares `this` with the specified object for equality. Returns `true` if the specified
    // `o` is not `null` and is of type `MassiveForceHashMap` and both `this` and `o` have equal
    // key-value pairs, i.e. the number of key-value pairs is the same in both maps and every
    // key-value pair in `this` equals one key-value pair in `o`. Two key-value pairs are
    // equal if the two keys are equal and the two values are equal. Otherwise `false` is returned.
    public boolean equals(Object o) {

        //return false if specified o is null or o is not type of MassiveForceHashMap
        if (o == null || !(o instanceof MassiveForceHashMap)) return false;

        for (int i = 0; i < this.key_array.length || i < ((MassiveForceHashMap) o).key_array.length; i++) {

            //return false if index is out of bounds or if key-value entries are not equal
            if (i >= this.key_array.length || i >= ((MassiveForceHashMap) o).key_array.length || !compareKeyValue(i, o)) {
                return false;
            }
        }
        return true;
    }

    //returns true if all key-value entries are the same
    private boolean compareKeyValue(int index, Object o) {
        return this.key_array[index].equals(((MassiveForceHashMap) o).key_array[index]) &&
                this.value_array[index].equals(((MassiveForceHashMap) o).value_array[index]);
    }

    // Returns the hashCode of `this`.
    public int hashCode() {
        int outputHash = count;

        for (int i = 0; i < key_array.length - 1; i++) {
            if (key_array[i] != null) {
                //add hashCode of key
                outputHash += key_array[i].hashCode();
                
                if (value_array[i] != null) {
                    //add hashCode of value
                    outputHash += key_array[i].hashCode();
                }
            }
        }
        return outputHash;
    }

    // Returns a list of all the keys in no specified order.
    public MassiveLinkedList keyList() {

        MassiveLinkedList list = new MassiveLinkedList();

        for (int i = 0; i < key_array.length-1; i++) {
            if (key_array[i] != null) {
                //add hashCode of key
                list.addLast(key_array[i]);
            }
        }

        return list;
    }

}