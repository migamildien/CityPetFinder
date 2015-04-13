package net.amatola.citypetfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class LostPetActivity extends Activity {

    private final int MY_LOST_PETS = 0;
    private final int ADD_LOST_PET = 1;
    private final int SEARCH_LOST_PETS = 2;
    private final int RAO_CONTACT_LIST = 3;

    AdapterView.OnItemClickListener myItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = null;
            int index = (int)id;

            switch (index) {
                case MY_LOST_PETS:
                    intent = new Intent(LostPetActivity.this, LostPetListActivity.class);
                    startActivity(intent);
                    break;
                case ADD_LOST_PET:
                    intent = new Intent(LostPetActivity.this, LostPetAddActivity.class);
                    startActivity(intent);
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_pet);

        final ArrayList<String> list = new ArrayList<String>();

        list.add("My Lost Pets");
        list.add("Add a lost pet");
        list.add("Search Lost Pet");
        list.add("RAO Contact List");

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        ListView listView = (ListView)findViewById(R.id.listView2);
        listView.setAdapter(a);
        listView.setOnItemClickListener(myItemClickListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lost_pet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
