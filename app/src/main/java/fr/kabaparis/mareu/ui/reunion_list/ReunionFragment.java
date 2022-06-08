package fr.kabaparis.mareu.ui.reunion_list;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import fr.kabaparis.mareu.R;
import fr.kabaparis.mareu.events.DeleteReunionEvent;
import fr.kabaparis.mareu.model.Reunion;
import fr.kabaparis.mareu.placeholder.PlaceholderContent;
import fr.kabaparis.mareu.service.ReunionApiService;

/**
 * A fragment representing a list of Items.
 */
public class ReunionFragment extends Fragment {

    private ReunionApiService mApiService;
    private List<Reunion> mReunions;
    private RecyclerView mRecyclerView;


    /**
     * Create and return a new instance
     *
     * @return @{@link ReunionFragment}
     */
    public static ReunionFragment newInstance() {
        ReunionFragment fragment = new ReunionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getReunionApiService();
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filterRoom) {
            AtomicInteger selectedRoom = new AtomicInteger();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setSingleChoiceItems(R.array.rooms, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
            selectedRoom.set(i);
                }
            });
            builder.setPositiveButton(R.string.filter_room, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Room selection
                    String currentRoom = getResources().getStringArray(R.array.rooms)[selectedRoom.get()];
                    Log.d("currentRoom" , currentRoom);

                    mReunions = mApiService.getReunionFilteredByRoom(currentRoom);
                    updateAdapter();
                }
            });
                    // Cancel of selection
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            builder.show();


            // Create the AlertDialog object and return it
            return true;
        }

        if (item.getItemId() == R.id.filterDate) {

            Calendar currentDate = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),  new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mReunions = mApiService.getReunionFilteredByDate(i, i1, i2);
                updateAdapter();
                }
            },currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH)
                    );

            datePickerDialog.show();

        }
            if (item.getItemId() == R.id.noFilter) {
            initList();
            }

            return true;
        }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reunion_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;

    }

    /**
     * Init the List of reunions
     */

    private void initList() {
        mReunions = mApiService.getReunions();
        updateAdapter();
    }

    private void updateAdapter() {
        mRecyclerView.setAdapter(new MyReunionRecyclerViewAdapter(mReunions));
    }


    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteReunion(DeleteReunionEvent event) {
        mApiService.deleteReunion(event.reunion);
        initList();
    }
}