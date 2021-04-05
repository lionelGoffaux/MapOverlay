package be.ac.umons.mapOverlay;

import be.ac.umons.mapOverlay.model.geometry.Line;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public abstract class IntersectionsFinderDependentTest {
    public IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
    public MockedStatic<IntersectionsFinder> intersectionsFinderMockedStatic = Mockito.mockStatic(IntersectionsFinder.class);


    protected void setEventPoint(Point eventPoint){
        Line sl = new Line(0, eventPoint.getY(), 1, eventPoint.getY());
        Mockito.when(intersectionsFinder.getEventPoint()).thenReturn(eventPoint);
        Mockito.when(intersectionsFinder.getSweepLineY()).thenReturn(eventPoint.getY());
        Mockito.when(intersectionsFinder.getSweepLine()).thenReturn(sl);
    }

}
