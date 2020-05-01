package com.example.myapplication.ui.fragment.profile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProfileBinding;
import com.example.myapplication.util.android.base.BaseFragment;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("Profile");
    }

    private ProfileFragment() {}

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textProfileUpdateMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_set_mobile,(ViewGroup) view.findViewById(R.id.dialog_mobile));
                new android.app.AlertDialog.Builder(getActivity()).setTitle("Please Input Contact Information").setIcon(
                        android.R.drawable.ic_dialog_dialer).setView(
                        layout).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dialog dialog = (Dialog) dialogInterface;
                        EditText inputMobile = dialog.findViewById(R.id.dialog_et_mobile);
                        if (inputMobile.getText().toString().isEmpty()) {
                            return;
                        }
                        try {
                            long number = Long.parseLong(inputMobile.getText().toString());


                        } catch (Exception e){
                            Toast.makeText(getActivity(), "Please Input Correct Phone Number!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", null).show();
            }
        });

        binding.textProfileUpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textProfileOldPassword.setText("Mobile:");
                binding.editProfileOldPassword.setHint("Phone Number");
                binding.textProfileNewPassword.setText("Password:");
                binding.editProfileNewPassword.setHint("Password");
                binding.textProfileRetypeNewPassword.setText("New Address");
                binding.editProfileRetypeNewPassword.setHint("Address");
                binding.linearLayoutProfilePasswordUpdation.setVisibility(View.VISIBLE);
                binding.buttonProfileConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phone = binding.editProfileOldPassword.getText().toString();
                        String password = binding.editProfileNewPassword.getText().toString();
                        String address = binding.editProfileRetypeNewPassword.getText().toString();

                    }
                });


            }
        });
        binding.linearLayoutProfilePasswordUpdation.setVisibility(View.INVISIBLE);
        binding.textProfileUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textProfileOldPassword.setText("Old Password:");
                binding.editProfileOldPassword.setHint("Old Password");
                binding.textProfileNewPassword.setText("New Password:");
                binding.editProfileNewPassword.setHint("New Password");
                binding.textProfileRetypeNewPassword.setText("Retype:");
                binding.editProfileRetypeNewPassword.setHint("Retype Password");
                binding.linearLayoutProfilePasswordUpdation.setVisibility(View.VISIBLE);
                binding.buttonProfileConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String oldPwd = binding.editProfileOldPassword.getText().toString();
                        String newPwd = binding.editProfileNewPassword.getText().toString();
                        String newPwd2 = binding.editProfileRetypeNewPassword.getText().toString();

                    }
                });


            }
        });

        binding.buttonProfileCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.linearLayoutProfilePasswordUpdation.setVisibility(View.INVISIBLE);
                binding.textProfileOldPassword.setText("");
                binding.textProfileNewPassword.setText("");
                binding.textProfileRetypeNewPassword.setText("");
            }
        });
    }
}
