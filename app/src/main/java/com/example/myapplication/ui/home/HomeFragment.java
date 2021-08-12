package com.example.myapplication.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class HomeFragment extends Fragment {
    TextView textViewAssistantMetadata;
    private HomeViewModel homeViewModel;
    private String username;

    @SuppressLint("FragmentLiveDataObserve")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

       // textViewAssistantMetadata.setText(username);
        textView.setText("Onyango"+" Metadata list");
        //homeViewModel();
        //String result = bundle.getString("bundleKey");
          //  String myname=getArguments().getString("metadataassistantusername");
        //todo: Load details from form signup. 2021 08-10 14h
        String username="maconyash";
//     username= getActivity().getIntent().getExtras().getString("metadataassistantusername");
       homeViewModel.setText(username);
       Bundle result = new Bundle();
       result.putString("bundleKey", username);
       getParentFragmentManager().setFragmentResult("requestKey", result);
        textView.setText(username+" Metadata List"); //  if(getArguments() != null )
      //  {
       // if (new MainActivity().getIntent().hasExtra("metadataassistantusername")) {

         // String username = requireActivity().getIntent().getStringExtra("metadataassistantusername");

           // String username = getArguments().getString("username");
         //   System.out.println("access  granted.."+getArguments().getString("username"));
            Toast.makeText(getActivity(), "user passed is "+username, Toast.LENGTH_SHORT).show();
       // }
        // }
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}