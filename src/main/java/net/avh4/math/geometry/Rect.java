package net.avh4.math.geometry;

public class Rect {
    private final double minX;
    private final double minY;
    private final double width;
    private final double height;

    public static Rect fromCenter(double centerX, double centerY, double width, double height) {
        return new Rect(centerX - width / 2, centerY - height / 2, width, height);
    }

    public static Rect fromCenter(Point center, double width, double height) {
        return fromCenter(center.x(), center.y(), width, height);
    }

    public static Rect fromTopLeft(double minX, double minY, double width, double height) {
        return new Rect(minX, minY, width, height);
    }

    public static Rect ofSize(double width, double height) {
        return Rect.fromTopLeft(0, 0, width, height);
    }

    /**
     * You should use Rect.fromTopLeft instead unless you are subclassing.
     */
    protected Rect(double minX, double minY, double width, double height) {
        this.minX = minX;
        this.minY = minY;
        this.width = width;
        this.height = height;
    }

    public double minX() {
        return minX;
    }

    public double minY() {
        return minY;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    public double maxY() {
        return minY + height;
    }

    public double maxX() {
        return minX + width;
    }

    public boolean contains(double x, double y) {
        return x >= minX && x <= maxX()
                && y >= minY && y <= maxY();
    }

    public boolean contains(Rect rect) {
        return rect.minX >= minX && rect.maxX() <= maxX()
                && rect.minY >= minY && rect.maxY() <= maxY();
    }

    @Override
    public String toString() {
        return "Rect{" +
                "minX=" + minX +
                ", minY=" + minY +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rect rect = (Rect) o;

        if (Double.compare(rect.height, height) != 0) return false;
        if (Double.compare(rect.minX, minX) != 0) return false;
        if (Double.compare(rect.minY, minY) != 0) return false;
        if (Double.compare(rect.width, width) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = minX != +0.0d ? Double.doubleToLongBits(minX) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = minY != +0.0d ? Double.doubleToLongBits(minY) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = width != +0.0d ? Double.doubleToLongBits(width) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = height != +0.0d ? Double.doubleToLongBits(height) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Rect divide(double leftPercent, double topPercent, double rightPercent, double bottomPercent) {
        return new Rect(minX + width * leftPercent, minY + height * topPercent,
                width * (rightPercent - leftPercent), height * (bottomPercent - topPercent));
    }

    public Rect inset(double leftInset, double topInset, double rightInset, double bottomInset) {

        double width = Math.max(0, this.width - leftInset - rightInset);
        double minX = this.minX + leftInset;
        if (width == 0) {
            minX = this.minX + this.width / 2;
        }
        double height = Math.max(0, this.height - topInset - bottomInset);
        double minY = this.minY + topInset;
        if (height == 0) {
            minY = this.minY + this.height / 2;
        }
        return new Rect(minX, minY, width, height);
    }

    public Rect inset(double inset) {
        return inset(inset, inset, inset, inset);
    }

    public Rect topLeft(double leftX, double topY) {
        return new Rect(0, 0, width, height); // TODO
    }

    public double midX() {
        return minX + width / 2;
    }

    public double midY() {
        return minY + height / 2;
    }

    public Rect aspectRatio(double desiredWidth, double desiredHeight) {
        if (width / height < desiredWidth / desiredHeight) {
            return Rect.fromCenter(midX(), midY(), width, width / desiredWidth * desiredHeight);
        } else {
            return Rect.fromCenter(midX(), midY(), height / desiredHeight * desiredWidth, height);
        }
    }

    public Rect scale(Rect fromRect, Rect toRect) {
        double newWidth = width / fromRect.width * toRect.width;
        double newHeight = height / fromRect.height * toRect.height;
        double newX = (minX - fromRect.minX) / fromRect.width * toRect.width + toRect.minX;
        double newY = (minY - fromRect.minY) / fromRect.height * toRect.height + toRect.minY;
        return new Rect(newX, newY, newWidth, newHeight);
    }

    public Rect translate(Rect fromRect, Rect toRect) {
        double newX = minX + fromRect.minX - toRect.minX;
        double newY = minY + fromRect.minY - toRect.minY;
        return new Rect(newX, newY, width, height);
    }

    public Rect size() {
        return Rect.ofSize(width, height);
    }

    public double area() {
        return width * height;
    }

    public static Rect interpolate(Rect a, Rect b, double percent) {
        double minX = interpolate(a.minX, b.minX, percent);
        double minY = interpolate(a.minY, b.minY, percent);
        double width = interpolate(a.width, b.width, percent);
        double height = interpolate(a.height, b.height, percent);
        return new Rect(minX, minY, width, height);
    }

    private static double interpolate(double a, double b, double percent) {
        return a + (b - a) * percent;
    }

    public Rect resizeFromCenter(double newWidth, double newHeight) {
        return Rect.fromCenter(midX(), midY(), newWidth, newHeight);
    }

    public Rect left(double leftPixels) {
        return new Rect(minX, minY, leftPixels, height);
    }

    public Rect top(double topPixels) {
        return new Rect(minX, minY, width, topPixels);
    }

    public Rect right(double rightPixels) {
        return new Rect(minX + width - rightPixels, minY, rightPixels, height);
    }

    public Rect bottom(double bottomPixels) {
        return new Rect(minX, minY + height - bottomPixels, width, bottomPixels);
    }

    public Rect translate(int dx, int dy) {
        return new Rect(minX + dx, minY + dy, width, height);
    }

    public Rect scale(double xScale, double yScale) {
        return new Rect(minX * xScale, minY * xScale, width * xScale, height * yScale);
    }

    public double percentX(double percent) {
        return minX + width * percent;
    }

    public boolean contains(Point point) {
        return contains(point.x(), point.y());
    }

    public double toPercentX(double x) {
        return (x - minX) / width;
    }

    public static Rect unit() {
        return new Rect(0, 0, 1, 1);
    }
}
