import codedraw.CodeDraw;

import java.awt.*;

// This class represents vectors in a 3D vector space.
public class Vector3 {

    //TODO: change modifiers.
    private double x;
    private double y;
    private double z;

    //TODO: define constructor.
    public Vector3(double initX, double initY, double initZ){
        this.x = initX;
        this.y = initY;
        this.z = initZ;
    }

    // Returns the sum of this vector and vector 'v'.
    public Vector3 plus(Vector3 v) {

        //TODO: implement method.
        return null;
    }

    // Returns the product of this vector and 'd'.
    public Vector3 times(double d) {

        //TODO: implement method.
        return null;
    }

    // Returns the sum of this vector and -1*v.
    public Vector3 minus(Vector3 v) {

        //TODO: implement method.
        return null;
    }

    // Returns the Euclidean distance of this vector
    // to the specified vector 'v'.
    public double distanceTo(Vector3 v) {

        //TODO: implement method.
        return -1d;
    }

    // Returns the length (norm) of this vector.
    public double length() {

        //TODO: implement method.
        return 0;
    }

    // Normalizes this vector: changes the length of this vector such that it becomes 1.
    // The direction and orientation of the vector is not affected.
    public void normalize() {

        //TODO: implement method.
    }

    // Draws a filled circle with a specified radius centered at the (x,y) coordinates of this vector
    // in the canvas associated with 'cd'. The z-coordinate is not used.
    public void drawAsFilledCircle(CodeDraw cd, double radius) {

        //TODO: implement method.
    }

    // Returns the coordinates of this vector in brackets as a string
    // in the form "[x,y,z]", e.g., "[1.48E11,0.0,0.0]".
    public String toString() {

        //TODO: implement method.
        return "";
    }

}

