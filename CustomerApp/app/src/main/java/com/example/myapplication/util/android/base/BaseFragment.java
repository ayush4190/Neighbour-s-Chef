package com.example.myapplication.util.android.base;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

/**
 * Base fragment for enabling ViewBinding support
 * @param <VB> Generated ViewBinding class associated with this fragment
 *
 * onCreateView() contains only 2 lines
 *            binding = VB.inflate(inflater, container, false);
 *            return binding.getRoot();
 *
 * Views must be setup in onViewCreated()
 */
public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {
    protected VB binding;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
