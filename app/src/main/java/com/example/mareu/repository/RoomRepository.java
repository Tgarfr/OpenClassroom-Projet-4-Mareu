package com.example.mareu.repository;

import com.example.mareu.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private List<Room> roomList;

    private RoomRepository() {
        roomList = new ArrayList<Room>();
        this.addRoom();
    }

    private static final RoomRepository INSTANCE = new RoomRepository();

    public static RoomRepository getInstance() {
        return INSTANCE;
    }

    private void addRoom() {
        roomList.add(new Room(0, "Salle 1"));
        roomList.add(new Room(1, "Salle 2"));
        roomList.add(new Room(2, "Salle 3"));
        roomList.add(new Room(3, "Salle 4"));
        roomList.add(new Room(4, "Salle 5"));
        roomList.add(new Room(5, "Salle 6"));
        roomList.add(new Room(6, "Salle 7"));
        roomList.add(new Room(7, "Salle 8"));
        roomList.add(new Room(8, "Salle 9"));
        roomList.add(new Room(9, "Salle 10"));
    }

    public List<Room> getRoomsList() {
        return roomList;
    }

    public int countRoom() {
        return roomList.size();
    }
}