package a10593699.uvu.edu.cb_project7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class OptionsActivity extends AppCompatActivity {
    private Button mDeleteList;
    private Button mCreateList;
    private Button mBackButton;
    private EditText mListName;
    final int MAX = 12;
    int index;
    int numLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        final singClient mSClient = singClient.get(this);
        final Bundle extras = getIntent().getExtras();

        mCreateList = (Button) findViewById(R.id.create_list_button);
        mDeleteList = (Button) findViewById(R.id.delete_list_button);
        mBackButton = (Button) findViewById(R.id.back_button2);
        mListName = (EditText) findViewById(R.id.item7);
        index = extras.getInt("index");
        numLists = extras.getInt("num");

        mListName.setText(mSClient.getListName(index));

        mBackButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(OptionsActivity.this, RecyclerActivity.class);
                Bundle extras = new Bundle();

                extras.putBoolean("data", false);
                i.putExtras(extras);

                startActivity(i);
            }
        });

        mCreateList.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String temp = mListName.getText().toString();
                boolean alreadyExists = false;
                int tempIndex = -1;

                for (int i = 0; i < MAX; i++){
                    if(temp.equals(mSClient.getListName(i))) {
                        alreadyExists = true;
                        break;
                    }
                }

                if(alreadyExists){
                    Toast.makeText(OptionsActivity.this, "A List Under This Name Already Exists.", Toast.LENGTH_LONG).show();
                }
                else if(!(numLists < MAX)){
                    Toast.makeText(OptionsActivity.this, "Max List Capacity Reached.", Toast.LENGTH_LONG).show();
                }
                else{
                    for(int i = 0; i < MAX; i++){
                        if(mSClient.getListName(i) == null){
                            tempIndex = i;
                            break;
                        }
                    }
                    mSClient.addListName(tempIndex, temp);

                    Intent i = new Intent(OptionsActivity.this, RecyclerActivity.class);
                    Bundle extras = new Bundle();

                    extras.putBoolean("data", true);
                    extras.putBoolean("created", true);
                    extras.putBoolean("deleted", false);
                    extras.putInt("index", tempIndex);
                    i.putExtras(extras);

                    startActivity(i);
                }
            }
        });

        mDeleteList.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String temp = mListName.getText().toString();
                boolean alreadyExists = false;
                int tempIndex = -1;

                for (int i = 0; i < MAX; i++){
                    if(temp.equals(mSClient.getListName(i))) {
                        alreadyExists = true;
                        tempIndex = i;
                        break;
                    }
                }

                if(!alreadyExists){
                    Toast.makeText(OptionsActivity.this, "A List Under This Name Doesn't Exist.", Toast.LENGTH_LONG).show();
                }
                else{
                    String what = mListName.getText().toString();
                    List<Client> tempList = mSClient.getData("LIST", what);
                    Client tempClient;

                    for(int i = 0; i < tempList.size(); i++){
                        tempClient = tempList.get(i);

                        mSClient.deleteClient(tempClient.getId());
                    }

                    mSClient.deleteListName(index, temp);

                    Intent i = new Intent(OptionsActivity.this, RecyclerActivity.class);
                    Bundle extras = new Bundle();

                    extras.putBoolean("data", true);
                    extras.putBoolean("created", false);
                    extras.putBoolean("deleted", true);
                    i.putExtras(extras);

                    startActivity(i);
                }
            }
        });
    }
}
