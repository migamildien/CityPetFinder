package net.amatola.citypetfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LostPetAddActivity extends Activity {

    private final static String mUrl = "http://citypetfinderwebapi.azurewebsites.net/api/LostPet";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_pet_add);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lost_pet_add, menu);
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
            case R.id.action_save:
                save();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void save() {

        mProgressBar.setVisibility(View.VISIBLE);

        EditText petName = (EditText)findViewById(R.id.petName);
        EditText petType = (EditText)findViewById(R.id.petType);
        EditText gender = (EditText)findViewById(R.id.gender);
        EditText breed = (EditText)findViewById(R.id.breed);
        EditText primaryColour = (EditText)findViewById(R.id.primaryColour);

        JSONObject jsonObject = new JSONObject();

        try {
            //jsonObject.put("LostPetId", JSONObject.NULL);
            jsonObject.put("PetName", petName.getText().toString());
            jsonObject.put("PetType", petType.getText().toString());
            jsonObject.put("Gender", gender.getText().toString());
            jsonObject.put("Breed", breed.getText().toString());
            //jsonObject.put("CrossBreed", JSONObject.NULL);
            //jsonObject.put("Size", JSONObject.NULL);
            jsonObject.put("PrimaryColour", primaryColour.getText().toString());
            //jsonObject.put("SecondaryColour", JSONObject.NULL);
            //jsonObject.put("MicrochipNumber", JSONObject.NULL);
            //jsonObject.put("MissingSinceDate", JSONObject.NULL);
            //jsonObject.put("SuburbLost", JSONObject.NULL);
            //jsonObject.put("OtherDescription", JSONObject.NULL);
            //jsonObject.put("ImageThumbnailUrl", JSONObject.NULL);
            //jsonObject.put("ImageUrl", JSONObject.NULL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Creating volley request obj
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, mUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        //hidePDialog();

                        Toast.makeText(getApplicationContext(), "Lost pet saved", Toast.LENGTH_SHORT).show();

                        mProgressBar.setVisibility(View.INVISIBLE);

                        Intent intent = new Intent(LostPetAddActivity.this, LostPetListActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hidePDialog();
                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();

                mProgressBar.setVisibility(View.INVISIBLE);

            }
        })


        {
            /*
            @Override
            protected Map<String, String> getParams()
            {
                final EditText editText3 = (EditText) findViewById(R.id.editText3);

                Map<String, String> params = new HashMap<String, String>();
                params.put("XML", editText3.getText().toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
            return params;
        }*/
        };

        AppController.getInstance().addToRequestQueue(request);
    }
}
