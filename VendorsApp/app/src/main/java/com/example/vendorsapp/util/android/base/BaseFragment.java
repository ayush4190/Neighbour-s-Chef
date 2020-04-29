package com.example.vendorsapp.util.android.base;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {
    protected VB binding;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
