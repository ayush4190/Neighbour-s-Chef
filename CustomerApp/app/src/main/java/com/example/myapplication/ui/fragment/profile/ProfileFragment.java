package com.example.myapplication.ui.fragment.profile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class ProfileFragment extends Fragment {

    private TextView mTextMobile, mTextAddress;
    private TextView mTextUpdateMobile, mTextUpdateAddress, mTextUpdatePwd;
    private EditText mEditOldPwd, mEditNewPwd, mEditNewPwd2;
    private Button mButtonConfirm, mButtonCancel;
    private LinearLayout mLinearPwd;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Profile");
    }
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mTextMobile = (TextView) view.findViewById(R.id.profile_mobile);

        mTextAddress = (TextView) view.findViewById(R.id.profile_address);

        mTextUpdateMobile = (TextView) view.findViewById(R.id.profile_update_mobile);

        mTextUpdateMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_set_mobile,(ViewGroup) view.findViewById(R.id.dialog_mobile));
                new android.app.AlertDialog.Builder(getActivity()).setTitle("Please Input Contact Information").setIcon(
                        android.R.drawable.ic_dialog_dialer).setView(
                        layout).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dialog dialog = (Dialog) dialogInterface;
                        EditText inputMobile = (EditText) dialog.findViewById(R.id.dialog_et_mobile);
                        if (inputMobile.getText().toString().isEmpty()){
                            return;
                        }
                        try{
                            long number = Long.valueOf(inputMobile.getText().toString());


                        }catch (Exception e){
                            Toast.makeText(getActivity(), "Please Input Correct Phone Number!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", null).show();
            }
        });


        mEditOldPwd = (EditText) view.findViewById(R.id.profile_oldpwd);
        mEditNewPwd = (EditText) view.findViewById(R.id.profile_newpwd);
        mEditNewPwd2 = (EditText) view.findViewById(R.id.profile_newpwd2);
        mTextUpdateAddress = (TextView)view.findViewById(R.id.profile_update_address);
        mTextUpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView mPhone = (TextView) mLinearPwd.findViewById(R.id.profile_tv_pwd);
                mEditOldPwd.setHint("Phone Number");
                mPhone.setText("Mobile:");
                TextView mPwd = (TextView) mLinearPwd.findViewById(R.id.profile_tv_newpwd);
                mPwd.setText("Password:");
                mEditNewPwd.setHint("Password");
                TextView mAddr = (TextView) mLinearPwd.findViewById(R.id.profile_tv_repwd);
                mAddr.setText("New Address");
                mEditNewPwd2.setHint("Address");
                mLinearPwd.setVisibility(View.VISIBLE);
                mButtonConfirm = (Button) mLinearPwd.findViewById(R.id.profile_confirm_button);
                mButtonConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phone = mEditOldPwd.getText().toString();
                        String password = mEditNewPwd.getText().toString();
                        String address = mEditNewPwd2.getText().toString();

                    }
                });


            }
        });
        mLinearPwd = (LinearLayout) view.findViewById(R.id.profile_pwd_linear);
        mLinearPwd.setVisibility(View.INVISIBLE);
        mTextUpdatePwd = (TextView) view.findViewById(R.id.profile_update_pwd);
        mTextUpdatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView mPhone = (TextView) mLinearPwd.findViewById(R.id.profile_tv_pwd);
                mPhone.setText("Old Password:");
                mEditOldPwd.setHint("Old Password");
                TextView mPwd = (TextView) mLinearPwd.findViewById(R.id.profile_tv_newpwd);
                mPwd.setText("New Password:");
                mEditNewPwd.setHint("New Password");
                TextView mAddr = (TextView) mLinearPwd.findViewById(R.id.profile_tv_repwd);
                mAddr.setText("Retype:");
                mEditNewPwd2.setHint("Retype Password");
                mLinearPwd.setVisibility(View.VISIBLE);
                mButtonConfirm = (Button) mLinearPwd.findViewById(R.id.profile_confirm_button);
                mButtonConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String oldPwd = mEditOldPwd.getText().toString();
                        String newPwd = mEditNewPwd.getText().toString();
                        String newPwd2 = mEditNewPwd2.getText().toString();
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


        mButtonCancel = (Button) view.findViewById(R.id.profile_cancel_button);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLinearPwd.setVisibility(View.INVISIBLE);
                mEditNewPwd.setText("");
                mEditOldPwd.setText("");
                mEditNewPwd2.setText("");
            }
        });
        return view;
    }

}
