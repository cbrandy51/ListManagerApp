package a10593699.uvu.edu.cb_project7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    private Button mBackButton;
    private Button mSaveButton;
    private Button mDeleteButton;
    private EditText mName;
    private EditText mPhone;
    private EditText mApt;
    private EditText mType;
    private EditText mList;
    private Client dmClient;
    private int id = 0;
    String name;
    String phone;
    String appointment;
    String type;
    String list;
    int listIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final singClient mSClient = singClient.get(this);
        final Bundle extras = getIntent().getExtras();

        mBackButton = (Button) findViewById(R.id.back_button);
        mSaveButton = (Button) findViewById(R.id.save_button);
        mDeleteButton = (Button) findViewById(R.id.delete_button);
        mApt = (EditText) findViewById(R.id.item3);
        mPhone = (EditText) findViewById(R.id.item2);
        mName = (EditText) findViewById(R.id.item1);
        mType = (EditText) findViewById(R.id.item4);
        mList = (EditText) findViewById(R.id.item5);

        mBackButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(DetailActivity.this, RecyclerActivity.class);
                Bundle extras = new Bundle();

                extras.putBoolean("data", false);
                i.putExtras(extras);

                startActivity(i);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (mSClient.getClient(id) == null){
                    name = mName.getText().toString();
                    phone = mPhone.getText().toString();
                    appointment = mApt.getText().toString();
                    type = mType.getText().toString();
                    list = mList.getText().toString();

                    mSClient.addClient(mSClient.getID(), name, phone, appointment, type, list);

                    Toast.makeText(DetailActivity.this, "Client was created.", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(DetailActivity.this, RecyclerActivity.class);
                    Bundle extras = new Bundle();

                    extras.putBoolean("data", false);
                    i.putExtras(extras);

                    startActivity(i);
                }
                else{
                    name = mName.getText().toString();
                    phone = mPhone.getText().toString();
                    appointment = mApt.getText().toString();
                    type = mType.getText().toString();
                    list = mList.getText().toString();

                    mSClient.updateClient(id, name, phone, appointment, type, list);

                    Toast.makeText(DetailActivity.this, "Client's Information was saved.", Toast.LENGTH_LONG).show();
                }
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (mSClient.getClient(id) == null){
                    Toast.makeText(DetailActivity.this, "Error finding Client by ID.", Toast.LENGTH_LONG).show();
                }
                else{
                    mSClient.deleteClient(id);

                    Toast.makeText(DetailActivity.this, "Client was deleted.", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(DetailActivity.this, RecyclerActivity.class);
                    Bundle extras = new Bundle();

                    extras.putBoolean("data", false);
                    i.putExtras(extras);

                    startActivity(i);
                }
            }
        });

        if (extras.getBoolean("data")){
            id = extras.getInt("id");

            if (mSClient.getClient(id) == null){
                Toast.makeText(DetailActivity.this, "Error finding Client by ID.", Toast.LENGTH_LONG).show();
            }
            else{
                dmClient = mSClient.getClient(id);

                mName.setText(dmClient.name);
                mApt.setText(dmClient.appointment);
                mPhone.setText(dmClient.phone);
                mType.setText(dmClient.type);
                mList.setText(dmClient.list_name);
            }
        }
        else{
            listIndex = extras.getInt("index");

            mList.setText(mSClient.getListName(listIndex));
        }
    }
}
