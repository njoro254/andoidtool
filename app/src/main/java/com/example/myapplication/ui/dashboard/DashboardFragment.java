package com.example.myapplication.ui.dashboard;
import com.example.myapplication.Apikit.ApiClientAdapter;
import com.example.myapplication.Apikit.GitHubClient;
import com.example.myapplication.Apikit.GitHubRepo;
import com.example.myapplication.Apikit.GitHubService;
import com.example.myapplication.Apikit.WebAPIService;
import com.example.myapplication.DbHandler;
import com.example.myapplication.Models.Dict;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MetadataUploadStatusActivity;
import com.example.myapplication.Models.KenCorpusService;
import com.example.myapplication.Models.Response201;
import com.example.myapplication.Models.SimpleEntity;
import com.example.myapplication.R;
import com.google.gson.Gson;

import org.junit.Assert;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
/////////////////////////////////////
public    String  strselectedabsolutefilepath;
    KenCorpusService kcorpusutil;
    ProgressDialog progressDialog;
    int PICK_PDF_REQUEST=1;
    String temp, response = "";
    private final Context mContext=getContext();
    private final String API_URL_BASE = "https://kencorpus.co.ke/";
    private final String LOG_TAG = "BNK";//Error..
    Uri fileuri;
    String metaassistantname, metaintervieweename,metalocation,metainterveweegender, metalanguage,
            meta_agegroup,metalanguagecode,metacategory,metatopic,metasource,metainterviewdate,metadatacode;
    String fileupload_name;
    DbHandler dbHandler;
    ///////////////
    Calendar cal = Calendar.getInstance();
    Calendar cal2day=Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedDate = df.format(cal.getTime());
    /////////////////////
public  String assistantname;
    public String speakername,strtopic,strspeakeragegroup;
    public String speakerlocation,metadataource,speakerlanguage,strspeakername;
    public String strcategory_id,speechdate,speechtopic,strspeechcategory,strlanguage;
    public String speakergender,strtopic_id,strtopic_code,strmeta_code,strsource,assistant_code,strlanguagecode;


////////////////
String[]strarrspeakeragroup={"Select Age Group","Primary","High School","University","Middle-Age","Seniors","-"};
    ArrayList<HashMap<String, String>> userList,metadataList,metaList;

    /////////////////////
    private DashboardViewModel dashboardViewModel;
    String strassistantname;
    ///////////////////
    ImageButton imagebuttondateset;
    TextView textViewdashboardsup,textViewdateset,tvSaveUpload;
    Spinner spinnercategory;
    EditText ed1,ed2,ed3;
    Button b1;
    Intent intentdata;
    private Calendar myCalendar = Calendar.getInstance();
    private TextView textViewGender,textViewAgeGroup,textViewTopic,textViewSource,textViewLanguage,textViewSpeechCategory,textviewAudiofilelabel,textViewdashboardlocation;
    private Spinner spinnersource;
     Spinner spinnertopics;
     Spinner spinnerlanguage;
    Spinner spinnerspeakeragegroup;
     Spinner spinnergender;

    public DashboardFragment() {
        spinnertopics = null;
        spinnergender=null;
        spinnerlanguage=null;
        spinnerspeakeragegroup=null;
        strspeakername="";

    }

    @SuppressLint("FragmentLiveDataObserve")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getContext());
        strselectedabsolutefilepath="";
        kcorpusutil=new KenCorpusService();
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                String result = bundle.getString("bundleKey");
                if ((result==null))
                {
                    Toast.makeText(getActivity(), "Null result", Toast.LENGTH_SHORT).show();
                return;
                }
                // Do something with the result
                Toast.makeText(getActivity(), "passed from Home Fragment"+result, Toast.LENGTH_SHORT).show();
            }
        });
     //intentdata=Intent.getIntent();
        //tested ok///////////
        metalanguagecode="DHO";
        //pull results from form interface works ok.
        Bundle result = new Bundle();
        result.putString("bundleKey", metalanguagecode);
        getParentFragmentManager().setFragmentResult("requestKey", result);
        //Try use the model

        //strassistantname=intentdata.getStringExtra("metaassistantname");
        //metalanguagecode=intentdata.getStringExtra("metalanguagecode");
       // textViewdashboardsup = root.findViewById(R.id.sup);
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardViewModel.getText();
        textViewdashboardlocation= root.findViewById(R.id.edittextspeakerlocation);
       // textViewdashboardloation.setText(metalanguagecode);
        textViewGender=(TextView)root.findViewById(R.id.textview_gender);
        textViewAgeGroup=(TextView)root.findViewById(R.id.textview_agegroup);
        textViewTopic=(TextView)root.findViewById(R.id.textView_Topic);

        textViewSource=(TextView)root.findViewById(R.id.textView_datasource);
        textViewSpeechCategory=(TextView)root.findViewById(R.id.textview_category);
        textViewLanguage=(TextView)root.findViewById(R.id.textview_language);

        textviewAudiofilelabel=(TextView)root.findViewById(R.id.tvaudiofilelabel) ;
        /////////////////Text fields implemented///////
        //////////////////////////////////
        ed1=(EditText)root.findViewById(R.id.editTextResearchAssistantName);
        ed2=(EditText)root.findViewById(R.id.editTextSpeakerName);

        ed3=(EditText)root.findViewById(R.id.edittextspeakerlocation);
        //edmetadataource=(TextView)findViewById(R.id.tvdatasource);
        // metadatadatepicker=(DatePicker)findViewById(R.id.metadatasimpleDatePicker);

       // tvSaveUpload=root.findViewById(r.)
        tvSaveUpload=(TextView)root.findViewById(R.id.tv_dashboard_save_upload);
        //tvSaveUpload.setOnClickListener(this);
        /////////////define spinners////////////
        /////////////////Spinner Gender///////////////

        spinnergender = (Spinner) root.findViewById(R.id.spinner_gender);

        //spinnergender.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adaptergender = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adaptergender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnergender.setAdapter(adaptergender);
        //////////////////
        ////////////////////////////////////

        spinnerspeakeragegroup = (Spinner)root.findViewById(R.id.spinner_agegroup);

        ArrayAdapter<String> adapterspeakeragegroup = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, strarrspeakeragroup);
        adapterspeakeragegroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerspeakeragegroup.setAdapter(adapterspeakeragegroup);
        //spinnerspeakeragegroup.setOnItemSelectedListener(this);
        //activate action listener

        spinnerspeakeragegroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                System.out.println(" My age group "+strspeakeragegroup);
                if(!spinnerspeakeragegroup.getSelectedItem().toString().trim().equals("Select Age Group")) {
                    // Toast.makeText(getApplicationContext(),"Select Metadata Category",Toast.LENGTH_SHORT).show();
                    textViewAgeGroup.setTextColor(Color.parseColor("#262927"));
                    //  strspeakeragegroup = ((Dict) spinnerspeakeragegroup.getSelectedItem()).getText();
                    strspeakeragegroup = parent.getItemAtPosition(position).toString();
                    textViewAgeGroup.setText("Age Group: ");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ///////////////////////////////

        spinnerlanguage = (Spinner) root.findViewById(R.id.spinner_language);

        //  spinnerlanguage.setOnItemSelectedListener(this);
        final List<Dict> listlanuages = new ArrayList<Dict>();
        listlanuages.add(new  Dict(0, "Select Language","-"));
        listlanuages.add(new  Dict(1, "SWA","SWA"));
        listlanuages.add(new  Dict(2, "DHO","DHO"));
        listlanuages.add(new  Dict(3, "LHYBK","LHYBK"));
        listlanuages.add(new  Dict(4, "LHYCH","LHYCH"));
        listlanuages.add(new  Dict(5, "LHYLG","LHYLG"));

        ArrayAdapter<Dict> adapterlanguage = new ArrayAdapter<Dict>(getActivity(),
                android.R.layout.simple_spinner_item, listlanuages);
        //  spinnertopics.setAdapter(adapter);
        // ArrayAdapter<String> adapterlanguage = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, speechlanguage);
        // adapterlanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerlanguage.setAdapter(adapterlanguage);
        ///////////////////////////////////

        spinnertopics = (Spinner)root.findViewById(R.id.spinner_topic);
        final List<Dict> topics = new ArrayList<Dict>();
        topics.add(new  Dict(0, "Select Topic","-"));
        topics.add(new  Dict(1, "Agriculture","Agr"));
        topics.add(new  Dict(2, "Education","Edu"));
        topics.add(new  Dict(3, "Politics","Pol"));
        topics.add(new  Dict(3, "Culture","Cul"));
        topics.add(new  Dict(4, "Religion","Rel"));
        ArrayAdapter<Dict> adaptertopics= new ArrayAdapter<Dict>(getActivity(),
                android.R.layout.simple_spinner_item, topics);
        // ArrayAdapter<String> adaptertopics = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, speechtopics);
        // adaptertopics.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnertopics.setAdapter(adaptertopics);

        spinnertopics.setAdapter(adaptertopics);
      //  spinnertopics.setOnItemSelectedListener(this);
        spinnercategory = root.findViewById(R.id.spinner_category);
        final List<Dict> listcategories = new ArrayList<Dict>();
        listcategories.add(new  Dict(0, "Select Category","-"));
        listcategories.add(new  Dict(1, "Letters",null));
        listcategories.add(new  Dict(2, "Folklore",null));
        listcategories.add(new  Dict(3, "Essays",null));
        listcategories.add(new  Dict(4, "Stories",null));
        listcategories.add(new  Dict(5, "Dialogues",null));
        listcategories.add(new  Dict(6, "Songs",null));

        ArrayAdapter<Dict> adaptercategory;
        adaptercategory = new ArrayAdapter<Dict>(getActivity(),android.R.layout.simple_spinner_item, listcategories);
        adaptercategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercategory.setAdapter(adaptercategory);
    //    spinnercategory.setOnItemSelectedListener(this);
        ////////////////////////////////////////////////////

        spinnersource = root.findViewById(R.id.spinner_source);

        //spinnersource.setOnItemSelectedListener(this);
        final List<Dict> listSource = new ArrayList<Dict>();
        listSource.add(new  Dict(0, "Select Source","-"));
        listSource.add(new  Dict(1, "Schools","Sch"));
        listSource.add(new  Dict(2, "Publisher","Pub"));
        listSource.add(new  Dict(3, "Media","Media"));
        listSource.add(new  Dict(4, "Community","Comm"));
        ArrayAdapter<Dict> adaptersource = new ArrayAdapter<Dict>(getActivity(),
                android.R.layout.simple_spinner_item, listSource);
        //  spinnertopics.setAdapter(adapter);
        // ArrayAdapter<String> adapterlanguage = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, speechlanguage);
        // adapterlanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersource.setAdapter(adaptersource);
        //////////////listeners///////////////
        spinnergender.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int i, long l) {
                speakergender = adapterView.getItemAtPosition(i).toString();
                System.out.println(" in on selected category"+speakergender);
                if(!spinnergender.getSelectedItem().toString().trim().equals("Select Gender")) {
                    // Toast.makeText(getApplicationContext(),"Select Metadata Category",Toast.LENGTH_SHORT).show();
                    textViewGender.setTextColor(Color.parseColor("#262927"));
                    speakergender = adapterView.getItemAtPosition(i).toString();

                    //  speakergender = ((Dict) spinnergender.getSelectedItem()).getText();
                    textViewGender.setText("Interviewee Gender: ");

                }
            }

            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });
        spinnerlanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strlanguagecode= ((Dict) spinnerlanguage.getSelectedItem()).getAbbrev().toString();
                strlanguage= ((Dict) spinnerlanguage.getSelectedItem()).getAbbrev().toString();
                if(!spinnerlanguage.getSelectedItem().toString().trim().equals("Select Language")) {
                    // Toast.makeText(getApplicationContext(),"Select Metadata Category",Toast.LENGTH_SHORT).show();
                    textViewLanguage.setTextColor(Color.parseColor("#262927"));
                    strlanguagecode = ((Dict) spinnerlanguage.getSelectedItem()).getText();
                    textViewLanguage.setText("Metadata Language: ");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnertopics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  Get the key method: mySpinner.getSelectedItem().toString() or ((com.example.uitestactivity.Dict)mySpinner.getSelectedItem()).getId()
                //  Get the value: ((com.example.uitestactivity.Dict)mySpinner.getSelectedItem()).getText();
                /*Toast.makeText(AuthorSpeakerActivity.this,
                        "Key:" + spinnertopics.getSelectedItem().toString() + "," + ((Dict) spinnertopics.getSelectedItem()).getId() +
                                ",Value:" + ((Dict) spinnertopics.getSelectedItem()).getText(),
                        Toast.LENGTH_LONG).show();*/
                System.out.println("listening..."+((Dict) spinnertopics.getSelectedItem()).getId());
                System.out.println("listening..also..."+((Dict) spinnertopics.getSelectedItem()).getText());
                System.out.println("listening..mapping to..."+((Dict) spinnertopics.getSelectedItem()).getAbbrev());//todo why map to edu...?
                strtopic_id=((Dict) spinnertopics.getSelectedItem()).getId().toString();
                int topicid = Integer.parseInt(strtopic_id);
                strtopic=((Dict) spinnertopics.getSelectedItem()).getText().toString();
                strtopic_code=((Dict) spinnertopics.getSelectedItem()).getAbbrev().toString();
                if(!spinnertopics.getSelectedItem().toString().trim().equals("Select Topic")) {
                    // Toast.makeText(getApplicationContext(),"Select Metadata Category",Toast.LENGTH_SHORT).show();
                    textViewTopic.setTextColor(Color.parseColor("#262927"));
                    strtopic = ((Dict) spinnertopics.getSelectedItem()).getText();
                    textViewTopic.setText("Metadata Topic: ");

                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnercategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int strspinnercategory=((Dict) spinnercategory.getSelectedItem()).getId();

                strspeechcategory = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(),strspeechcategory+" Select Category",Toast.LENGTH_SHORT).show();
                if(!spinnercategory.getSelectedItem().toString().trim().equals("Select Category")) {
                    // Toast.makeText(getApplicationContext(),"Select Metadata Category",Toast.LENGTH_SHORT).show();
                    textViewSpeechCategory.setTextColor(Color.parseColor("#262927"));
                    strspeechcategory = ((Dict) spinnercategory.getSelectedItem()).getText();
                    textViewSpeechCategory.setText("Category: ");

                }
                // if (nameEdt.getText().toString().isEmpty() && jobEdt.getText().toString().isEmpty()) {
                //    Toast.makeText(MainActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                return;
                // }
                // calling a method to post the data and passing our name and job.
                // postData(nameEdt.getText().toString(), jobEdt.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnersource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strsource=((Dict) spinnersource.getSelectedItem()).getText().toString();
                if(!spinnersource.getSelectedItem().toString().trim().equals("Select Source")) {
                    // Toast.makeText(getApplicationContext(),"Select Metadata Category",Toast.LENGTH_SHORT).show();
                    textViewSource.setTextColor(Color.parseColor("#262927"));
                    strsource = ((Dict) spinnersource.getSelectedItem()).getText();
                    textViewSource.setText("Metadata Source: ");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ////////////////////////////////////
        String API_BASE_URL = "https://api.github.com/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        //GitHubClient client =  retrofit.create(GitHubClient.class);
        GitHubService client =  retrofit.create(GitHubService.class);

// Fetch a list of the Github repositories.
        Call<List<GitHubRepo>> call =
                client.reposForUser("njoro254");

// Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                // The network call was a success and we got a response
                // TODO: use the repository list and display it
                //GitHubRepo repo=response.body();
                List<GitHubRepo> lr=response.body();
                for (GitHubRepo repo: lr)
                {
                    repo.getName();
                    System.out.println(repo.getName()+" Njoros...");
                }

            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });

        ///date listener
        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        /////////////
        String dateselect = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        //text view date set
        textViewdateset=(TextView)root.findViewById(R.id.textviewselectdate);

        textViewdateset.setText("Select Date: "+dateselect);
        ////////////////////
        imagebuttondateset=(ImageButton)root.findViewById(R.id.ibtnmetadatadateselect);
        imagebuttondateset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), dateListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                //following line to restrict future date selection
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
//Liseners implementation////////




        final TextView textViewbrowseaudio = root.findViewById(R.id.tv_dashboard_browsefile);
        textViewbrowseaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGalleryPDF();
                Toast.makeText(getContext()," Selecting Metadata file "  , Toast.LENGTH_LONG).show();

                //if (null != selectedPath && !selectedPath.isEmpty()) {
                // selectedPath=filepath.getAbsolutePath();
                TextView textViewfilepath;
                // String prefixuploadfilename="Onyango."+Math.random();
                String suffix_extension="";
                //  String extension = MimeTypeMap.getFileExtensionFromUrl(fileuri.getPath());
              //  textviewAudiofilelabel.setText(strselectedabsolutefilepath);

                ///////////////////Bundle test////////////////////////
                Bundle result = new Bundle();
                result.putString("bundleKey", metalanguagecode);
                getParentFragmentManager().setFragmentResult("requestKey", result);
                //////////////////////////////////////////////////////

            }
        });

    //Todo: Learn on view Model.. 2021 Augh 10 17h
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                textViewbrowseaudio.setText(s);
            }
        });
        return root;
    }///////////////////////
    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Uri uri = data.getData();
        // File file = new File(uri.getPath());
        //selectedDocPath=file.getPath();
        //System.out.println(selectedDocPath+" file path test...");
        // selectedDocPath = getPath(uri);
        System.out.println(" Result Code "+resultCode+" request code:"+requestCode +"data:"+data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedFileURI = data.getData();
            Context context;
            context=getContext();
            fileuri=selectedFileURI;
            kcorpusutil.setmContext(context);
          //  context=this;
            String fullPath = kcorpusutil.getRealPathFromUri( selectedFileURI);
            String filePath = kcorpusutil.getRealPathFromUri(fileuri);
            ////////////////
           // if(validformsate())
           // {
            //    Toast.makeText(getActivity(), "Errors on Form Select Metadata please.", Toast.LENGTH_SHORT).show();
           // return;
           // }
          //collectformdata();
            assistantname  = ed1.getText().toString();
            speakername  = ed2.getText().toString();
            strspeakername=ed2.getText().toString();
            speakerlocation  = ed3.getText().toString();
            int rand=kcorpusutil.genrandom(1000);
            System.out.println(" selected topic"+strtopic);
            strmeta_code=assistantname+strlanguagecode+strspeechcategory+strtopic+rand;
            Toast.makeText(getActivity(), assistantname+" Metadata with code "+strmeta_code+" for date "+speechdate+" Saved Successfully!",Toast.LENGTH_LONG).show();
            metadatacode=strmeta_code;
            System.out.println(assistantname+ " "+speakername+" speaker "+speakerlocation +" gender "+speakergender+" Speaker age group "+strspeakeragegroup+" data source"+strsource+"\n"+" Speaker language"+speakerlanguage+"Speech Category "+strspeechcategory+" Speech topic "+speechtopic+" Source "+strmeta_code+"Date "+speechdate);

            //String fullPath = getRealPathFromUri(selectedFileURI);
            //  String filePath = getRealPathFromUri(selectedFileURI);
            String selectedDocPath = "";
            Uri selectedImageuri = data.getData();
            File file = new File(fullPath);
            selectedDocPath = file.getPath();
            String extension = MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath());
            String mime = kcorpusutil.getMimeType(file.getName());
            if (extension==null){
                Toast.makeText(mContext, "Null file found", Toast.LENGTH_SHORT).show();
                return;
            }
            // Toast.makeText(MetaProfileAttachMetaFileActivity.this,rnd.nextInt(15)+" I am rand called on upload functioname ",Toast.LENGTH_LONG).show();;
            //  String ext=getMimeType(filePath);
            // String extension = MimeTypeMap.getFileExtensionFromUrl(fullPath);
            //  String filePath = getRealPathFromUri(fileuri);
            // Toast.makeText(MetaProfileAttachMetaFileActivity.this,rnd.nextInt(15)+" I am rand called on upload functioname ",Toast.LENGTH_LONG).show();;
            // String ext=getMimeType(filePath);
            // String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
            //   fileupload_name="onyango"+rnd.nextInt()+"."+extension;
            fileupload_name=strmeta_code+"."+extension;
            //File file = new File(fullPath);
            strselectedabsolutefilepath=file.getName()+" Attached file renamed to "+fileupload_name;
            Toast.makeText(getContext(), " Renaming file..."+file.getName()+" to "+fileupload_name, Toast.LENGTH_LONG).show();

            textviewAudiofilelabel.setText(strselectedabsolutefilepath);
            progressDialog.setMessage("Uploading medadata file "+fileupload_name+" file please wait..");


            tvSaveUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // validformsate();
                    // kcorpusutil = new KenCorpusService();

                    //   kcorpusutil.findfile();
                    //kcorpusutil.loastore();
                    //Intent intent = new Intent(AuthorSpeakerActivity.this, MetadataSearchListActivity.class);
                    DbHandler dbHandler = new DbHandler(getActivity());
                    // dbHandler.onUpgrade(,1,2);
                    // dbHandler.insertUserDetails(assistantname,speakername,speakerlocation,strmeta_code);//ok
                    try {
                        Toast.makeText(getActivity()," Saving "+strmeta_code+" locally...",Toast.LENGTH_LONG).show();

                        dbHandler.insertfieldmetadataDetails(assistantname, speakername, speakergender, strspeakeragegroup, speakerlocation, strlanguagecode, strspeechcategory,strtopic, strsource, speechdate, "A", strmeta_code);//ok
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getActivity()," Error saving locally...",Toast.LENGTH_LONG).show();
                    }
                    userList = dbHandler.getUsers();
                    metaList=dbHandler.GetUserByUserId(1);
                    metadataList=dbHandler.getKenCorpusMetadata();
                    //metadataList=db.getUsers();
                    int list_metadata_size=metadataList.size();
                    int listsize=userList.size();
                    if (metadataList.size()>0) {System.out.println("true");

                        System.out.println(metadataList.get(0).get("designation") + " metadata size " + list_metadata_size + " on create");
                    }
                    else{
                        Toast.makeText(getActivity(), "Empty List", Toast.LENGTH_SHORT).show();
                    }
                    //    k

                    Toast.makeText(getActivity(), "Submitting "+strmeta_code+" to kencorpus ", Toast.LENGTH_SHORT).show();
                    System.out.println("submitting to kencorpus cloud");
                    System.out.println(" mc "+strmeta_code);
                    // Intent intent=new Intent(getActivity(), MetadataUploadStatusActivity.class);
                    // intent.putExtra("metalanguagecode",metalanguagecode);
                    // startActivity(intent);
                    progressDialog.setMessage("Uploading Metadata records to Kencorpus Online please wait..");
                    //upload and reverser on error.
                    //
                    // dbHandler.insertUserDetails(metaassistantname,metalocation,metalanguagecode,metadatacode);

                    // dbHandler.insertfieldmetadataDetails(metaassistantname, metaintervieweename, metainterveweegender,meta_agegroup, metalocation, metalanguagecode, metainterviewdate,metacategory, metatopic, metasource, "A", metadatacode);//ok date
                    //}
                    // catch (SQLException ex)
                    //  {
                    // Toast.makeText(MetaProfileAttachMetaFileActivity.this,"Saving on local db...  ",Toast.LENGTH_LONG).show();
                    //  }
                    try {
                        //postMetadataTask();
                        Toast.makeText(getActivity()," Saving Metadata"+metadatacode+" on kencorpus cloud db...  ",Toast.LENGTH_LONG).show();

                       // new  APIMetaDataAsyncTask().execute();//  ().execute();//
                    }catch (Exception ex)
                    {
                        Toast.makeText(getActivity()," Error Saving "+metadatacode+" on kencorpus cloud db...  ",Toast.LENGTH_LONG).show();

                        System.out.println(ex.getMessage()+" Error Storing metadat data");
                    }
                    //  progressDialog.setMessage("Uploading pdf metadata to server please wait..");
                    try {

                        // postMetadataTask();

                        uploadFile(fileuri);


                    }catch (Exception ex)
                    {

                    }

                }
            });

        }

    }

    private boolean validformsate() {
        if (ed1.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter Research Assistant Name", Toast.LENGTH_SHORT).show();
            // ed1.setTextColor(Color.parseColor("#bf170b"));
            return false;
        }
        if ( ed2.getText().toString().isEmpty()&& ed2.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter Interviewee Name and Location Details", Toast.LENGTH_SHORT).show();
            // ed1.setTextColor(Color.parseColor("#bf170b"));
            return false;
        }
        if(spinnertopics.getSelectedItem().toString().trim().equals("Select Topic")) {
            Toast.makeText(getActivity()," Metadata Topic is Required",Toast.LENGTH_SHORT).show();
            textViewTopic.setText("Topic Required");
            textViewTopic.setTextColor(Color.parseColor("#bf170b"));//#6200EE
            spinnertopics.setFocusable(true);
            return false;
        }
        if(spinnersource.getSelectedItem().toString().trim().equals("Select Source")) {
            Toast.makeText(getActivity()," Metadata Source is Required",Toast.LENGTH_SHORT).show();
            textViewSource.setText("Data Source Required");
            textViewSource.setTextColor(Color.parseColor("#bf170b"));//#6200EE
            spinnersource.setFocusable(true);
            return false;
        }
        if(spinnerspeakeragegroup.getSelectedItem().toString().trim().equals("Select Age Group")) {
            Toast.makeText(getActivity()," Metadata Age Group is Required",Toast.LENGTH_SHORT).show();
            textViewAgeGroup.setText("Age Group Required");
            textViewAgeGroup.setTextColor(Color.parseColor("#bf170b"));//#6200EE
            spinnerspeakeragegroup.setFocusable(true);
            return false;
        }
        if(spinnergender.getSelectedItem().toString().trim().equals("Select Gender")) {
            Toast.makeText(getActivity()," Metadata Gender is Required",Toast.LENGTH_SHORT).show();
            textViewGender.setText("Gender Required");
            textViewGender.setTextColor(Color.parseColor("#bf170b"));//#6200EE
            spinnergender.setFocusable(true);
            return false;
        }
        if(spinnerlanguage.getSelectedItem().toString().trim().equals("Select Language")) {
            Toast.makeText(getActivity()," Metadata Language is Required",Toast.LENGTH_SHORT).show();
            textViewLanguage.setText("Language Required");
            textViewLanguage.setTextColor(Color.parseColor("#bf170b"));//#6200EE
            spinnerlanguage.setFocusable(true);
            return false;

        }
        if(spinnercategory.getSelectedItem().toString().trim().equals("Select Category")) {
            Toast.makeText(getActivity()," Metadata Category is Required",Toast.LENGTH_SHORT).show();
            textViewSpeechCategory.setText("Category Required");
            textViewSpeechCategory.setTextColor(Color.parseColor("#bf170b"));//#6200EE
            spinnercategory.setFocusable(true);
            return false;
        }

        return true;
    }

    public void openGalleryPDF(){

        Intent intent = new Intent();
        intent.setType("*/*");
        Toast.makeText(getContext(),"Selecting Document Files metadata for attachment..." , Toast.LENGTH_LONG).show();

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select pdf "), 1);
    }
    ////////////////////////////////
    /*username:onyash
interviewdate:2021
source:community
shortdescription:twende
speakername:Owuor
gender:Male
agegroup:Senior
location:Kisumu
language:SWA
category:Folklore
topic:Culture*/
    private void recordCorpusMetadata(String username,String interviewdate,
                                String Source,String speakername,String gender,String agegroup,
                                String location, String language,String category, String topic,Uri fileUri) {
       kcorpusutil.setmContext(getActivity());
        Random rnd = new Random();
        rnd.setSeed(100);
        String filePath = kcorpusutil.getRealPathFromUri(fileUri);
        // Toast.makeText(MetaProfileAttachMetaFileActivity.this,rnd.nextInt(15)+" I am rand called on upload functioname ",Toast.LENGTH_LONG).show();;
      //  String ext=kcorpusutil.getMimeType(filePath);
        String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
        //   fileupload_name="onyango"+rnd.nextInt()+"."+extension;
//fileupload_name=metadatacode+"."+extension;
        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            // if (file.lastIndexOf(".") >= 0) {
            //   String prefixuploadfilename="Onyango"+Math.random();
            // String suffix_extension="";

            String type="";
            final String fileuploadname=file.getName();
            if (extension != null) {
                // suffix_extension=extension;
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                // fileuploadname=prefixuploadfilename+suffix_extension;
                //TypedFile typedFile = new TypedFile(type, new File(path));
                //  Toast.makeText(MetaProfileAttachMetaFileActivity.this,strselectedabsolutefilepath+" ext ia a ."+extension +" mime "+type+" new file name "+fileupload_name,Toast.LENGTH_LONG).show();;

            }

            //     file = new File(resumePath + fileName.substring(fileName.lastIndexOf("\\")));
            //TypedFile typedFile = new TypedFile(type, new File(path));
            // Toast.makeText(MetaProfileAttachMetaFileActivity.this,"The file's "+" ext ."+extension +" mime "+type+" "+fileupload_name,Toast.LENGTH_LONG).show();;

            //   }

            if (file.exists()) {
                // file.renameTo("kencorpus");
                OkHttpClient okHttpClient=new OkHttpClient.Builder()
                        .connectTimeout(340, TimeUnit.SECONDS)
                        .readTimeout(340,TimeUnit.SECONDS)
                        .writeTimeout(340,TimeUnit.SECONDS)
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://kencorpus.co.ke/")
                        .client(okHttpClient)
                        .build();

                WebAPIService service = retrofit.create(WebAPIService.class);

                // creates RequestBody instance from file
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                // MultipartBody.Part is used to send also the actual filename
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileupload_name, requestFile);
                // MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                // adds another part within the multipart request
                String descriptionString = "Sample description";
                RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
                // executes the request
                Call<ResponseBody> call = service.postFile(body, description);
                progressDialog.show();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        Log.i(LOG_TAG, "success");
                        progressDialog.dismiss();


                      /*  ResponseBody rsb=new ResponseBody() {
                            @javax.annotation.Nullable
                            @Override
                            public MediaType contentType() {
                                return null;
                            }

                            @Override
                            public long contentLength() {
                                return 0;
                            }

                            @Override
                            public BufferedSource source() {
                                return null;
                            }
                        }*/
                        if(response.isSuccessful())
                        {
                            Date date;
                            // DateFormat df=new DateFormat();
                            //df.format(date,"12")
                            // date=new Date();
                            Toast.makeText(getActivity()," Metadata file "+fileuploadname+"  uploaded to cloud as "+fileupload_name+" successfully."+response.code() , Toast.LENGTH_LONG).show();

                            ////////////////
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {


                                    ///only on success////////
                                    Intent intent = new Intent(getActivity(), MetadataUploadStatusActivity.class);

                                    System.out.println("metacode "+metadatacode);
                                    intent.putExtra("metadatacode",metadatacode);
                                    intent.putExtra("metaassistantname",metaassistantname);
                                    intent.putExtra("speakername",metaintervieweename);
                                    intent.putExtra("meta_agegroup",meta_agegroup);

                                    intent.putExtra("metainterveweegender",metainterveweegender);
                                    intent.putExtra("metalocation",metalocation);
                                    intent.putExtra("metainterviewdate",metainterviewdate);
                                    intent.putExtra("metacategory",metacategory);
                                    intent.putExtra("metatopic",metatopic);

                                    intent.putExtra("metasource",metasource);
                                    intent.putExtra("metalanguagecode",metalanguagecode);
                                    intent.putExtra("metainterviewdate",metainterviewdate);


                                    System.out.println("metalanguage "+metalanguage);

                                    System.out.println("metalanguagecode "+metalanguagecode);

                                    System.out.println("metainterviewdate "+metainterviewdate);








                                    System.out.println(" redirected...to stored profile");

                                    startActivity(intent);
//2000 words//recording
                                }
                            },200);

                            //////////
                        }else
                        {
                            Toast.makeText(getActivity(),"Metadata file upload encountered errors."+response.code() , Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(LOG_TAG, t.getMessage());
                        Toast.makeText(getActivity(),"Metadata file upload failed"+t.getMessage() , Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }
    private void uploadFile(Uri fileUri) {
        kcorpusutil.setmContext(getActivity());
        Random rnd = new Random();
        rnd.setSeed(100);
        String filePath = kcorpusutil.getRealPathFromUri(fileUri);
        // Toast.makeText(MetaProfileAttachMetaFileActivity.this,rnd.nextInt(15)+" I am rand called on upload functioname ",Toast.LENGTH_LONG).show();;
        //  String ext=kcorpusutil.getMimeType(filePath);
        String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
        //   fileupload_name="onyango"+rnd.nextInt()+"."+extension;
//fileupload_name=metadatacode+"."+extension;
        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            // if (file.lastIndexOf(".") >= 0) {
            //   String prefixuploadfilename="Onyango"+Math.random();
            // String suffix_extension="";

            String type="";
            final String fileuploadname=file.getName();
            if (extension != null) {
                // suffix_extension=extension;
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                // fileuploadname=prefixuploadfilename+suffix_extension;
                //TypedFile typedFile = new TypedFile(type, new File(path));
                //  Toast.makeText(MetaProfileAttachMetaFileActivity.this,strselectedabsolutefilepath+" ext ia a ."+extension +" mime "+type+" new file name "+fileupload_name,Toast.LENGTH_LONG).show();;

            }

            //     file = new File(resumePath + fileName.substring(fileName.lastIndexOf("\\")));
            //TypedFile typedFile = new TypedFile(type, new File(path));
            // Toast.makeText(MetaProfileAttachMetaFileActivity.this,"The file's "+" ext ."+extension +" mime "+type+" "+fileupload_name,Toast.LENGTH_LONG).show();;

            //   }

            if (file.exists()) {
                // file.renameTo("kencorpus");
                OkHttpClient okHttpClient=new OkHttpClient.Builder()
                        .connectTimeout(340, TimeUnit.SECONDS)
                        .readTimeout(340,TimeUnit.SECONDS)
                        .writeTimeout(340,TimeUnit.SECONDS)
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://kencorpus.co.ke/")
                        .client(okHttpClient)
                        .build();

                WebAPIService service = retrofit.create(WebAPIService.class);
                ApiClientAdapter service2=new ApiClientAdapter();

                // creates RequestBody instance from file
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                // MultipartBody.Part is used to send also the actual filename
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileupload_name, requestFile);
                // MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                // adds another part within the multipart request
              //  MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileupload_name, requestFile);
                // MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                // adds another part within the multipart request
            //    RequestBody speakername = RequestBody.create(MediaType.parse("multipart/form-data"),speakername);

                String descriptionString = "Sample description";
                String strlocation="Kisumu";
                RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
                // executes the request

                RequestBody location = RequestBody.create(MediaType.parse("text/plain"), strsource);
               // Call<ResponseBody> call = service.postFile(body, location);
                /*  */
               // String descriptionString = "Sample description";
                System.out.println(assistantname+ "from post...ndani... "+speakername+" speaker "+speakerlocation +" gender "+speakergender+" Speaker age group "+strspeakeragegroup+" data source"+strsource+"  Speaker language"+strlanguage+"Speech Category "+strspeechcategory+" Speech topic "+strtopic+" Source "+strsource+"Date "+speechdate+" meta data code"+strmeta_code);

                RequestBody speakername = RequestBody.create(MediaType.parse("text/plain"), strspeakername);
                RequestBody username = RequestBody.create(MediaType.parse("text/plain"), assistantname);

               // RequestBody speakername = RequestBody.create(MediaType.parse("multipart/form-data"),speakername);
                RequestBody interviewdate = RequestBody.create(MediaType.parse("text/plain"), speechdate);
                RequestBody source = RequestBody.create(MediaType.parse("text/plain"), strsource);
               // RequestBody location = RequestBody.create(MediaType.parse("text/plain"), speakerlocation);
                RequestBody agegroup = RequestBody.create(MediaType.parse("text/plain"), strspeakeragegroup);
                RequestBody language = RequestBody.create(MediaType.parse("text/plain"), strlanguage);
                RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), speakergender);
                RequestBody topic = RequestBody.create(MediaType.parse("text/plain"), strtopic);
                RequestBody category = RequestBody.create(MediaType.parse("text/plain"), strspeechcategory);
                Call<ResponseBody> call=null;
               try {

                 //  call = service.postFile(body, location);
                call= service.postrecordmetadata(body,username,speakername,interviewdate,source,location,agegroup,language,gender,topic,category);

               }
               catch (Exception ex)
               {

                   Toast.makeText(getActivity(), "Failure posting....", Toast.LENGTH_SHORT).show();
              return;
               }


                 progressDialog.show();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           final Response<ResponseBody> response) {
                        Log.i(LOG_TAG, "success");
                        progressDialog.dismiss();


                      /*  ResponseBody rsb=new ResponseBody() {
                            @javax.annotation.Nullable
                            @Override
                            public MediaType contentType() {
                                return null;
                            }

                            @Override
                            public long contentLength() {
                                return 0;
                            }

                            @Override
                            public BufferedSource source() {
                                return null;
                            }
                        }*/
                        if(response.isSuccessful())
                        {
                            Date date;
                            // DateFormat df=new DateFormat();
                            //df.format(date,"12")
                            // date=new Date();
                            Gson gson = new Gson();
                         // ResponseBody responseBody = client.newCall(request).execute().body();
                         //todo: Cra
                        //   Response201 entity = gson.fromJson(response.body().toString(), Response201.class);
                           // System.out.println("Status "+entity.getStatus());
                        //  System.out.println(" Url "+entity.getUrl());
                           // entity.
                           // SimpleEntity sampleResponse;
                           // sampleResponse=new SimpleEntity("ok","http://kencorpus.co.ke");
                           // Assert.assertNotNull(entity);
                           // Assert.assertEquals(sampleResponse.getUrl(), entity.getUrl());
                           // Toast.makeText(getActivity(),"Metadata "+entity.getUrl()+" file "+fileuploadname+"  uploaded to cloud as "+fileupload_name+" successfully."+response.code() , Toast.LENGTH_LONG).show();

                            ////////////////
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {


                                    ///only on success////////
                                    Intent intent = new Intent(getActivity(), MetadataUploadStatusActivity.class);

                                    System.out.println("metacode "+metadatacode);
                                    intent.putExtra("metadatacode",metadatacode);
                                    intent.putExtra("metaassistantname",metaassistantname);
                                    intent.putExtra("speakername",metaintervieweename);
                                    intent.putExtra("meta_agegroup",meta_agegroup);

                                    intent.putExtra("metainterveweegender",response.body().toString());
                                    intent.putExtra("metalocation",metalocation);
                                    intent.putExtra("metainterviewdate",metainterviewdate);
                                    intent.putExtra("metacategory",metacategory);
                                    intent.putExtra("metatopic",metatopic);

                                    intent.putExtra("metasource",metasource);
                                    intent.putExtra("metalanguagecode",metalanguagecode);
                                    intent.putExtra("metainterviewdate",metainterviewdate);


                                    System.out.println("metalanguage "+metalanguage);

                                    System.out.println("metalanguagecode "+metalanguagecode);

                                    System.out.println("metainterviewdate "+metainterviewdate);








                                    System.out.println(" redirected...to stored profile");

                                   // startActivity(intent);
//2000 words//recording
                                }
                            },200);

                            //////////
                        }else
                        {
                            Toast.makeText(getActivity(),"Metadata file upload encountered errors."+response.code() , Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(LOG_TAG, t.getMessage());
                        Toast.makeText(getActivity(),"Metadata file upload failed"+t.getMessage() , Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }

    ///////////////////
///////////////////////////custom methods/////////////////
private void updateLabel() {
    String msg=textViewdateset.getText().toString();
    Date date=myCalendar.getTime();

    msg=myCalendar.getTime().toString();
    String dateselect = new SimpleDateFormat("yyyy-MM-dd").format(date);
    System.out.println(dateselect+ " today interview date.");
    textViewdateset.setText("Interview Date: "+dateselect);
    speechdate=dateselect;
}
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_dashboard_browsefile) {
            openGalleryPDF();
            Toast.makeText(getContext()," Attaching Metadata file "  , Toast.LENGTH_LONG).show();

            //if (null != selectedPath && !selectedPath.isEmpty()) {
            // selectedPath=filepath.getAbsolutePath();
            TextView textViewfilepath;
            // String prefixuploadfilename="Onyango."+Math.random();
            String suffix_extension="";
            //  String extension = MimeTypeMap.getFileExtensionFromUrl(fileuri.getPath());
            textviewAudiofilelabel.setText("Brose file set...on click browse");

            // // textViewfilepath=(TextView)findViewById(R.id.tvprofilemetadata_path_filename);
            //   textViewfilepath.setText("attaching file..." + selectedPath);
            String type="";
            //  if (extension != null) {
            // type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

            //TypedFile typedFile = new TypedFile(type, new File(path));
            //Toast.makeText(MetaProfileAttachMetaFileActivity.this,strselectedabsolutefilepath+" ext "+extension +" mime "+type,Toast.LENGTH_LONG).show();;
            //}
        }
        if (v.getId() == R.id.tv_dashboard_save_upload) {
            //   openGalleryPDF();
            Toast.makeText(getActivity(), " Local save and upload", Toast.LENGTH_LONG).show();
        }
    }
    ///////////////////////////////////////////////////////get real

}