package a10593699.uvu.edu.cb_project7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
    private Button mCreateButton;
    private Button mQueryButton;
    private Button mOptionsButton;
    private Button mBackButton;
    private Button mNextButton;
    private TextView mListName;
    private EditText mWhere;
    private EditText mWhat;
    private RecyclerView mRecycleView;
    private List<Client> lmClients;
    private RVAdapter adapter;
    singClient mSClient;
    final int MAX = 12;
    String where;
    String what;
    int index;
    int numLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        mRecycleView = (RecyclerView) findViewById(R.id.recycler_view);
        mCreateButton = (Button) findViewById(R.id.create_button);
        mQueryButton = (Button) findViewById(R.id.query_button);
        mOptionsButton = (Button) findViewById(R.id.options_button);
        mBackButton = (Button) findViewById(R.id.back_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mWhere = (EditText) findViewById(R.id.item5);
        mWhat = (EditText) findViewById(R.id.item6);
        mListName = (TextView) findViewById(R.id.list_name);
        mSClient = singClient.get(this);
        LinearLayoutManager llm = new LinearLayoutManager(RecyclerActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        index = 0;
        numLists = mSClient.getNumList();

        mListName.setText(mSClient.getListName(index));

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RecyclerActivity.this, DetailActivity.class);
                Bundle extras = new Bundle();

                extras.putBoolean("data", false);
                extras.putInt("index", index);
                i.putExtras(extras);

                startActivity(i);
            }
        });

        mQueryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                where = mWhere.getText().toString();
                what = mWhat.getText().toString();

                if(where.equals("Ex: TYPE") || what.equals("Ex: Meeting"))
                    return;

                List<Client> temp = mSClient.getData(where, what);
                adapter.setClients(temp);
                adapter.notifyDataSetChanged();
            }
        });

        mOptionsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(RecyclerActivity.this, OptionsActivity.class);
                Bundle extras = new Bundle();

                extras.putInt("index", index);
                extras.putInt("num", numLists);
                i.putExtras(extras);

                startActivity(i);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String temp;
                if(numLists <= 0)
                    return;

                do{
                    if(index == 0) {
                        index = (MAX - 1);
                        Log.i("CS 3680", "back " + index);
                    }
                    else{
                        index--;
                        Log.i("CS 3680", "back " + index);
                    }
                }while(mSClient.getListName(index) == null);

                temp = mSClient.getListName(index);
                mListName.setText(temp);
                lmClients = mSClient.getData("LIST", temp);

                adapter.setClients(lmClients);
                adapter.notifyDataSetChanged();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String temp;
                if (numLists <= 0)
                    return;

                do{
                    if(index == (MAX - 1)){
                        index = 0;
                        Log.i("CS 3680", "next " + index);
                    }
                    else{
                        index++;
                        Log.i("CS 3680", "next " + index);
                    }
                }while(mSClient.getListName(index) == null);

                temp = mSClient.getListName(index);
                mListName.setText(temp);
                lmClients = mSClient.getData("LIST", temp);

                adapter.setClients(lmClients);
                adapter.notifyDataSetChanged();
            }
        });

        mRecycleView.setLayoutManager(llm);
        mRecycleView.setHasFixedSize(true);

        lmClients = mSClient.getData("LIST", mSClient.getListName(index));

        adapter = new RVAdapter(lmClients);
        mRecycleView.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        Bundle extras;
        String temp;

        if((extras = getIntent().getExtras()) != null){
            if(extras.getBoolean("data")){
                if(extras.getBoolean("created")){
                    index = extras.getInt("index");
                    mSClient.addListNumber();
                    temp = mSClient.getListName(index);
                    mListName.setText(temp);
                    lmClients = mSClient.getData("LIST", temp);
                }
                else if(extras.getBoolean("deleted")){
                    mSClient.minusListNumber();
                    for(int i = 0; i < MAX; i++){
                        temp = mSClient.getListName(i);
                        if(temp != null){
                            mListName.setText(temp);
                            lmClients = mSClient.getData("LIST", temp);
                            break;
                        }
                    }
                }
            }
            else{
                temp = mSClient.getListName(index);
                mListName.setText(temp);
                lmClients = mSClient.getData("LIST", temp);
            }
        }

        adapter.setClients(lmClients);
        adapter.notifyDataSetChanged();
    }

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ClientViewHolder> {

        public  class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView client_name;
            public int id;

            ClientViewHolder(View itemView) {
                super(itemView);

                itemView.setOnClickListener(this);
                client_name = (TextView) itemView;
            }

            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecyclerActivity.this, DetailActivity.class);
                Bundle extras = new Bundle();

                Log.i("CS 3680", "id " + id);

                extras.putInt("id", id);
                extras.putBoolean("data", true);

                i.putExtras(extras);

                startActivity(i);
            }
        }

        private List<Client> RVAclients;

        RVAdapter(List<Client> lmClients) {
            this.RVAclients = lmClients;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView mRecyclerView) {
            super.onAttachedToRecyclerView(mRecyclerView);
        }

        @Override
        public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

            ClientViewHolder cvh = new ClientViewHolder(v);
            return cvh;
        }

        @Override
        public void onBindViewHolder(ClientViewHolder holder, int position) {
            Client c = RVAclients.get(position);
            holder.client_name.setText(c.name);
            holder.id = c.getId();
        }

        @Override
        public int getItemCount() {
            return RVAclients.size();
        }

        public void setClients(List<Client> clients){
            RVAclients = clients;
        }
    }
}

