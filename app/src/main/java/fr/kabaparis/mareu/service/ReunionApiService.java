package fr.kabaparis.mareu.service;


import java.util.ArrayList;
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



    /**
     * Get all my Reunions by Id
     * @return {@link List}
     */
    Reunion getReunionById(long id);

    /**
     * Get all my Reunions filtered by room
     * @return {@link List}
     */
    ArrayList<Reunion> getReunionFilteredByRoom(String room);

    /**
     * Get all my Reunions filtered by room
     * @return {@link List}
     */
    ArrayList<Reunion> getReunionFilteredByDate(int year, int month, int day);


    /**
     * Get a reunion overlapped
     * @param
     */
    boolean getOverlappingReunions(int year, int month, int day);

}
