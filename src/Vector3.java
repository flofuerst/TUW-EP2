import codedraw.CodeDraw;

import java.awt.*;

// This class represents vectors in a 3D vector space.
public class Vector3 {

    private double x;
    private double y;
    private double z;

    public Vector3() {
        new Vector3(0.0, 0.0, 0.0);
    }

    public Vector3(double initX, double initY, double initZ) {
        this.x = initX;
        this.y = initY;
        this.z = initZ;
    }

    // Returns the sum of this vector and vector 'v'.
    public Vector3 plus(Vector3 v) {

        Vector3 result = new Vector3();
        result.x = this.x + v.x;
        result.y = this.y + v.y;
        result.z = this.z + v.z;

        return result;
    }

    // Returns the product of this vector and 'd'.
    public Vector3 times(double d) {

        Vector3 result = new Vector3();
        result.x = this.x * d;
        result.y = this.y * d;
        result.z = this.z * d;

        return result;
    }

    // Returns the sum of this vector and -1*v.
    public Vector3 minus(Vector3 v) {
        return plus(v.times(-1));
    }

    // Returns the Euclidean distance of this vector
    // to the specified vector 'v'.
    public double distanceTo(Vector3 v) {

        double dX = this.x - v.x;
        double dY = this.y - v.y;
        double dZ = this.z - v.z;

        return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
    }

    // Returns the length (norm) of this vector.
    public double length() {

        return distanceTo(new Vector3()); // distance to origin.
    }

    // Normalizes this vector: changes the length of this vector such that it becomes 1.
    // The direction and orientation of the vector is not affected.
    public void normalize() {
        double length = length();
        this.x /= length;
        this.y /= length;
        this.z /= length;
    }

    // Draws a filled circle with a specified radius centered at the (x,y) coordinates of this vector
    // in the canvas associated with 'cd'. The z-coordinate is not used.
    public void drawAsFilledCircle(CodeDraw cd, double radius) {
        double x = cd.getWidth() * (this.x + Simulation.SECTION_SIZE / 2) / Simulation.SECTION_SIZE;
        double y = cd.getWidth() * (this.y + Simulation.SECTION_SIZE / 2) / Simulation.SECTION_SIZE;
        radius = cd.getWidth() * radius / Simulation.SECTION_SIZE;
        cd.fillCircle(x, y, Math.max(radius, 1.5));
    }

    //Draws the name of a planet obove the planet itself
    public void drawName(CodeDraw cd, String name){
        double x = cd.getWidth() * (this.x + Simulation.SECTION_SIZE / 2) / Simulation.SECTION_SIZE;
        double y = cd.getWidth() * (this.y + Simulation.SECTION_SIZE / 2) / Simulation.SECTION_SIZE;
        cd.drawText(x-20,y - 20, name);
    }

    // Returns the coordinates of this vector in brackets as a string
    // in the form "[x,y,z]", e.g., "[1.48E11,0.0,0.0]".
    public String toString() {
        return "[" + this.x + "," + this.y + "," + this.z + "]";
    }

}

