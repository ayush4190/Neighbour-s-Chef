package com.example.vendorsapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URL;
import java.util.Objects;

public class AddItems extends Fragment  {

   private EditText item_name , price , quantity , item_id ;
    private ImageView mImageView ;
    private View view ;
    private Button mButton ;
    private ItemDetail mitemDetail = new ItemDetail() ;
    private int count = 4; //number of items that need to be added
    boolean status= false;
    private int id = 0; // how to create autoincrement and checks on number of fields
    private String food_id;
    private Spinner spinner;
    //create a list of items for the spinner.
    String[] items = new String[]{"Select" ,"Today's menu", "Tomorrows menu", "Rest of the week"};

    private DatabaseReference databaseReference  ;
    public AddItems() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      view =  inflater.inflate(R.layout.fragment_add_items,container,false);


        // creating init function to assign all id to the variables
        init();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(item_name.getText().toString()))
                {
                    item_name.setError("Field cannot be left blank");
                    count -- ;
                }
                if(TextUtils.isEmpty(price.getText().toString()))
                {
                    price.setError("Field cannot be left blank");
                    count --;
                }
                if(TextUtils.isEmpty(quantity.getText().toString()))
                {
                    quantity.setError("Field cannot be left blank");
                    count--;
                }
                if(TextUtils.isEmpty(item_id.getText().toString()))
                {
                    item_id.setError("Field cannot be left blank");
                    count --;
                }

    if(count == 4) {
        assignValues();
    }

            }
        });

        return view ;
    }

    private void init() {

        item_name = (EditText) view.findViewById(R.id.item_name);
        price =(EditText) view.findViewById(R.id.price);
        quantity =(EditText) view.findViewById(R.id.packet_quantity);
        item_id =  (EditText) view.findViewById(R.id.Food_Id);
        item_id.setEnabled(true);
        mImageView = (ImageView) view.findViewById(R.id.item_image);
        mButton = (Button) view.findViewById(R.id.add_item_button);
        view.requestFocus();

        spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items)
        {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
                if (position == 0) {
                    textview.setTextColor(Color.GREEN);
                } else {
                    textview.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // uncomment this when publishing the app ;
      //  databaseReference = FirebaseDatabase.getInstance().getReference().child("Production");
        food_id = String.valueOf(id +1);
//        item_id.setText(food_id);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Development").child("3");



    }

    private void assignValues()
    {

        mitemDetail.setProduct_name(item_name.getText().toString());
        mitemDetail.setProduct_price(price.getText().toString());
        mitemDetail.setProduct_quantity(quantity.getText().toString());
        mitemDetail.setProduct_id(food_id);


      databaseReference.setValue(mitemDetail);

        databaseReference.setValue(mitemDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(),"item added Succefully",Toast.LENGTH_SHORT).show();
                id = id+1 ;
                cleartext();
//                startActivity(new Intent());
                }
        });




    }

    private void cleartext()
    {
        item_id.setText("");
        price.setText("");
        item_name.setText("");
        quantity.setText("");
    }

// check for on back press


}
