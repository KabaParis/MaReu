package fr.kabaparis.mareu.ui.reunion_list;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import fr.kabaparis.mareu.R;
import fr.kabaparis.mareu.events.DeleteReunionEvent;
import fr.kabaparis.mareu.model.Reunion;
import fr.kabaparis.mareu.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyReunionRecyclerViewAdapter extends RecyclerView.Adapter<MyReunionRecyclerViewAdapter.ViewHolder> {

    private final List<Reunion> mReunionList;

    public MyReunionRecyclerViewAdapter(List<Reunion> items) {
        mReunionList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reunion, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Reunion reunion = mReunionList.get(position);
        String meetingTitle = holder.itemView.getContext().getString(R.string.meeting_title, reunion.getRoom_name(), reunion.getTime(), reunion.getSubject());
        holder.mRoomName.setText(meetingTitle);
            holder.mAttendeesName.setText(reunion.getAddress());
        ColorStateList colorStateList = AppCompatResources.getColorStateList(holder.itemView.getContext(), reunion.getRoom_colour());
        ImageViewCompat.setImageTintList(holder.mRoomColour, colorStateList);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReunionActivity.class);
                intent.putExtra("id", reunion.getId());

                v.getContext().startActivity(intent);

            }
        });


        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteReunionEvent(reunion));
            }

        });

    }
    public Reunion getReunion(int position) {
        return this.mReunionList.get(position);

    }

    @Override
    public int getItemCount() {
        return mReunionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mRoomColour;
        public TextView mRoomName;
        public TextView mAttendeesName;
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            mRoomColour = view.findViewById(R.id.room_colour);
            mRoomName = view.findViewById(R.id.room_name);
            mAttendeesName = view.findViewById(R.id.attendees_name);
            mDeleteButton = view.findViewById(R.id.room_list_delete_button);



        }


    }
}