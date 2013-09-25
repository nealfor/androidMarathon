package marathon.training;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

public class raceCreate extends Activity implements OnClickListener{	
	String raceID = null;
	raceHelper helper=null;
	String eventName=null;
	String eventType=null;
	String pace=null;		
	
	public void onCreate(Bundle savedInstanceState)
	{    
	  super.onCreate(savedInstanceState);
      setContentView(R.layout.racecreate);
      
      final String [] raceTypes = new String[]{ "Easy","Intermediate","Advanced","Extreme!"};
      
      ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,raceTypes);
      Spinner spin = (Spinner)findViewById(R.id.spinnerPlanType);
      spin.setAdapter(ad);
            
      Button calculate = (Button)findViewById(R.id.btnCalcTime);
      Button save = (Button)findViewById(R.id.btnSubmit);
      Button clear = (Button)findViewById(R.id.btnClear);
      
      raceID = getIntent().getStringExtra(RaceForm.ID_EXTRA);
      if (raceID!=null) {
    	  helper = new raceHelper(this);
    	  Toast.makeText(getApplicationContext(), "RACE ID = "+raceID, Toast.LENGTH_SHORT).show();
    	  load();
    	  save.setText("Update");
    	  helper.close();
       }
      
      calculate.setOnClickListener(this);
      save.setOnClickListener(this);
      clear.setOnClickListener(this);
      
      
      
}//end onCreate
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i;
		switch(v.getId()){
		case R.id.btnClear:
			String clearText = "";
			EditText et = (EditText)findViewById(R.id.txtBoxMinutes);
			et.setText(clearText);
			et = (EditText)findViewById(R.id.txtBoxSeconds);
			et.setText(clearText);
			et = (EditText)findViewById(R.id.textBoxRaceName);
			et.setText(clearText);
			TextView tv = (TextView)findViewById(R.id.textViewRacePace);
			tv.setText("XX:XX:XX");
			return;
		case R.id.btnCalcTime:
			double doubleSeconds = getData();
			if(doubleSeconds == 0)
			{
				TextView marathonTime = (TextView)findViewById(R.id.textViewRacePace);
				marathonTime.setText("XX:XX:XX");
			}
			else								
				setMarathonTime(doubleSeconds);
			return;
		 case R.id.btnSubmit:
			 boolean valid = checkAndSubmit();
			 if (valid == true)
				 Toast.makeText(getApplicationContext(), "Submitted!", Toast.LENGTH_SHORT).show();
			 return;
		}//end switch case
	};
	
	//pulls the info from the text boxes, passes back in seconds
	double getData(){				
		int seconds=0,minutes=0;
		EditText et=(EditText)findViewById(R.id.txtBoxSeconds);		
		try{
			seconds = Integer.parseInt(et.getText().toString());			
		}
		catch(NumberFormatException e){
			Toast.makeText(getApplicationContext(), "Please enter non-zero numbers", Toast.LENGTH_SHORT).show();
			return(0);
		}
	    et=(EditText)findViewById(R.id.txtBoxMinutes);
	    try{
	    	minutes = Integer.parseInt(et.getText().toString());	    	
	    }
	    catch(NumberFormatException e){
	    	Toast.makeText(getApplicationContext(), "Please enter non-zero numbers", Toast.LENGTH_SHORT).show();
	    	return(0);
	    }
	    return((minutes*60)+seconds);
	};//end getData

	//converts the seconds into XX:XX:XX format and displays
	//to the textView
	void setMarathonTime(double TotalSeconds){
		double totalMarSeconds = TotalSeconds * 26.2;
		double tempHours = (int)(totalMarSeconds / 3600);
		double tempMinutes = totalMarSeconds - (tempHours*3600);
		tempMinutes = (int) (tempMinutes * 0.016666666667);
		double tempSeconds = totalMarSeconds - ((tempMinutes*60)+(tempHours*3600));
		String HHMMSS = Integer.toString((int)tempHours)+":"+Integer.toString((int)tempMinutes)+":"+Integer.toString((int)tempSeconds);
		TextView tv = (TextView)findViewById(R.id.textViewRacePace);
		tv.setText(HHMMSS);		
	};
	boolean checkAndSubmit(){
				
		String eventName, eventType, eventPace;
		double paceCheck=0;
		
		raceHelper helper = new raceHelper(this);
				
		
		EditText et = (EditText)findViewById(R.id.textBoxRaceName);		
		eventName = et.getText().toString();		
		
		if(eventName.length()==0)
		{
			Toast.makeText(getApplicationContext(), "Enter A Race Name - Not Saved", Toast.LENGTH_SHORT).show();
			return(false);
		}
		
		eventType = "Easy";
		
		paceCheck = getData();
		if(paceCheck==0)
		{
			Toast.makeText(getApplicationContext(), "Enter A Pace - Not Saved", Toast.LENGTH_SHORT).show();
			return(false);
		}
		else
			eventPace = Integer.toString((int)paceCheck);
		
		
		if(raceID==null)
		{
			helper.insertrace(eventName,eventType,eventPace);
		}
		else//this is an update
		{
			helper.update(raceID,eventName,eventType,eventPace);
		}
				
		helper.close();
		
		return(true);
	};
	private void load() {
		int paceNumber = 0;
		int paceMin = 0;
		int paceSec = 0;
		String paceMinString=null;
		String paceSecString=null;		
		
		Cursor c=helper.getById(raceID);		
		c.moveToFirst();
		eventName = helper.getEventName(c);
		eventType = helper.getPlanType(c);
		pace = helper.getPace(c);
		
		//Convert pace (stored in seconds) to minutes&seconds
		
		paceNumber = Integer.parseInt(pace);
		paceMin = (short)(paceNumber / 60);
		paceMinString = Integer.toString(paceMin);		
		paceSec = paceNumber - (paceMin*60);
		paceSecString = Integer.toString(paceSec);
		
		EditText et=(EditText)findViewById(R.id.txtBoxSeconds);
		et.setText(paceSecString);
		et=(EditText)findViewById(R.id.txtBoxMinutes);
		et.setText(paceMinString);
		et=(EditText)findViewById(R.id.textBoxRaceName);
		et.setText(eventName);
		
		
		c.close();
	}//end load
	
}//end class
