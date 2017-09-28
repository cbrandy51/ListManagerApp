package a10593699.uvu.edu.cb_project7;

public class Client {
    String name;
    String phone;
    String appointment;
    String type;
    String list_name;
    int id;

    public Client(int id, String name, String phone, String appointment, String type, String list){
        this.name = name;
        this.phone = phone;
        this.appointment = appointment;
        this.type = type;
        this.list_name = list;
        this.id = id;
    }

    int getId(){
        return id;
    }

    String getName(){
        return name;
    }

    String getPhone(){
        return phone;
    }

    String getAppointment(){
        return appointment;
    }

    String getType(){
        return type;
    }

    String getList_name() {
        return list_name;
    }
}
