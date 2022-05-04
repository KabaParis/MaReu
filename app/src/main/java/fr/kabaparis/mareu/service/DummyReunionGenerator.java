package fr.kabaparis.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.kabaparis.mareu.R;
import fr.kabaparis.mareu.model.Reunion;

public abstract class DummyReunionGenerator {

    public static List<Reunion> DUMMY_REUNIONS = Arrays.asList(
            new Reunion(1, "Réunion A", 1651687485000L, "Peach",
                    "maxime@lamzone.com, alex@lamzone.com, nicolas@lamzone.com", R.color.light_blue),
            new Reunion(2, "Réunion B", 1645984703000L, "Mario",
                    "paul@lamzone.com, viviane@lamzone.com, sylvain@lamzone.com", R.color.purple_200),
            new Reunion(3, "Réunion C", 1648665625000L, "Luigi",
                    "amandine@lamzone.com, luc@lamzone.com", R.color.teal_700),
            new Reunion(4, "Réunion D", 1648665625000L, "Kaba",
                    "Stephanie@lamzone.com, pierre@lamzone.com, julie@lamzone.com", R.color.black),
            new Reunion(5, "Réunion A", 1641232703000L, "Pear",
                    "lucas@lamzone.com, alex@lamzone.com, jean@lamzone.com", R.color.red),
            new Reunion(6, "Réunion F", 1648665625000L, "Office",
                    "denise@lamzone.com, alex@lamzone.com, jordan@lamzone.com", R.color.purple_500),
            new Reunion(7, "Réunion G", 1648665625000L, "Security",
                    "marc@lamzone.com, michael@lamzone.com", R.color.teal_200),
            new Reunion(8, "Réunion H", 1648665625000L, "Furniture",
                    "anne@lamzone.com, sophie@lamzone.com, nicolas@lamzone.com", R.color.orange),
            new Reunion(9, "Réunion I", 1648665625000L, "Computers",
                    "malika@lamzone.com, luc@lamzone.com, antoine@lamzone.com", R.color.yellow),
            new Reunion(10,"Réunion J", 1648665625000L, "Lucas",
                    "maxime@lamzone.com, alex@lamzone.com, josiane@lamzone.com", R.color.purple_700)

    );

    static List<Reunion> generateReunions() {
        return new ArrayList<>(DUMMY_REUNIONS);
    }


}
