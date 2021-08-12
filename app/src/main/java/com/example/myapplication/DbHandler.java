package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tutlane on 06-01-2018.
 */

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    //database name
    private static final String DB_NAME = "kencopuslocaldb";
    //auth user details
    private static final String TABLE_Users = "userdetails";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOC = "location";
    private static final String KEY_DESG = "designation";
    private static final String KEY_META_ID = "metacode";
//Inbuilt tables
    private static final String TABLE_FieldMetaData = "fieldmetadata";
    private static final String TABLE_LANGUAGES = "languages";
    private static final String TABLE_ASSISTANTS = "assistants";
    private static final String TABLE_TOPICS = "topics";
    private static final String TABLE_fieldMetadataDocuments = "documents";

    //////
    private static final String TABLE_METADATA_DOCUMENTS = "audiodocuments";//Has Metadata document id and metadata id as foreign keys.




    //metadata details
    private static final String KEY_METADATA_ID = "id";
    private static final String KEY_METADATA_ASSISTANT_NAME = "assistantname";


    private static final String KEY_METADATA_INTERVIEWEENAME= "intervieweename";
    private static final String KEY_METADATA_INTEVIEWEE_GENDER= "intervieweegender";
    private static final String KEY_METADATA_INTEVIEWEE_AGE_GROUP= "intervieweagegroup";
    private static final String KEY_METADATA_LOCATION = "location";
    private static final String KEY_METADATA_LANGUAGE_CODE = "languagecode";
    private static final String KEY_METADATA_CONTENT_CATEGORY_ID = "categoryid";

    private static final String KEY_METADATA_CONTENT_TOPIC_ID = "topicid";
    private static final String KEY_METADATA_CONTENT_SOURCE= "source";
    private static final String KEY_METADATA_CONTENT_DATE= "interviewdate";

    private static final String KEY_METADATA_ASSISTANT_CODE = "assistantcode";
    //private static final String KEY_METADATA_RECORDING_ENVIRONMENT = "reoordingenvironment";
    private static final String KEY_METADATA_CODE = "metadatacode";


    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        ///////////////// table users
        String CREATE_FIELD_USERS_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_LOC + " TEXT,"
                + KEY_DESG + " TEXT,"
                + KEY_META_ID + " TEXT"+ ")";// USERS ARE ASSISTANTS AND INVESTIGATORS
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        //execute create statement
        db.execSQL(CREATE_FIELD_USERS_TABLE);
        //////////////////////////////////////
        /* ///////////////// table ASSISTANTS////////////
        String CREATE_FIELD_USERS_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_LOC + " TEXT,"
                + KEY_DESG + " TEXT"+ ")";
        //execute create statement
        db.execSQL(CREATE_FIELD_USERS_TABLE);*/
        ////////////table field metadata
        String CREATE_FIELD_METADATA_TABLE = "CREATE TABLE " + TABLE_FieldMetaData + "("
                + KEY_METADATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_METADATA_ASSISTANT_NAME + " TEXT,"

                + KEY_METADATA_INTERVIEWEENAME + " TEXT,"
                + KEY_METADATA_INTEVIEWEE_GENDER + " TEXT,"
                + KEY_METADATA_INTEVIEWEE_AGE_GROUP + " TEXT,"
                //+ KEY_METADATA_LANGUAGE_CODE + "TEXT,"
                + KEY_METADATA_CONTENT_CATEGORY_ID + " TEXT,"
                + KEY_METADATA_CONTENT_TOPIC_ID + " TEXT,"
                + KEY_METADATA_CONTENT_SOURCE + " TEXT,"
                + KEY_METADATA_CONTENT_DATE + " TEXT,"
                + KEY_METADATA_ASSISTANT_CODE + " TEXT,"
                + KEY_METADATA_CODE+ " TEXT,"
                + KEY_METADATA_LOCATION + " TEXT"+ ")";
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FieldMetaData);
        //execute create statement
       // db.execSQL(CREATE_FIELD_USERS_TABLE);
        db.execSQL(CREATE_FIELD_METADATA_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FieldMetaData);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    void insertUserDetails(String name, String location, String designation,String metacode){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_LOC, location);
        cValues.put(KEY_DESG, designation);
        cValues.put(KEY_META_ID, metacode);
        // Insert the new row, returning the primary key value of the new row
        long newRowId=0;
        newRowId = db.insert(TABLE_Users,null, cValues);
        db.close();
        if(newRowId>0)
        System.out.println(newRowId+" added for metadata code "+metacode);
        else
            System.out.println(newRowId+" add failed metadata code unsaved "+metacode);
      //  Toast.makeText(getApplicationContext(),"DB Error ",Toast.LENGTH_SHORT).show();
    }
    // Adding new metadata Details todo: Test add metadata to localdb and online db.
    public long insertfieldmetadataDetails(String assistantname, String intervieweename,String intervieweegender,String intervieweagegroup,
                                    String location,String languagecode,String categoryid, String topicid,String source, String interviewdate,String assistantcode,String metadatacode){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_METADATA_ASSISTANT_NAME, assistantname);
        cValues.put(KEY_METADATA_INTERVIEWEENAME, intervieweename);
        cValues.put(KEY_METADATA_INTEVIEWEE_GENDER, intervieweegender);
       // cValues.put(KEY_METADATA_LANGUAGE_CODE, languagecode);
        cValues.put(KEY_METADATA_INTEVIEWEE_AGE_GROUP, intervieweagegroup);
        cValues.put(KEY_METADATA_LOCATION, location);

        cValues.put(KEY_METADATA_CONTENT_CATEGORY_ID, categoryid);
        cValues.put(KEY_METADATA_CONTENT_TOPIC_ID, topicid);
        cValues.put(KEY_METADATA_CONTENT_SOURCE, source);
    cValues.put(KEY_METADATA_CONTENT_DATE, interviewdate);
      //  cValues.put(KEY_METADATA_ASSISTANT_CODE, assistantcode);//ABCDEFGHIJKLM userid_username
        cValues.put(KEY_METADATA_ASSISTANT_CODE, languagecode);//ABCDEFGHIJKLM userid_username
        cValues.put(KEY_METADATA_CODE, metadatacode);

        // cValues.put(KEY_METADATA_RECORDING_ENVIRONMENT, reoordingenvironment);
        long newRowId=0;
        // Insert the new row, returning the primary key value of the new ro
         newRowId = db.insert(TABLE_FieldMetaData,null, cValues);
       // DbHandler dbHandler=new DbHandler(this);

        System.out.println(newRowId+" added for metadata id "+metadatacode);
        db.close();
        if(newRowId>0)
        return newRowId;
        return newRowId;
    }
    // Get User Details
    public ArrayList<HashMap<String, String>> getUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location, designation,metacode FROM "+ TABLE_Users+" order by id desc";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String, String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("designation",cursor.getString(cursor.getColumnIndex(KEY_DESG)));
            user.put("location",cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            user.put("metacode",cursor.getString(cursor.getColumnIndex(KEY_META_ID)));
            userList.add(user);
        }cursor.close();
        db.close();
        return  userList;
    }

    // Get Metadata details
    public ArrayList<HashMap<String, String>> getKenCorpusMetadata(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> fieldmetadataList = new ArrayList<>();
     //   String query = "SELECT * FROM "+ TABLE_FieldMetaData;
        String query = "SELECT assistantname, intervieweename, intervieweegender,assistantcode ,location ,topicid,categoryid ,interviewdate,metadatacode FROM "+ TABLE_FieldMetaData+" order by id desc";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String, String> fieldmetadata = new HashMap<>();
            fieldmetadata.put("assistantname",cursor.getString(cursor.getColumnIndex(KEY_METADATA_ASSISTANT_NAME )));
            fieldmetadata.put("intervieweename",cursor.getString(cursor.getColumnIndex(KEY_METADATA_INTERVIEWEENAME)));
            fieldmetadata.put("intervieweegender",cursor.getString(cursor.getColumnIndex(KEY_METADATA_INTEVIEWEE_GENDER)));
            fieldmetadata.put("languagecode",cursor.getString(cursor.getColumnIndex(KEY_METADATA_ASSISTANT_CODE)));//TODO: ADD COLUMN  LANGUAGE CODE 2021 JUL 31 14H
            fieldmetadata.put("location",cursor.getString(cursor.getColumnIndex(KEY_METADATA_LOCATION)));
            fieldmetadata.put("topic",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CONTENT_TOPIC_ID)));
            fieldmetadata.put("category",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CONTENT_CATEGORY_ID)));
            fieldmetadata.put("interviewdate",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CONTENT_DATE)));
            fieldmetadata.put("metadatacode",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CODE)));
            fieldmetadataList.add(fieldmetadata);
        }
        cursor.close();
        db.close();
        return  fieldmetadataList;
    }
    // Get User Details based on userid
    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users;
        //initializing cursor
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_NAME, KEY_LOC, KEY_DESG,KEY_META_ID}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String, String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("designation",cursor.getString(cursor.getColumnIndex(KEY_DESG)));
            user.put("location",cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            user.put("metacode",cursor.getString(cursor.getColumnIndex(KEY_META_ID)));
            userList.add(user);
        }
        cursor.close();
        db.close();
        return  userList;
    }
    public ArrayList<HashMap<String, String>> GetMetaDatabyMetacode(String  metacode){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> metadataList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_FieldMetaData;
        //initializing cursor
        Cursor cursor = db.query(TABLE_FieldMetaData, new String[]{
                KEY_METADATA_CONTENT_SOURCE, KEY_METADATA_CONTENT_TOPIC_ID,
                KEY_METADATA_CONTENT_CATEGORY_ID,KEY_METADATA_ASSISTANT_NAME,
                KEY_METADATA_CONTENT_DATE,KEY_METADATA_INTERVIEWEENAME,KEY_METADATA_CODE}, KEY_METADATA_CODE+ "=?",new String[]{String.valueOf(metacode)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String, String> metadata = new HashMap<>();
          //  metadata.put("languagecode",cursor.getString(cursor.getColumnIndex(KEY_METADATA_LANGUAGE_CODE)));

            metadata.put("datasource",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CONTENT_SOURCE)));

            metadata.put("topic",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CONTENT_TOPIC_ID)));
            metadata.put("category",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CONTENT_CATEGORY_ID)));
            metadata.put("assistantname",cursor.getString(cursor.getColumnIndex(KEY_METADATA_ASSISTANT_NAME)));
            metadata.put("interviewdate",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CONTENT_DATE)));
            metadata.put("intervieweename",cursor.getString(cursor.getColumnIndex(KEY_METADATA_INTERVIEWEENAME)));
            metadata.put("metacode",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CODE)));
            metadataList.add(metadata);
            System.out.println(cursor.getString(cursor.getColumnIndex("topic"))+" topic is present.");
        }
        return  metadataList;
    }

    public ArrayList<HashMap<String, String>> GetMetaDatabyMetdatabyid(int  metaid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> metadataList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_FieldMetaData;
        //initializing cursor
        Cursor cursor = db.query(TABLE_FieldMetaData, new String[]{
                KEY_METADATA_CONTENT_SOURCE, KEY_METADATA_CONTENT_TOPIC_ID,
                KEY_METADATA_CONTENT_CATEGORY_ID,KEY_METADATA_ASSISTANT_NAME,
                KEY_METADATA_CONTENT_DATE,KEY_METADATA_INTERVIEWEENAME,KEY_METADATA_CODE}, KEY_METADATA_ID+ "=?",new String[]{String.valueOf(metaid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String, String> metadata = new HashMap<>();
            //metadata.put("languagecode",cursor.getString(cursor.getColumnIndex(KEY_METADATA_LANGUAGE_CODE)));

            metadata.put("datasource",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CONTENT_SOURCE)));

            metadata.put("topic",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CONTENT_TOPIC_ID)));
            metadata.put("category",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CONTENT_CATEGORY_ID)));
            metadata.put("assistantname",cursor.getString(cursor.getColumnIndex(KEY_METADATA_ASSISTANT_NAME)));
            metadata.put("interviewdate",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CONTENT_DATE)));
            metadata.put("intervieweename",cursor.getString(cursor.getColumnIndex(KEY_METADATA_INTERVIEWEENAME)));
            metadata.put("metacode",cursor.getString(cursor.getColumnIndex(KEY_METADATA_CODE)));
            metadataList.add(metadata);
        }
        return  metadataList;
    }
    // Delete User Details
    public void DeleteUser(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }
    public ArrayList<HashMap<String, String>> GetUserByUserMetacode(String metacode){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users;
        //initializing cursor
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_NAME, KEY_LOC, KEY_DESG,KEY_META_ID}, KEY_META_ID+ "=?",new String[]{String.valueOf(metacode)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String, String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("designation",cursor.getString(cursor.getColumnIndex(KEY_DESG)));
            user.put("location",cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            user.put("metacode",cursor.getString(cursor.getColumnIndex(KEY_META_ID)));
            userList.add(user);
        }
        return  userList;
    }
    // Update User Details
    public int UpdateUserDetails(String location, String designation, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_LOC, location);
        cVals.put(KEY_DESG, designation);
        int count = db.update(TABLE_Users, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }
    // Adding new User Details
    void insertTopicDetails(String title, String code, String designation){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        //cValues.put(KEY_NAME, name);
      //  cValues.put(KEY_LOC, location);
        cValues.put(KEY_DESG, designation);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
        db.close();
    }
    ///////////////load dynamic spinner todo:2021 Jul 12 solve topics dyamic list/////////////
    public List< SpinnerObject> getAllTopicsLabels(){
        List < SpinnerObject > listlabels = new ArrayList < SpinnerObject > ();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TOPICS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ( cursor.moveToFirst () ) {
            do {
                listlabels.add ( new SpinnerObject ( cursor.getInt(0) , cursor.getString(1) ) );
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning labels
        return listlabels;
    }
}