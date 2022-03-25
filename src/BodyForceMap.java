// A map that associates a body with a force exerted on it. The number of
// key-value pairs is not limited.
//
public class BodyForceMap {

    private Body[] key_arr;
    private Vector3[] value_arr;
    private int size;

    // Initializes this map with an initial capacity.
    // Precondition: initialCapacity > 0.
    public BodyForceMap(int initialCapacity) {
        this.size = initialCapacity;
        this.key_arr = new Body[initialCapacity];
        this.value_arr = new Vector3[initialCapacity];
    }

    // Adds a new key-value association to this map. If the key already exists in this map,
    // the value is replaced and the old value is returned. Otherwise 'null' is returned.
    // Precondition: key != null.
    public Vector3 put(Body key, Vector3 force) {
        //check if key exists in map; if yes, replace value of key and return old key
        for (int x = 0; x < size; x++) {
            if (key_arr[x] == key) {
                Vector3 saved = value_arr[x];
                value_arr[x] = force;
                return saved;
            }
        }
        for (int i = 0; i < size; i++) {
            //add key and value to map if key_arr at index is null
            if (key_arr[i] == null) {
                key_arr[i] = key;
                value_arr[i] = force;
                return null;
            } else if (key.mass() >= this.key_arr[i].mass()) {
                //all smaller keys and values are shifted one index to the right
                for (int j = this.size - 1; j > i; j--) {
                    this.key_arr[j] = this.key_arr[j - 1];
                    this.value_arr[j] = this.value_arr[j - 1];
                }
                //add key and value to map if mass of key is bigger than,
                // or equal mass of key_arr at index
                this.key_arr[i] = key;
                this.value_arr[i] = force;
                break;
            }
        }
        return null;
    }

    // Returns the value associated with the specified key, i.e. the returns the force vector
    // associated with the specified body. Returns 'null' if the key is not contained in this map.
    // Precondition: key != null.
    public Vector3 get(Body key) {
        int left = 0;
        int right = size - 1;
        int equalIndex = 0;
        boolean equal = false;
        while (left <= right) {
            int middle = left + ((right - left) / 2);

            //check if key_arr at index middle is key
            if (this.key_arr[middle] == key) {
                return value_arr[middle];
            }
            //check if key_arr at index is null
            else if (this.key_arr[middle] == null) {
                right = middle - 1;
            }
            //define new right-border
            else if (this.key_arr[middle].mass() < key.mass()) {
                right = middle - 1;
            } else {
                //save index of first! equal mass for further iteration if needed
                if(this.key_arr[middle].mass() == key.mass() && !equal){
                    equalIndex = middle;
                    equal=true;
                }
                //define new left-border
                left = middle + 1;
            }
        }

        //iterate to the other (left) side of the same mass-keys
        for (int i = equalIndex-1; i >0; i--) {
            if (this.key_arr[i] == key) {
                return value_arr[i];
            }
        }
        return null;
    }
}
