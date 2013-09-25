package marathon.training;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

public class raceHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME="racelist.db";
	private static final int SCHEMA_VERSION=1;
	
	public raceHelper(Context context){
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}//public raceHelper
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE racelist (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" eventname TEXT, plantype TEXT, pace TEXT);");
		
		db.execSQL("CREATE TABLE weeklist (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
		" eventfkey TEXT, week1 TEXT, week2 TEXT, week3 TEXT, week4 TEXT, week5 TEXT" +
		", week6 TEXT, week7 TEXT, week8 TEXT, week9 TEXT, week10 TEXT, week11 TEXT, week12 TEXT" +
		", week13 TEXT, week14 TEXT, week15 TEXT, week16 TEXT, week17 TEXT, week18 TEXT);");
	}//end onCreate
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		//nothing here yet
	}//end onUpgrade
	
	public Cursor getAll() {
		return(getReadableDatabase()
				.rawQuery("SELECT _id, eventname, plantype, pace FROM racelist"+
						" ORDER BY eventname",null));
	}
	
	public void insertrace(String eventname, String plantype,
			String pace) {
			ContentValues cv=new ContentValues();
			cv.put("eventname", eventname);
			cv.put("plantype", plantype);
			cv.put("pace", pace);
			
			getWritableDatabase().insert("racelist", "eventname", cv);
			ContentValues weekCV=new ContentValues();
			weekCV.put("eventfkey", eventname);
			weekCV.put("week1", "");
			weekCV.put("week2", "");
			weekCV.put("week3", "");
			weekCV.put("week4", "");
			weekCV.put("week5", "");
			weekCV.put("week6", "");
			weekCV.put("week7", "");
			weekCV.put("week8", "");
			weekCV.put("week9", "");
			weekCV.put("week10", "");
			weekCV.put("week11", "");
			weekCV.put("week12", "");
			weekCV.put("week13", "");
			weekCV.put("week14", "");
			weekCV.put("week15", "");
			weekCV.put("week16", "");
			weekCV.put("week17", "");
			weekCV.put("week18", "");
			getWritableDatabase().insert("weeklist", "eventfkey", weekCV);
	}
	
	public void deleteRaceByID(String id){
		getWritableDatabase().delete("weeklist", "_id ="+id, null);
		getWritableDatabase().delete("racelist", "_id ="+id, null);
	}

	public Cursor getById(String id) {
		String[] args={id};
		return(getReadableDatabase()
		.rawQuery("SELECT _id, eventname, plantype, pace FROM racelist WHERE _id=?",
		args));
		}
	public Cursor getWeekById(String id) {
		String[] args={id};
		return(getReadableDatabase()
		.rawQuery("SELECT _id, eventname, plantype, pace FROM racelist WHERE _id=?",
		args));
		}
	
	public void update(String id, String name, String type, String pace) {
			ContentValues cv=new ContentValues();
			String[] args={id};
			cv.put("eventname", name);			
			cv.put("plantype", type);
			cv.put("pace", pace);
			getWritableDatabase().update("racelist", cv, "_id=?",
			args);
	}
	
	public String getEventName(Cursor c) {
		return(c.getString(1));
		}
	
	public String getPlanType(Cursor c) {
		return(c.getString(2));
		}
	
	public String getPace(Cursor c) {
		return(c.getString(3));
		}
}//end raceHelper Class
