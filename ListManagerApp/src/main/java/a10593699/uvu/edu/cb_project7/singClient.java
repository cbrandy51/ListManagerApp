package a10593699.uvu.edu.cb_project7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import database.ClientCursorWrapper;
import database.ClientDBSchema.ClientTable;

class singClient {
    private static singClient sClients;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private int id;
    private int numList;
    private String[] lists;
    final int MAX = 12;

    public static singClient get(Context context){
        if (sClients == null)
            sClients = new singClient(context);

        return sClients;
    }

    private singClient(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new DBHelper(mContext).getWritableDatabase();
        id = 1;
        numList = 1;
        lists = new String[MAX];

        for(int i = 0; i < MAX; i++){
            lists[i] = null;
        }

        DBHelper.initialize(mDatabase, this);
    }

    /*public List<Client> getClients(){
        List<Client> clients = new ArrayList<>();

        ClientCursorWrapper cursor = queryClients(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                clients.add(cursor.getClient());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return clients;
    }*/

    public Client getClient(int id){
        ClientCursorWrapper cursor = queryClients("ID = ?", new String[] {"" + id});

        try {
            if(cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();
            return cursor.getClient();
        } finally {
            cursor.close();
        }
    }

    public void addClient(int id, String name, String phone, String appointment, String type, String list){
        Client client = new Client(id, name, phone, appointment, type, list);

        ContentValues values = getContentValues(client);

        mDatabase.insert(ClientTable.NAME, null, values);
    }

    public void updateClient(int id, String name, String phone, String appointment, String type, String list) {
        ContentValues values = new ContentValues();
        values.put(ClientTable.Columns.C_NAME, name);
        values.put(ClientTable.Columns.PHONE, phone);
        values.put(ClientTable.Columns.APT_DATE, appointment);
        values.put(ClientTable.Columns.TYPE, type);
        values.put(ClientTable.Columns.LIST, list);

        mDatabase.update(ClientTable.NAME, values, "ID = ?", new String[]{"" + id });
    }

    public int getID(){
        return (id++);
    }

    public void addListNumber(){
        numList++;
    }

    public void minusListNumber(){
        numList--;
    }

    public int getNumList(){
        return numList;
    }

    public void deleteClient(int id){
        mDatabase.delete(ClientTable.NAME, "ID = ?", new String[] {"" + id});
    }

    public List<Client> getData(String where, String what){
        List<Client> temp = new ArrayList<>();

        ClientCursorWrapper cursor = queryClients(where + " = ?", new String[] {"" + what});

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                temp.add(cursor.getClient());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return temp;
    }

    private static ContentValues getContentValues(Client client){
        ContentValues values = new ContentValues();
        values.put(ClientTable.Columns.ID, client.getId());
        values.put(ClientTable.Columns.C_NAME, client.getName());
        values.put(ClientTable.Columns.PHONE, client.getPhone());
        values.put(ClientTable.Columns.APT_DATE, client.getAppointment());
        values.put(ClientTable.Columns.TYPE, client.getType());
        values.put(ClientTable.Columns.LIST, client.getList_name());

        return values;
    }

    private ClientCursorWrapper queryClients(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(ClientTable.NAME, null, whereClause, whereArgs, null, null, null);

        return new ClientCursorWrapper(cursor);
    }

    public String getListName(int index){
        if(index > MAX)
            return null;
        else
            return lists[index];
    }

    public void addListName(int index, String name){
        if(index > MAX)
            return;
        else
            lists[index] = name;
    }

    public void deleteListName(int index, String name){
        if(index > MAX)
            return;
        else if (name.equals(lists[index]))
            lists[index] = null;
        else
            return;
    }
}