package be.ac.umons.mapOverlay;

import be.ac.umons.mapOverlay.model.geometry.Line;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public abstract class IntersectionsFinderDependentTest {
    public IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
    public MockedStatic<IntersectionsFinder> intersectionsFinderMockedStatic = Mockito.mockStatic(IntersectionsFinder.class);


    protected void setSweepLineY(double value){
        Line sl = new Line(0, value, 1, value);
        Mockito.when(intersectionsFinder.getSweepLineY()).thenReturn(value);
        Mockito.when(intersectionsFinder.getSweepLine()).thenReturn(sl);
    }

}
