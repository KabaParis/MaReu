package fr.kabaparis.mareu.service;

import java.util.List;

import fr.kabaparis.mareu.model.Reunion;

/**
 * Dummy mock for the Api
 */
public class DummyReunionApiService implements ReunionApiService {

    private List<Reunion> reunions = DummyReunionGenerator.generateReunions();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reunion> getReunions() {
        return reunions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteReunion(Reunion reunion) {
        reunions.remove(reunion);
    }

    /**
     * {@inheritDoc}
     *
     * @param reunion
     */
    @Override
    public void createReunion(Reunion reunion) {
        reunions.add(reunion);
    }



    // go through the id list and when it matches, return the reunion, if not, return null

    /**
     * {@inheritDoc}
     */
    @Override
    public Reunion getReunionById(long id) {
        for (Reunion reunion : reunions)
            if (reunion.getId() == id) {

                return reunion;
            }
        return null;
    }

}
