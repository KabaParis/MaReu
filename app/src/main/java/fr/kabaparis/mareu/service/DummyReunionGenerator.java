package fr.kabaparis.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.kabaparis.mareu.R;
import fr.kabaparis.mareu.model.Reunion;

public abstract class DummyReunionGenerator {

    public static List<Reunion> DUMMY_REUNIONS = Arrays.asList(
            new Reunion(1, "Réunion A", "14h00", "Peach",
                    "maxime@lamzone.com, alex@lamzone.com, nicolas@lamzone.com", R.color.light_blue),
            new Reunion(2, "Réunion B", "16h00", "Mario",
                    "paul@lamzone.com, viviane@lamzone.com, sylvain@lamzone.com", R.color.purple_200),
            new Reunion(3, "Réunion C", "19h00", "Luigi",
                    "amandine@lamzone.com, luc@lamzone.com", R.color.teal_700),
            new Reunion(4, "Réunion D", "11h00", "Kaba",
                    "Stephanie@lamzone.com, pierre@lamzone.com, julie@lamzone.com", R.color.black),
            new Reunion(5, "Réunion E", "10h00", "Pear",
                    "lucas@lamzone.com, alex@lamzone.com, jean@lamzone.com", R.color.red),
            new Reunion(6, "Réunion F", "15h00", "Office",
                    "denise@lamzone.com, alex@lamzone.com, jordan@lamzone.com", R.color.purple_500),
            new Reunion(7, "Réunion G", "09h30", "Security",
                    "marc@lamzone.com, michael@lamzone.com", R.color.teal_200),
            new Reunion(8, "Réunion H", "17h00", "Furniture",
                    "anne@lamzone.com, sophie@lamzone.com, nicolas@lamzone.com", R.color.orange),
            new Reunion(9, "Réunion I", "16h00", "Computers",
                    "malika@lamzone.com, luc@lamzone.com, antoine@lamzone.com", R.color.purple_700),
            new Reunion(10,"Réunion J", "13h00", "Lucas",
                    "maxime@lamzone.com, alex@lamzone.com, josiane@lamzone.com", R.color.white)

    );

    static List<Reunion> generateReunions() {
        return new ArrayList<>(DUMMY_REUNIONS);
    }


}
