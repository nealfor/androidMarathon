package marathon.training;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public class RaceForm extends Activity{
	String raceID = null;
	raceHelper helper=null;
	TextView eventName=null;
	TextView eventType=null;
	TextView pace=null;
	TextView week1=null,
	week2=null,
	week3=null,
	week4=null,
	week5=null,
	week6=null,
	week7=null,
	week8=null,
	week9=null,
	week10=null,
	week11=null,
	week12=null,
	week13=null,
	week14=null,
	week15=null,
	week16=null,
	week17=null,
	week18=null;
	
	
	public final static String ID_EXTRA="marathon.training._id";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.raceform);
	
	eventName=(TextView)findViewById(R.id.eventName);
	eventType=(TextView)findViewById(R.id.eventType);
	pace=(TextView)findViewById(R.id.pace);
	
	helper = new raceHelper(this);
	
	raceID = getIntent().getStringExtra(MarathonAppActivity.ID_EXTRA);
	
	if (raceID!=null) {
		load();
		Toast.makeText(getApplicationContext(), "RACE ID = "+raceID, Toast.LENGTH_SHORT).show();
	}
	
	week1=(TextView)findViewById(R.id.week1);
	week2=(TextView)findViewById(R.id.week2);
	week3=(TextView)findViewById(R.id.week3);
	week4=(TextView)findViewById(R.id.week4);
	week5=(TextView)findViewById(R.id.week5);
	week6=(TextView)findViewById(R.id.week6);
	week7=(TextView)findViewById(R.id.week7);
	week8=(TextView)findViewById(R.id.week8);
	week9=(TextView)findViewById(R.id.week9);
	week10=(TextView)findViewById(R.id.week10);
	week11=(TextView)findViewById(R.id.week11);
	week12=(TextView)findViewById(R.id.week12);
	week13=(TextView)findViewById(R.id.week13);
	week14=(TextView)findViewById(R.id.week14);
	week15=(TextView)findViewById(R.id.week15);
	week16=(TextView)findViewById(R.id.week16);
	week17=(TextView)findViewById(R.id.week17);
	week18=(TextView)findViewById(R.id.week18);
	
	
	
	
	}//end onCreate
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		helper.close();
	}
	
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	new MenuInflater(this).inflate(R.menu.option, menu);
    	return(super.onCreateOptionsMenu(menu));
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId()==R.id.add) {
    		startActivity(new Intent(RaceForm.this, raceCreate.class));
    		return(true);
    	}
    	if (item.getItemId()==R.id.edit) {
    		Intent i=new Intent(RaceForm.this, raceCreate.class);
    		i.putExtra(ID_EXTRA,String.valueOf(raceID));
    		startActivity(i);
    		return(true);
    	}
    	if (item.getItemId()==R.id.delete) {
    		//startActivity(new Intent(RaceForm.this, raceCreate.class));
    		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    		alertDialog.setTitle("Confirm Deletion");
    		alertDialog.setMessage("Are you sure you want to delete?");
    		alertDialog.setButton("Yes!", new DialogInterface.OnClickListener() {
    			   public void onClick(DialogInterface dialog, int which) {
    				   helper.deleteRaceByID(raceID);
    				   Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
    				   finish();
    			   }});    			       		
    		alertDialog.show();
    		return(true);
    	}
    	if (item.getItemId()==R.id.exit) {
    		finish();
    		return(true);
    	}
    	return(super.onOptionsItemSelected(item));
    }
	
	private void load() {
		int paceNumber = 0;
		int paceMin = 0;
		int paceSec = 0;
		String paceText=null;
		
		Cursor c=helper.getById(raceID);
		c.moveToFirst();
		eventName.setText(helper.getEventName(c));
		eventType.setText(helper.getPlanType(c));
		pace.setText(helper.getPace(c));
		
		//Convert pace (stored in seconds) to minutes&seconds
		
		paceNumber = Integer.parseInt(helper.getPace(c));
		paceMin = (short)(paceNumber / 60);
		paceSec = paceNumber - (paceMin*60);
		paceText = Integer.toString(paceMin)+":"+Integer.toString(paceSec);
		pace.setText(helper.getPace(c));
		pace.setText(paceText);
		
		
		c.close();
	}//end load

}//end Class
