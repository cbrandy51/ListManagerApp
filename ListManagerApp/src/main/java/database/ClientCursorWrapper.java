package database;

import android.database.Cursor;
import android.database.CursorWrapper;
import a10593699.uvu.edu.cb_project7.Client;
import database.ClientDBSchema.ClientTable;

public class ClientCursorWrapper extends CursorWrapper{
    public ClientCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Client getClient(){
        int id = getInt(getColumnIndex(ClientTable.Columns.ID));
        String name = getString(getColumnIndex(ClientTable.Columns.C_NAME));
        String phone = getString(getColumnIndex(ClientTable.Columns.PHONE));
        String appointment = getString(getColumnIndex(ClientTable.Columns.APT_DATE));
        String type = getString(getColumnIndex(ClientTable.Columns.TYPE));
        String list = getString(getColumnIndex(ClientTable.Columns.LIST));

        Client client = new Client(id, name, phone, appointment, type, list);
        return client;
    }
}
