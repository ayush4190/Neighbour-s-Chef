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

        binding.profileUpdateMobile.setOnClickListener(new View.OnClickListener() {
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

        binding.profileUpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.profileTvPwd.setText("Mobile:");
                binding.profileOldpwd.setHint("Phone Number");
                binding.profileNewpwd.setText("Password:");
                binding.profileNewpwd.setHint("Password");
                binding.profileTvRepwd.setText("New Address");
                binding.profileNewpwd2.setHint("Address");
                binding.profilePwdLinear.setVisibility(View.VISIBLE);
                binding.profileConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phone = binding.profileOldpwd.getText().toString();
                        String password = binding.profileNewpwd.getText().toString();
                        String address = binding.profileNewpwd2.getText().toString();

                    }
                });


            }
        });
        binding.profilePwdLinear.setVisibility(View.INVISIBLE);
        binding.profileUpdatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.profileTvPwd.setText("Old Password:");
                binding.profileOldpwd.setHint("Old Password");
                binding.profileTvNewpwd.setText("New Password:");
                binding.profileNewpwd.setHint("New Password");
                binding.profileTvRepwd.setText("Retype:");
                binding.profileNewpwd2.setHint("Retype Password");
                binding.profilePwdLinear.setVisibility(View.VISIBLE);
                binding.profileConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String oldPwd = binding.profileOldpwd.getText().toString();
                        String newPwd = binding.profileNewpwd.getText().toString();
                        String newPwd2 = binding.profileNewpwd2.getText().toString();
                        /*------use checkOldPwd() to check old pwd--------*/
//                        if (!checkMatch(newPwd, newPwd2)) {
//                            Toast.makeText(getContext(), "New password does not match", Toast.LENGTH_LONG).show();
//                            mEditNewPwd.setText("");
//                            mEditOldPwd.setText("");
//                            mEditNewPwd2.setText("");
//                            return;
//                        }
//                        postUpdatePwd(oldPwd, newPwd);
                    }
                });


            }
        });

        binding.profileCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.profilePwdLinear.setVisibility(View.INVISIBLE);
                binding.profileOldpwd.setText("");
                binding.profileNewpwd.setText("");
                binding.profileNewpwd2.setText("");
            }
        });
    }
}
