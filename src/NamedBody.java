import codedraw.CodeDraw;

public class NamedBody implements Massive {
    String name;
    Body b;


    // Initializes this with name, mass, current position and movement. The associated force
    // is initialized with a zero vector.
    public NamedBody(String name, double mass, Vector3 massCenter, Vector3 currentMovement) {
        this.name = name;
        b = new Body(mass, massCenter, currentMovement);
    }

    // Returns the name of the body.
    public String getName() {
        return name;

    }

    //returns the mass of b
    public double mass() {
        return b.mass();
    }

    //returns the massCenter of b
    public Vector3 massCenter() {
        return b.massCenter();
    }

    // Compares `this` with the specified object. Returns `true` if the specified `o` is not
    // `null` and is of type `NamedBody` and both `this` and `o` have equal names.
    // Otherwise `false` is returned.
    public boolean equals(Object o) {
        return o != null && o instanceof NamedBody && this.name.equals(((NamedBody) o).name);

    }

    // Returns the hashCode of `this`.
    public int hashCode() {
        return this.name.hashCode();
    }

    // Returns a readable representation including the name of this body.
    public String toString() {
        return name + " [" + hashCode() + "] = " + b.toString();
    }

    public void draw(CodeDraw cd) {
        b.draw(cd);
    }

    public void move(Vector3 force) {
        b.move(force);
    }
}
