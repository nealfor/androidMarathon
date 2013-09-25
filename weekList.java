package marathon.training;

import marathon.training.MarathonAppActivity.raceAdapter;
import marathon.training.MarathonAppActivity.raceHolder;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class weekList extends Activity {
	String raceID = null;
	String[] days = {"",
			"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	Cursor model=null;
    weekAdapter adapter = null;
    raceHelper helper = new raceHelper(this);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weeklist);
        
        raceID = getIntent().getStringExtra(MarathonAppActivity.ID_EXTRA);
        
        helper = new raceHelper(this);
        model = helper.getWeekById(raceID);
        ListView list =(ListView)findViewById(R.id.racesFromDB);
        adapter=new weekAdapter(model);
        list.setAdapter(adapter);
	}
	
	
	class weekAdapter extends CursorAdapter{

		weekAdapter(Cursor c){
			super(weekList.this, c);
		}
		
			
		@Override
		public void bindView(View row, Context ctxt,
			Cursor c) {
				weekHolder holder=(weekHolder)row.getTag();
				for(int i=0;i<7;i++)
				{
					holder.populateFrom(days[i],"Easy","8:30");
				}
		}
		@Override
		public View newView(Context ctxt, Cursor c,	ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater)ctxt.getSystemService
		      (Context.LAYOUT_INFLATER_SERVICE);
				View row=inflater.inflate(R.layout.racerow, parent, false);
				weekHolder holder=new weekHolder(row);
				row.setTag(holder);
				return(row);
			}
		}//end class
	
	static class weekHolder{
		private TextView dayName=null;
		private TextView runType=null;
		private TextView pace=null;
		
		weekHolder(View racerow) {
			dayName=(TextView)racerow.findViewById(R.id.eventName);
			runType=(TextView)racerow.findViewById(R.id.eventType);
			pace=(TextView)racerow.findViewById(R.id.pace);			
			}
		
		void populateFrom(String dayOfWeek,String runTypeText,String runPace) {
			dayName.setText(dayOfWeek);
			runType.setText(runTypeText);			
			pace.setText(runPace);
			}
	}
	
}//end weekList class
