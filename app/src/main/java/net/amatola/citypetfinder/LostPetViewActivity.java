package net.amatola.citypetfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LostPetViewActivity extends Activity {

    private final String mUrl = "http://citypetfinderwebapi.azurewebsites.net/api/LostPet";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_pet_view);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);

        Intent intent = getIntent();
        int lostPetId = intent.getIntExtra(LostPetListActivity.LOST_PET_ID, 0);
        refresh(lostPetId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lost_pet_view, menu);
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

    private void refresh(int lostPetId) {

        mProgressBar.setVisibility(View.VISIBLE);

        // Creating volley request obj
        JsonObjectRequest request = new JsonObjectRequest(mUrl + "/" + String.valueOf(lostPetId),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        //hidePDialog();

                        // Parsing json
                        try {

                            TextView petName = (TextView) findViewById(R.id.petName);
                            TextView petBreed = (TextView) findViewById(R.id.petBreed);

                            petName.setText(response.getString("PetName"));
                            petBreed.setText(response.getString("Breed"));

                            /* todo use Gson
                            LostPet lostPet = new LostPet();
                            lostPet.setImageThumbnailUrl(response.getString("ImageThumbnailUrl"));
                            lostPet.setPetName(response.getString("PetName"));
                            lostPet.setBreed(response.getString("Breed"));*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mProgressBar.setVisibility(View.INVISIBLE);
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
