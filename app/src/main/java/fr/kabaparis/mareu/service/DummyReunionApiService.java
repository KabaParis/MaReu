package fr.kabaparis.mareu.service;

import java.util.ArrayList;
import java.util.Calendar;
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


    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public ArrayList<Reunion> getReunionFilteredByRoom(String room) {

        // Creation of new ArrayList
        ArrayList<Reunion> result = new ArrayList<>();


        // Go through the list of meetings and get the room if the name matches
        for (int i = 0; i < reunions.size(); i++) {

            String reunionNameAtIndex = reunions.get(i).getRoom_name();

            boolean sameRoom = reunionNameAtIndex.equals(room);
            if (sameRoom)
                result.add(reunions.get(i));
        }

        return result;

    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public ArrayList<Reunion> getReunionFilteredByDate(int year, int month, int day) {

        //     timestamp = new Timestamp(Calendar.getInstance();

        // Creation of new ArrayList
        ArrayList<Reunion> result = new ArrayList<>();


        // Go through the list of meetings and get the room if the date matches
        for (int i = 0; i < reunions.size(); i++) {

            long date = reunions.get(i).getTimestamp();
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.setTimeInMillis(date);

            if (year == newCalendar.get(Calendar.YEAR) && month == newCalendar.get(Calendar.MONTH)
                    && day == newCalendar.get(Calendar.DAY_OF_MONTH))
                result.add(reunions.get(i));
        }

        return result;
    }



    /**
     * {@inheritDoc}
     *
     * @param year, month, day
     */
    @Override
    // Make sure to avoid overlapping same meeting in the same room at the same date and time
    public boolean getOverlappingReunions(int year, int month, int day) {

        // Creation of new ArrayList
        ArrayList<Reunion> result = new ArrayList<>();
        // Go through the list of meetings and get the matching room names and timestamps
        for (int i = 0; i < reunions.size(); i++) {

            String reunionNameAtIndex = reunions.get(i).getRoom_name();
            boolean sameRoom = reunionNameAtIndex.equals(reunionNameAtIndex);

            long date = reunions.get(i).getTimestamp();
    //        long time = reunions.get(i).getTimestamp();
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.setTimeInMillis(date);
    //        newCalendar.setTimeInMillis(time);

            //3000(milliseconds in a second)*60(seconds in a minute)*45(number of minutes)=8100000
   /*         if (sameRoom && Math.abs(year - newCalendar.get(Calendar.YEAR) + month - newCalendar.get(Calendar.MONTH) +
                    day - newCalendar.get(Calendar.DAY_OF_MONTH) + hour - newCalendar.get(Calendar.HOUR) +
                    minute - newCalendar.get(Calendar.MINUTE)) > 8100000)
                // if timestamp is not within 45 minutes of new timestamp in same room
                result.add(reunions.get(i));
*/
            if (sameRoom && Math.abs(year - newCalendar.get(Calendar.YEAR) + month - newCalendar.get(Calendar.MONTH) +
                    day - newCalendar.get(Calendar.DAY_OF_MONTH)) > 8100000)
                // if timestamp is not within 45 minutes of new timestamp in same room
                result.add(reunions.get(i));

        }
        return true;
    }

}

