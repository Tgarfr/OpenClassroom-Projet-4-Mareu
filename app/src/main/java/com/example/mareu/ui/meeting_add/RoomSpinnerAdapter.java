package com.example.mareu.ui.meeting_add;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mareu.R;
import com.example.mareu.model.Room;
import com.example.mareu.repository.RoomRepository;

public class RoomSpinnerAdapter extends BaseAdapter {

    RoomRepository roomRepository;

    public RoomSpinnerAdapter(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public int getCount() {
        return roomRepository.countRoom();
    }

    @Override
    public Room getItem(int i) {
        return roomRepository.getRoomsList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return roomRepository.getRoomsList().get(i).getId();
    }

    @Override
    public View getView(int i, View rowView, ViewGroup viewGroup) {
        Room room = this.getItem(i);

        if (rowView == null) {
            rowView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_room_spinner_add_meeting, viewGroup, false);
        }

        TextView roomNameItemLayout = rowView.findViewById(R.id.spinner_item_room_name);
        roomNameItemLayout.setText(room.getName());

        return rowView;
    }
}
