package server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Apartment {
    public static void main(String[] args) {
        List<ApartmentDay> apartments = new ArrayList<ApartmentDay>();

        apartments.add(new ApartmentDay(1, "A101", true));
        apartments.add(new ApartmentDay(2, "A101", true));
        apartments.add(new ApartmentDay(3, "A101", true));
        apartments.add(new ApartmentDay(4, "A101", true));

        apartments.add(new ApartmentDay(1, "A102", true));
        apartments.add(new ApartmentDay(2, "A102", false));
        apartments.add(new ApartmentDay(3, "A102", true));
        apartments.add(new ApartmentDay(4, "A102", true));

        apartments.add(new ApartmentDay(1, "A103", false));
        apartments.add(new ApartmentDay(2, "A103", true));
        apartments.add(new ApartmentDay(3, "A103", true));
        apartments.add(new ApartmentDay(4, "A103", true));

        apartments.add(new ApartmentDay(1, "A104", true));
        apartments.add(new ApartmentDay(2, "A104", true));
        apartments.add(new ApartmentDay(3, "A104", true));
        apartments.add(new ApartmentDay(4, "A104", false));

//        int row = 4;
//        int column = 4;
//        ApartmentDay[][] apartments1 = new ApartmentDay[4][4];
//        int index = 0;
//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < column; j++) {
//                apartments1[i][j] = apartments.get(index);
//                index++;
//            }
//        }
//
//
//
//        System.out.println();

    }


    /**
     * 获取全部房间名
     *
     * @param apartmentDays
     * @return
     */
    static List<String> roomNames(List<ApartmentDay> apartmentDays) {
        List<String> roomNames = new LinkedList<String>();
        for (ApartmentDay apartmentDay : apartmentDays) {
            if (!roomNames.contains(apartmentDay.room)) {
                roomNames.add(apartmentDay.room);
            }
        }
        return roomNames;
    }


    public static Selected getBestSelect(List<ApartmentDay> apartmentDays, int begin, int end) {
        List<Selected> selectedList = new LinkedList<Selected>();
        Selected selected;
        int maxSame = 0;
        List<String> roomName = roomNames(apartmentDays);
        for (String name : roomName) {
            List<ApartmentDay> apartmentDayList = getByName(apartmentDays, name);
            for (ApartmentDay apartmentDay : apartmentDayList) {
                int tempMax = 0;


            }
        }


        return null;
    }

    /**
     * 根据名字获取
     *
     * @param apartmentDays
     * @param name
     * @return
     */
    public static List<ApartmentDay> getByName(List<ApartmentDay> apartmentDays, String name) {
        List<ApartmentDay> apartmentDayList = new LinkedList<ApartmentDay>();
        for (ApartmentDay apartmentDay : apartmentDays) {
            if (apartmentDay.room.equals(name)) {
                apartmentDayList.add(apartmentDay);
            }
        }
        return apartmentDayList;
    }

    static List<ApartmentDay> getByDay(List<ApartmentDay> apartmentDays, int day) {
        List<ApartmentDay> apartmentDays1 = new LinkedList<ApartmentDay>();

        for (ApartmentDay apartmentDay : apartmentDays) {
            if (apartmentDay.date == day && apartmentDay.isFree) {
                apartmentDays1.add(apartmentDay);
            }
        }

        return apartmentDays1;
    }

    /**
     * 根据日期和房间名获取房间
     *
     * @param apartmentDays
     * @param roomName
     * @param day
     * @return
     */
    static ApartmentDay getApartmentByNameAndDay(List<ApartmentDay> apartmentDays, String roomName, int day) {
        for (ApartmentDay apartmentDay : apartmentDays) {
            if (apartmentDay.isFree && apartmentDay.date == day && apartmentDay.room.equals(roomName)) {
                return apartmentDay;
            }
        }

        return null;
    }


}

class Selected {
    int length = 0;
    List<ApartmentDay> apartmentDays;

    public Selected() {
        apartmentDays = new ArrayList<ApartmentDay>();
    }
}


class ApartmentDay {
    public ApartmentDay() {
    }

    public ApartmentDay(int date, String room, boolean isFree) {
        this.date = date;
        this.room = room;
        this.isFree = isFree;
    }

    int date;
    String room;
    boolean isFree;


}