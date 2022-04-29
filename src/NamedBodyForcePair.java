import codedraw.CodeDraw;

// A body with a name and an associated force. The leaf node of
// a hierarchical cosmic system. This class implements 'CosmicSystem'.
//
public class NamedBodyForcePair implements CosmicSystem {
    String name;
    Body b;
    Vector3 force;


    // return massCenter/position of this bdy
    public Vector3 getMassCenter() {
        return b.massCenter();
    }

    // Returns the mass of this body.
    public double getMass() {
        return b.mass();
    }


    // Initializes this with name, mass, current position and movement. The associated force
    // is initialized with a zero vector.
    public NamedBodyForcePair(String name, double mass, Vector3 massCenter, Vector3 currentMovement) {
        this.name = name;
        b = new Body(mass, massCenter, currentMovement);
        force = new Vector3();
    }

    // Returns a readable representation of this system/body.
    public String toString() {

        return name;
    }

    // Returns the name of the body.
    public String getName() {
        return name;
    }

    public int numberOfBodies() {
        return 1;
    }

    // Returns the distance between the mass centers of 'this' and the specified system.
    //Precondition: cs != null
    public double distanceTo(CosmicSystem cs) {
        return this.b.massCenter().distanceTo(cs.getMassCenter());
    }

    // Adds the force that the specified body exerts on this body to this body
    // Precondition: b != null
    public void addForceFrom(Body b) {
        if(!this.b.equals(b)){
            force = force.plus(this.b.gravitationalForce(b));
        }
    }

    // Adds the force that this system exerts on each of the bodies of 'cs' to the bodies in 'cs'.
    // For exact computations this means that for each body of 'this' its force on each body of
    // 'cs' is added to this body of 'cs'.
    // Precondition: cs != null
    public void addForceTo(CosmicSystem cs) {
        cs.addForceFrom(b);
    }

    // Returns a list with all the bodies of 'this'. The order is not defined.
    public BodyLinkedList getBodies() {
        BodyLinkedList list = new BodyLinkedList();
        list.addLast(b);

        return list;
    }

    // Moves 'this' body according to the previously accumulated force and
    // resets force to zero.
    public void update() {
        //move body
        b.move(force);

        //reset force
        force = new Vector3();
    }

    public void draw(CodeDraw cd) {
    }
}
