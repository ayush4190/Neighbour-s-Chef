package com.example.myapplication.ui.fragment.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.FragmentHelpBinding;
import com.example.myapplication.util.android.base.BaseFragment;

import static com.example.myapplication.util.android.Utility.sendEmail;

public class HelpFragment extends BaseFragment<FragmentHelpBinding> {
    private HelpFragment() {}

    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("Help");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHelpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.helpEmailSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(requireContext(),
                        binding.helpEmailSubject.getText().toString(),
                        binding.helpEmailContent.getText().toString()
                );
            }
        });
    }
}

