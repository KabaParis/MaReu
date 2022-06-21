package fr.kabaparis.mareu;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fr.kabaparis.mareu.model.Reunion;
import fr.kabaparis.mareu.service.DummyReunionGenerator;
import fr.kabaparis.mareu.service.ReunionApiService;
import fr.kabaparis.mareu.ui.reunion_list.DI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Reunion service
 */

public class ReunionUnitTest {

    private ReunionApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getReunionsWithSuccess() {
        List<Reunion> Reunions = service.getReunions();
        List<Reunion> expectedReunions = DummyReunionGenerator.DUMMY_REUNIONS;
        assertTrue(expectedReunions.containsAll(Reunions));
    }

    @Test
    public void deleteReunionWithSuccess() {
        Reunion reunionToDelete = service.getReunions().get(0);
        service.deleteReunion(reunionToDelete);
        assertFalse(service.getReunions().contains(reunionToDelete));
    }

    @Test
    public void createReunionWithSuccess() {
        Reunion reunionToCreate = new Reunion(8, "test",
                1648665625000L, "test", "test", R.color.orange);
        service.createReunion(reunionToCreate);
        assertTrue(service.getReunions().contains(reunionToCreate));
    }

    @Test
    public void getReunionFilteredByRoom() {
        ArrayList<Reunion> reunion = service.getReunionFilteredByRoom("Réunion A");
        assertEquals(2, reunion.size());
        reunion = service.getReunionFilteredByRoom("Réunion E");
        assertEquals(0, reunion.size());
    }

    @Test
    public void getReunionFilteredByDate() {
        ArrayList<Reunion> reunion = service.getReunionFilteredByDate(2022, 04, 04);
        assertEquals(1, reunion.size());
        reunion = service.getReunionFilteredByDate(2022, 06, 03);
        assertEquals(0, reunion.size());

    }

    @Test
    public void getOverlappingReunions() {
        // Create a new meeting
        boolean overlappingReunions = service.getOverlappingReunions("Réunion A", 2022,
                04, 04, 19, 30);
        // There is an overlapping so the meeting can't be created
        assertTrue(overlappingReunions);

        // Create another meeting
        boolean notOverlappingReunions = service.getOverlappingReunions("Réunion A", 2022,
                06, 20, 10, 00);
        // There is no overlapping so the meeting is created
        assertFalse(notOverlappingReunions);

    }
}



