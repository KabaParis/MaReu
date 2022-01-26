package service;

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



}
