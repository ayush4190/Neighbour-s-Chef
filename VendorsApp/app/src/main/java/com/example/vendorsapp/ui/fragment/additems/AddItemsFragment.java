package com.example.vendorsapp.ui.fragment.additems;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.vendorsapp.databinding.FragmentAddItemsBinding;
import com.example.vendorsapp.model.Product;
import com.example.vendorsapp.util.android.base.BaseFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItemsFragment extends BaseFragment<FragmentAddItemsBinding> {
    private Product product = new Product() ;
    private int count = 4; //number of items that need to be added
    boolean status = false;
    private static int id = 1; // how to create autoincrement and checks on number of fields
    private String foodId, menuDate;
    //create a list of items for the spinner.
    private String[] items = new String[]{"Select" ,"Today's menu", "Tomorrows menu", "Rest of the week"};

    private AddItemsFragment() {}

    public static AddItemsFragment newInstance() {
        return new AddItemsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddItemsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        binding.addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(binding.itemName.getText().toString())) {
                    binding.itemName.setError("Field cannot be left blank");
                    count -- ;
                }
                if(TextUtils.isEmpty(binding.price.getText().toString())) {
                    binding.price.setError("Field cannot be left blank");
                    count --;
                }
                if(TextUtils.isEmpty(binding.packetQuantity.getText().toString())) {
                    binding.packetQuantity.setError("Field cannot be left blank");
                    count--;
                }


                if (count == 4) {
                    assignValues();
                }

            }
        });
    }

    private void init() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
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
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                menuDate = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        foodId = autoIncrement();
        binding.FoodId.setText(foodId);
    }

    private void assignValues() {
        product.setName(binding.itemName.getText().toString());
        product.setPrice(binding.price.getText().toString());
        product.setQuantity(binding.packetQuantity.getText().toString());
        product.setId(foodId);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Development").child(menuDate).child(foodId);

        databaseReference.setValue(product);

        databaseReference.setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(),"item added Succefully",Toast.LENGTH_SHORT).show();
                cleartext();

            }
        });
    }

    private void cleartext() {
        binding.FoodId.setText("");
        binding.price.setText("");
        binding.itemName.setText("");
        binding.packetQuantity.setText("");
    }

    private String autoIncrement() {
        id = id +1 ;
        return String.valueOf(id);
    }
}
