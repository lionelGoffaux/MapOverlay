package be.ac.umons.mapOverlay.model.map;

public class Segment {
    private Point upperPoint, endPoint;

    public Segment(double x1, double y1, double x2, double y2){
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Segment(Point p1, Point p2){
        upperPoint = p1;
        endPoint = p2;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "upperPoint=" + upperPoint +
                ", endPoint=" + endPoint +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return upperPoint.equals(segment.upperPoint) && endPoint.equals(segment.endPoint);
    }
}
