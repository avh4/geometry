package net.avh4.math.geometry;

public class Point {
    private final double x;
    private final double y;

    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public Point translate(Rect fromRect, Rect toRect) {
        double xPercent = (x - fromRect.minX()) / fromRect.width();
        double yPercent = (y - fromRect.minY()) / fromRect.height();
        double newX = xPercent * toRect.width() + toRect.minX();
        double newY = yPercent * toRect.height() + toRect.minY();
        return new Point(newX, newY);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Double.compare(point.x, x) != 0) return false;
        if (Double.compare(point.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public static Point at(double x, double y) {
        return new Point(x, y);
    }
}
