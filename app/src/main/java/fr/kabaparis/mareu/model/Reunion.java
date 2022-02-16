package fr.kabaparis.mareu.model;

import androidx.annotation.ColorRes;

import java.util.Objects;

/**
 * Model object representing a Reunion
 */
public class Reunion {

    /** Identifier */
    private long id;

    /** Reunion room name */
    private String room_name;

    /** Reunion time */
    private String time;

    /** Reunion subject */
    private String subject;

    /** Reunion attendees email address */
    private String address;

    /** Reunion Room Colour */
    @ColorRes
    private int room_colour;


    /**
     * Constructor
     * @param id
     * @param room_name
     * @param time
     * @param subject
     * @param address
     * @param room_colour
     */

    public Reunion(int id, String room_name, String time, String subject, String address, @ColorRes int room_colour) {

        this.id = id;
        this.room_name = room_name;
        this.time = time;
        this.subject = subject;
        this.address = address;
        this.room_colour = room_colour;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRoom_colour() {
        return room_colour;
    }

    public void setRoom_colour(int room_colour) { this.room_colour = room_colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reunion reunion = (Reunion) o;
        return Objects.equals(id, reunion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
