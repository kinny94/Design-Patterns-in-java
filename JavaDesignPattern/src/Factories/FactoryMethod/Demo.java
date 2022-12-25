package Factories.FactoryMethod;

enum CoordinateSystem {
    CARTESIAN,
    POLAR
}

class Point {

    private double x, y;

    // when the constructor is made private, that means that there is no way on constructing the point unless you use the factory methods
    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // This is dirty implementation since we have to know how a and b are dependent on cs
    private Point(double a, double b, CoordinateSystem cs) {

        switch (cs) {
            case CARTESIAN: 
                this.x = a;
                this.y = b; 
                break;
            case POLAR:
                x = a * Math.cos(b);
                y = a * Math.sin(b);
                break;
        }
    }

    // making inner class so we can access the Point class
    public static class Factory {
        public static Point newCartesianPoint(double x, double y) {
            return new Point(x, y);
        }
    
        public static Point newPolarPoint(double rho, double theta) {
            return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
        }
    }
    
}

public class Demo {
    public static void main(String[] args) {
        Point point = Point.Factory.newCartesianPoint(2, 3);
    }
}
