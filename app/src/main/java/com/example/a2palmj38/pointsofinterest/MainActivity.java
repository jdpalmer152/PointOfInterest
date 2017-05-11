package com.example.a2palmj38.pointsofinterest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainActivity extends Activity
{
   MapView mv;
   ItemizedIconOverlay<OverlayItem> items;
   ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;
   public boolean upload;

   protected void onCreate(Bundle savedInstancedState)
   {
        super.onCreate(savedInstancedState);

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        setContentView(R.layout.activity_main);

        mv = (MapView)findViewById(R.id.map1);
        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(50.9, -1.40));

        double latitude = mv.getMapCenter() .getLatitude();
        double longitude = mv.getMapCenter() .getLongitude();

       markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>()
       {
           public boolean onItemLongPress(int i, OverlayItem item) {
               Toast.makeText(MainActivity.this, item.getSnippet(),  Toast.LENGTH_SHORT).show();
               return true;
           }
           public boolean onItemSingleTapUp(int i, OverlayItem item){
               Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
               return true;
           }
       };
       items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), markerGestureListener);
       mv.getOverlays().add(items);
   }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_markers, menu);
        return true;
    }

    public void onStart()
    {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        upload = prefs.getBoolean("upload", true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addmarker)
        {
            Intent intent = new Intent(this, PoiActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        else if(item.getItemId() == R.id.savemarker)
        {
            markersave();
            return true;
        }
        else if (item.getItemId() == R.id.loadmarker)
        {
            markerload();
            return true;
        }
        else if (item.getItemId() == R.id.preferences)
        {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }
       return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
     if (upload == false) {
         if (requestCode == 0) {

             Bundle bundle = intent.getExtras();

             String POIName = bundle.getString("2palmj38.name");
             String POIType = bundle.getString("2palmj38.type");
             String POIDesc = bundle.getString("2palmj38.desc");

             double latitude = mv.getMapCenter().getLatitude();
             double longitude = mv.getMapCenter().getLongitude();

             OverlayItem item = new OverlayItem(POIName, POIType + POIDesc, new GeoPoint(latitude, longitude));

             items.addItem(item);
             mv.invalidate();

             Toast.makeText(MainActivity.this, "Marker has been added!", Toast.LENGTH_SHORT).show();
         }
     }
     else
     {
        Toast.makeText(MainActivity.this, "Service is unavailable!", Toast.LENGTH_SHORT).show();
     }
    }
    private void markersave()
    {
        Toast.makeText(MainActivity.this, "Marker has been saved!", Toast.LENGTH_SHORT).show();

        try
        {
            PrintWriter pw = new PrintWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/markers.txt"));

            for (int i=0; i < items.size(); i++)
            {
                OverlayItem item = items.getItem(i);
                String stringMarkerSave = item.getTitle() + ", " + item.getSnippet() + ", " + item.getPoint().getLatitude() + ", " + item.getPoint().getLongitude();
                pw.println(stringMarkerSave);

            }
            pw.flush();
            pw.close();
        }
        catch(IOException e)
        {
            new AlertDialog.Builder(this).setMessage("Error Saving: " + e).show();
        }

    }



    private void markerload()
    {
        Toast.makeText(MainActivity.this, "Markers loaded!", Toast.LENGTH_SHORT).show();

        try
        {
            FileReader fr = new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/markers.txt");
            BufferedReader reader = new BufferedReader(fr);
            String stringMarkerLoad;
            while((stringMarkerLoad = reader.readLine()) !=null)
            {

                String[] mapmarker = stringMarkerLoad.split(", ");
                if(mapmarker.length==4)
                {
                    double lat=Double.parseDouble(mapmarker[2]);
                    double lon=Double.parseDouble(mapmarker[3]);


                    OverlayItem item = new OverlayItem(mapmarker[0], mapmarker[1], new GeoPoint(lat, lon));
                    items.addItem(item);
                }
            }
        }
        catch(IOException e)
        {
            new AlertDialog.Builder(this).setMessage("Error Loading:" + e).show();
        }


    }

}