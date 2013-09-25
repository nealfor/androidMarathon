package marathon.training;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;



public class MarathonAppActivity extends Activity {
    
    Cursor model=null;
    raceAdapter adapter = null;
    Race r=new Race();
    raceHelper helper = new raceHelper(this);
    public final static String ID_EXTRA="marathon.training._id";
    
    
       
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        helper = new raceHelper(this);
        
        
        Button newPlan = (Button)findViewById(R.id.btnCreateNew);
        Button about = (Button)findViewById(R.id.btnAbout);
        newPlan.setOnClickListener(myClickListener);
        about.setOnClickListener(myClickListener);
        
        ListView list =(ListView)findViewById(R.id.racesFromDB);
        
        model=helper.getAll();
        startManagingCursor(model);
        adapter=new raceAdapter(model);
        list.setAdapter(adapter);
        list.setOnItemClickListener(onListClick);
        
        //list.setOnItemLongClickListener(myLongListener);
        

    }//end onCreate

    @Override
    public void onDestroy() {
    	super.onDestroy();
    	
    	helper.close();
    }
    
    OnClickListener myClickListener = new OnClickListener(){
    	@Override
    	public void onClick(View v){
    		if(v.getId() == R.id.btnCreateNew)
    		{
    			Intent i = new Intent(MarathonAppActivity.this,raceCreate.class);
    			startActivity(i);
    			return;
    		}
    		else//must be about
    		{
    			Toast.makeText(getApplicationContext(), "Neal wuz here", Toast.LENGTH_SHORT).show();
    		}
    	}
    };
    
    private AdapterView.OnItemClickListener onListClick=new
    AdapterView.OnItemClickListener() {
    	public void onItemClick(AdapterView<?> parent,
    			View view, int position,
    			long id) {
    		Intent i=new Intent(MarathonAppActivity.this, RaceForm.class);
    		i.putExtra(ID_EXTRA,String.valueOf(id));
    		startActivity(i);
    }
    };
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	new MenuInflater(this).inflate(R.menu.option, menu);
    	return(super.onCreateOptionsMenu(menu));
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId()==R.id.add) {
    		startActivity(new Intent(MarathonAppActivity.this, raceCreate.class));
    		return(true);
    	}
    	if (item.getItemId()==R.id.exit) {
    		finish();
    		return(true);
    	}
    	return(super.onOptionsItemSelected(item));
    }

class raceAdapter extends CursorAdapter{

	raceAdapter(Cursor c){
		super(MarathonAppActivity.this, c);
	}
	
		
	@Override
	public void bindView(View row, Context ctxt,
		Cursor c) {
			raceHolder holder=(raceHolder)row.getTag();
			holder.populateFrom(c, helper);
	}
	@Override
	public View newView(Context ctxt, Cursor c,	ViewGroup parent) {
			//LayoutInflater inflater=getLayoutInflater();
		LayoutInflater inflater = (LayoutInflater)ctxt.getSystemService
	      (Context.LAYOUT_INFLATER_SERVICE);
			View row=inflater.inflate(R.layout.racerow, parent, false);
			raceHolder holder=new raceHolder(row);
			row.setTag(holder);
			return(row);
		}
	}//end class
	
   static class raceHolder {
		private TextView eventName=null;
		private TextView eventType=null;
		private TextView pace=null;
		private int paceNumber = 0;
		private int paceMin = 0;
		private int paceSec = 0;
		private String paceText=null;
		
		raceHolder(View racerow) {
		eventName=(TextView)racerow.findViewById(R.id.eventName);
		eventType=(TextView)racerow.findViewById(R.id.eventType);
		pace=(TextView)racerow.findViewById(R.id.pace);
		
		}
		
		void populateFrom(Cursor c, raceHelper helper) {
		eventName.setText(helper.getEventName(c));
		eventType.setText(helper.getPlanType(c));
		paceText = helper.getPace(c);
		paceNumber = Integer.parseInt(helper.getPace(c));
		paceMin = (short)(paceNumber / 60);
		paceSec = paceNumber - (paceMin*60);
		paceText = Integer.toString(paceMin)+":"+Integer.toString(paceSec);
		//pace.setText(helper.getPace(c));
		pace.setText(paceText);
		}
		
	}//end raceHolder
	
	
	
}//end MarathonAppActivity
