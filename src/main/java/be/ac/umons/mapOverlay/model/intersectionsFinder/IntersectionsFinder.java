package be.ac.umons.mapOverlay.model.intersectionsFinder;

import be.ac.umons.mapOverlay.model.event.Event;
import be.ac.umons.mapOverlay.model.event.EventQueue;
import be.ac.umons.mapOverlay.model.geometry.Line;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.mapOverlay.model.map.Map;
import be.ac.umons.mapOverlay.model.sweepLine.GetLCVisitor;
import be.ac.umons.mapOverlay.model.sweepLine.GetLeftNeighbourVisitor;
import be.ac.umons.mapOverlay.model.sweepLine.GetRightNeighbourVisitor;
import be.ac.umons.mapOverlay.model.sweepLine.SweepLineStatus;
import be.ac.umons.utils.observer.IntersectionsFinderEvent;
import be.ac.umons.utils.observer.Publisher;

import java.util.ArrayList;

public class IntersectionsFinder extends Publisher {

    protected static IntersectionsFinder instance;
    protected Map map = new Map();
    protected EventQueue eventQueue;
    protected SweepLineStatus status;
    protected double sweepLineY = 0;
    protected Point eventPoint = new Point(0, 0);
    protected ArrayList<Point> intersections;
    protected Point newSegmentStart;

    private IntersectionsFinderState state;

    protected IntersectionsFinder() {
        setState(EditionState.getInstance());
    }

    public static IntersectionsFinder getInstance(){
        if(instance==null)
            instance = new IntersectionsFinder();
        return instance;
    }

    /***
     * Change l'état de l'intersectionFinder.
     * @param state
     */
    protected void setState(IntersectionsFinderState state) {
        this.state = state;
        state.entry(this);
    }


    /***
     * Déclenche l'event stepForward.
     */
    public void stepForward(){
        state.stepForward(this);
        notifySubscribers(IntersectionsFinderEvent.STEP_FORWARD);
    }

    /***
     * Déclenche l'event findAll.
     */
    public void findAll(){
        state.findAll(this);
        notifySubscribers(IntersectionsFinderEvent.FIND_ALL);
    }

    /***
     * Déclenche l'event setMap.
     */
    public void setMap(Map map) {
        state.setMap(this, map);
        notifySubscribers(IntersectionsFinderEvent.SET_MAP);
    }

    /***
     * Déclenche l'event startnewSegment.
     */
    public void startNewSegment(double x, double y){
        state.startNewSegment(this, x, y);
        notifySubscribers(IntersectionsFinderEvent.START_NEW_SEGMENT);
    }

    /***
     * Déclenche l'event endNewSegment.
     */
    public void endNewSegment(double x, double y) {
        state.endNewSegment(this, x, y);
        notifySubscribers(IntersectionsFinderEvent.END_NEW_SEGMENT);
    }

    /***
     * Déclenche l'event createNewMap.
     */
    public void createNewMap() {
        state.newMap(this);
        notifySubscribers(IntersectionsFinderEvent.CREATE_NEW_MAP);
    }

    /***
     * Retourne la position en Y de la sweep line.
     * @return
     */
    public double getSweepLineY() {
        return sweepLineY;
    }

    /***
     * Retourne la sweep line.
     * @return
     */
    public Line getSweepLine() {
        return new Line(0, sweepLineY, 1, sweepLineY);
    }

    /***
     * Retourne la map.
     * @return
     */
    public Map getMap() {
        return map;
    }

    /***
     * Retourne la list des segments de la map.
     * @return
     */
    public ArrayList<Segment> getSegments(){
        return map.getSegments();
    }

    /***
     * Retourne la liste des intersections.
     * @return
     */
    public ArrayList<Point> getIntersections() {
        return intersections;
    }

    /***
     * Traite un event point pour trouver les intersection.
     * @param e
     */
    protected void handleEventPoint(Event e){
        ArrayList<Segment> u = e.getU();
        GetLCVisitor glcv = new GetLCVisitor(e.getPoint());
        status.accept(glcv);

        ArrayList<Segment>  l = glcv.getL();
        ArrayList<Segment>  c = glcv.getC();

        if (u.size() + l.size() + c.size() > 1){
            intersections.add(e.getPoint());
        }

        status.suppressAll(l);
        status.suppressAll(c);

        sweepLineY = e.getPoint().getY();
        eventPoint = e.getPoint();

        status.insertAll(u);
        status.insertAll(c);

        ArrayList<Segment> uc = new ArrayList<Segment>(u);
        uc.addAll(c);

        GetLeftNeighbourVisitor glnv;
        GetRightNeighbourVisitor grnv;

        if(uc.isEmpty()){
            glnv = new GetLeftNeighbourVisitor(e.getPoint());
            grnv = new GetRightNeighbourVisitor(e.getPoint());
            status.accept(glnv);
            status.accept(grnv);
            Segment sl = glnv.getNeighbour();
            Segment sr = grnv.getNeighbour();
            if (sr != null && sl != null) findNewEvent(sl, sr, e.getPoint());
        } else {
            Segment sp = Segment.getLeftest(uc);
            glnv = new GetLeftNeighbourVisitor(sp);
            status.accept(glnv);
            Segment sl = glnv.getNeighbour();
            if (sl != null) findNewEvent(sl, sp, e.getPoint());
            Segment spp = Segment.getRightest(uc);
            grnv =  new GetRightNeighbourVisitor(spp);
            status.accept(grnv);
            Segment sr = grnv.getNeighbour();
            if (sr != null) findNewEvent(sr, spp, e.getPoint());
        }
    }

    /***
     * Vérifie si un nouveau point d'event doit être ajouté.
     * @param sl
     * @param sr
     * @param point
     */
    protected void findNewEvent(Segment sl, Segment sr, Point point) {
        Point p = sl.getIntersection(sr);
        if (p!=null&&p.compareTo(point) > 0) eventQueue.insert(new Event(p));
    }

    /***
     * Retourne l'event point actuel.
     * @return
     */
    public Point getEventPoint() {
        return eventPoint;
    }
}
