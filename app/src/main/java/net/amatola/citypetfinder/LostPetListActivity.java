package net.amatola.citypetfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LostPetListActivity extends Activity {

    public final static String LOST_PET_ID = "net.amatola.citypetfinder.LOST_PET_ID";

    AdapterView.OnItemClickListener myItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(LostPetListActivity.this, LostPetViewActivity.class);
            intent.putExtra(LOST_PET_ID, (int)id);
            startActivity(intent);

        }
    };

    private final String mUrl = "http://citypetfinderwebapi.azurewebsites.net/api/LostPet";
    private List<LostPet> mLostPetList = new ArrayList<LostPet>();
    private ListView listView;
    private CustomListAdapter mAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_pet_list);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        listView = (ListView) findViewById(R.id.listView3);
        mAdapter = new CustomListAdapter(this, mLostPetList);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(myItemClickListener);

        refresh();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lost_pet_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        switch (id) {
            case R.id.action_refresh:
                refresh();
                return true;
            case R.id.action_new:
                Intent intent = new Intent(LostPetListActivity.this, LostPetAddActivity.class);
                startActivity(intent);
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refresh() {

        mProgressBar.setVisibility(View.VISIBLE);

        // Creating volley request obj
        JsonArrayRequest request = new JsonArrayRequest(mUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d(TAG, response.toString());
                        //hidePDialog();

                        mLostPetList.clear();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                LostPet lostPet = new LostPet();
                                lostPet.setLostPetId(obj.getInt("LostPetId"));
                                lostPet.setImageThumbnailUrl(obj.getString("ImageThumbnailUrl"));
                                lostPet.setPetName(obj.getString("PetName"));
                                lostPet.setBreed(obj.getString("Breed"));

                                mLostPetList.add(lostPet);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        mProgressBar.setVisibility(View.INVISIBLE);

                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hidePDialog();
                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();

                mProgressBar.setVisibility(View.INVISIBLE);

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

}
