package edu.ucu.cite.jobportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class interested extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    TextView TextViewInterestedData;
    TextView TextViewEventDetailData, TextViewEventDateData, TextViewdatedurationdata,TextViewAddressData,TextViewVenueData,
            TextViewDescriptionData,TextViewSponsorData,TextViewOrganizerData;
    ImageView ImageVieweventimagedata;
    ProgressDialog progressDialog;
    String eventid,StringInterestedPlain,StringNotInterestedPlain,StringInterestedSelected,StringNotInterestedSelected;
    Date DateUploaded,DateStartDate , DateEndDate,qEndDate,qCurrentDate;
    String StringStartDate,StringEndDate,StartTime,EndTime,StringqEndDate,StringqCurrentDate;
    LinearLayout linearLayoutdatevalidation;
    Button ButtonInterestedPlain,ButtonNotInterestedPlain,ButtonInterestedSelected,ButtonNotInterestedSelected;
    String idno,Submitinterested,Submitnotinterested;
    DrawerLayout mydrawer;
    NavigationView navigationView;
    ActionBarDrawerToggle mtoggle=null,mytoggle=null;
    androidx.appcompat.widget.Toolbar toolbar;
    TextView TextViewTitleNav,TextViewNavFullname, TextViewNavIdno;
    ImageView ImageViewNavProfile;
    Button btnChoose, btnUpload;
    ImageView imageUpload ,ImageProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested);
        TextViewTitleNav = findViewById(R.id.titlenav);
        TextViewTitleNav.setText("Event Information");

        ImageViewNavProfile = findViewById(R.id.navprofile);
        TextViewNavFullname = findViewById(R.id.navfullname);
        TextViewNavIdno = (TextView)  findViewById(R.id.navidno);
        ImageProfile = findViewById(R.id.imageUpload);
        String firstname = SharedPrefManager.getInstance(this).getFirstname();
        String middlename = SharedPrefManager.getInstance(this).getMiddlename();
        String lastname = SharedPrefManager.getInstance(this).getLastname();
        idno = SharedPrefManager.getInstance(this).getIDno();
        String graduatedimage = SharedPrefManager.getInstance(this).getGraduatedimage();


//        Glide.with(interested.this).load(graduatedimage).into(ImageProfile);
        Glide.with(interested.this).load(graduatedimage).into(ImageViewNavProfile);
        TextViewNavFullname.setText(firstname + " " + middlename + " " + lastname);
        TextViewNavIdno.setText(idno);
        mydrawer = findViewById(R.id.mydrawer);
        navigationView = findViewById(R.id.navigationdrawer);

        toolbar=findViewById(R.id.sidetoolbar);
        mytoggle = new ActionBarDrawerToggle(this,mydrawer, toolbar, R.string.open, R.string.close);

        mydrawer.addDrawerListener(mytoggle);
        navigationView.bringToFront();
        mytoggle.syncState();

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        TextViewEventDetailData = findViewById(R.id.eventdetaildata);
        TextViewEventDateData = findViewById(R.id.eventdatedata);
        TextViewdatedurationdata = findViewById(R.id.datedurationdata);
        TextViewAddressData = findViewById(R.id.addressdata);
        TextViewVenueData = findViewById(R.id.venuedata);
        TextViewDescriptionData = findViewById(R.id.descriptiondata);
        TextViewSponsorData = findViewById(R.id.sponsordata);
        TextViewOrganizerData = findViewById(R.id.organizerdata);
        ButtonInterestedPlain = findViewById(R.id.interestedplain);
        ButtonNotInterestedPlain = findViewById(R.id.notinterestedplain);
        ButtonInterestedSelected = findViewById(R.id.interestedselected);
        ButtonNotInterestedSelected = findViewById(R.id.notinterestedselected);



        TextViewEventDetailData.setText(getIntent().getStringExtra("eventdetail"));


        String StringAddress = "<b> Address: </b>" + getIntent().getStringExtra("eventaddress");
        TextViewAddressData.setText(HtmlCompat.fromHtml(StringAddress, HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH));

        String StringVenue = "<b> Venue: </b>" +  getIntent().getStringExtra("eventvenue");
        TextViewVenueData.setText(HtmlCompat.fromHtml(StringVenue, HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH));


        TextViewDescriptionData.setText(getIntent().getStringExtra("eventdescription"));
        ImageVieweventimagedata = findViewById(R.id.eventimagedata);
        String image = getIntent().getStringExtra("eventimage");
        Glide.with(interested.this).load(image).into(ImageVieweventimagedata);

        //event date uploaded
        String date = getIntent().getStringExtra("eventdate");
        SimpleDateFormat input = new SimpleDateFormat("yy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy");
        try {
            DateUploaded = input.parse(date);                 // parse input
            TextViewEventDateData.setText(output.format(DateUploaded));    // format output
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //duration event
        //startdate
        String date1 = getIntent().getStringExtra("eventstartdate");
        SimpleDateFormat input1 = new SimpleDateFormat("yy-MM-dd");
        SimpleDateFormat output1 = new SimpleDateFormat("dd MMM yyyy");
        try {
            DateStartDate = input.parse(date1);
            StringStartDate = output.format(DateStartDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //enddate
        String date2 = getIntent().getStringExtra("eventenddate");
        SimpleDateFormat input2 = new SimpleDateFormat("yy-MM-dd");
        SimpleDateFormat output2 = new SimpleDateFormat("dd MMM yyyy");
        try {
            DateEndDate = input.parse(date2);
            StringEndDate = output.format(DateEndDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //time
        String dateString3 = getIntent().getStringExtra("eventstarttime");
        String dateString4 = getIntent().getStringExtra("eventendtime");
        //old format
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try{
            Date date3 = sdf.parse(dateString3);
            Date date4 = sdf.parse(dateString4);
            //new format
            SimpleDateFormat sdf3 = new SimpleDateFormat("hh:mm aa");
            SimpleDateFormat sdf4 = new SimpleDateFormat("hh:mm aa");
            //formatting the given time to new format with AM/PM
            StartTime = sdf3.format(date3);
            EndTime = sdf4.format(date4);
        }catch(ParseException e){
            e.printStackTrace();
        }

        //uploading date time
        String StringDateeDuration = "<b>Date: </b>" + StringStartDate + " to " + StringEndDate + "<br>"
                + "<b>Time: </b>" + StartTime + " to " + EndTime;

        TextViewdatedurationdata.setText(HtmlCompat.fromHtml(StringDateeDuration, HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH));


        //are you interested
        String date3 = getIntent().getStringExtra("eventenddate");
        SimpleDateFormat input3 = new SimpleDateFormat("yy-MM-dd");
        SimpleDateFormat output3 = new SimpleDateFormat("yMMdd");
        try {
            qEndDate = input.parse(date3);
            StringqEndDate = output3.format(qEndDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        linearLayoutdatevalidation = findViewById(R.id.datevalidationdata);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yMMdd");
        StringqCurrentDate = sdf1.format(c.getTime());
        int IntegerCurrentDate = Integer.parseInt(StringqCurrentDate);
        int IntegerEnddate = Integer.parseInt(StringqEndDate);
        if (IntegerEnddate > IntegerCurrentDate){
            linearLayoutdatevalidation.setVisibility(LinearLayout.VISIBLE);
        }

        //Sponsor Organizer
        String StringSponsor = getIntent().getStringExtra("eventsponsor");
        String splitted[] = StringSponsor.split(",,,");
        String StringAllSponsor = "<b>Sponsor</b><br>";
        for(int i =0; i<splitted.length; i++){
            StringAllSponsor +=   splitted[i] + "<br>";
        }

        String StringOrganizer = getIntent().getStringExtra("eventorganizer");
        String splitted1[] = StringOrganizer.split(",,,");
        String StringAllOrganizer = "<b>Organizer</b><br>";
        for(int i =0; i<splitted1.length; i++){
            StringAllOrganizer +=   splitted1[i] + "<br>";
        }

        TextViewSponsorData.setText(HtmlCompat.fromHtml(StringAllSponsor, HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH));
        TextViewOrganizerData.setText(HtmlCompat.fromHtml(StringAllOrganizer, HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH));

        //are you interested
        idno = SharedPrefManager.getInstance(this).getIDno();
        //interested selected
        StringInterestedSelected = "";
        String StringInterestedChecking = getIntent().getStringExtra("eventinterested");
        if (!StringInterestedChecking.isEmpty()) {
            String StringLooping1 = getIntent().getStringExtra("eventinterested");
            String StringLooping1splitted1[] = StringLooping1.split(",");

            for (int i = 0; i < StringLooping1splitted1.length; i++) {
                if (StringLooping1splitted1[i].equals(idno)) {
                    StringInterestedSelected = "Yes";
                    ButtonInterestedSelected.setVisibility(LinearLayout.VISIBLE);
                    ButtonNotInterestedPlain.setVisibility(LinearLayout.VISIBLE);
                }
            }
        }

        //Notinterested selected
        StringNotInterestedSelected = "";
        String StringNotInterestedChecking = getIntent().getStringExtra("eventnotinterested");
        if (!StringNotInterestedChecking.isEmpty()) {
            String StringLooping2 = getIntent().getStringExtra("eventnotinterested");
            String StringLooping1splitted2[] = StringLooping2.split(",");

            for (int i = 0; i < StringLooping1splitted2.length; i++) {
                if (StringLooping1splitted2[i].equals(idno)) {
                    StringNotInterestedSelected = "No";
                    ButtonNotInterestedSelected.setVisibility(LinearLayout.VISIBLE);
                    ButtonInterestedPlain.setVisibility(LinearLayout.VISIBLE);
                }
            }
        }
        if (StringInterestedSelected.isEmpty() && StringNotInterestedSelected.isEmpty()){
            ButtonInterestedPlain.setVisibility(LinearLayout.VISIBLE);
            ButtonNotInterestedPlain.setVisibility(LinearLayout.VISIBLE);

        }



    }








    public void SubmitInterested(View view) {
        progressDialog = new ProgressDialog(interested.this);
        progressDialog.setMessage("Please wait....");
        String eventid = getIntent().getStringExtra("eventid");
        String interested = getIntent().getStringExtra("eventinterested");
        String notinterested = getIntent().getStringExtra("eventnotinterested");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_INTERESTED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getApplicationContext(), eventinfo.class));
                            finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText",response);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idno",idno);
                params.put("eventid",eventid);
                params.put("interested",interested);
                params.put("notinterested",notinterested);



                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void SubmitNotInterested(View view) {
        progressDialog = new ProgressDialog(interested.this);
        progressDialog.setMessage("Please wait....");
        String eventid = getIntent().getStringExtra("eventid");
        String interested = getIntent().getStringExtra("eventinterested");
        String notinterested = getIntent().getStringExtra("eventnotinterested");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_NOTINTERESTED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getApplicationContext(), eventinfo.class));
                            finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText",response);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idno",idno);
                params.put("eventid",eventid);
                params.put("interested",interested);
                params.put("notinterested",notinterested);



                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.mydrawer);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (mytoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.profile:
                Intent intent1 = new Intent(interested.this,profile.class);
                startActivity(intent1);
                break;
            case R.id.jobhiring:
                Intent intent2 = new Intent(interested.this,jobhiringinfo.class);
                startActivity(intent2);
                break;
            case R.id.news:
                Intent intent3 = new Intent(interested.this,newsinfo.class);
                startActivity(intent3);
                break;
            case R.id.event:
                Intent intent4 = new Intent(interested.this,eventinfo.class);
                startActivity(intent4);
                break;
            case R.id.logout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                Intent intent5 = new Intent(interested.this,login.class);
                startActivity(intent5);
                break;


        }

        return true;
    }
}