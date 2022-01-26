package service;


import java.util.List;

import fr.kabaparis.mareu.model.Reunion;

/**
 * Reunion API client
 */
public interface ReunionApiService {

    /**
     * Get all my Reunions
     * @return {@link List}
     */
    List<Reunion> getReunions();

    /**
     * Deletes a reunion
     * @param reunion
     */
    void deleteReunion(Reunion reunion);

    /**
     * Create a reunion
     * @param reunion
     */
    void createReunion(Reunion reunion);

}
