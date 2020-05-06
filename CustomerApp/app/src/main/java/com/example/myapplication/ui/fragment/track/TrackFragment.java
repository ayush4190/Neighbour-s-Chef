package com.example.myapplication.ui.fragment.track;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentTrackBinding;
import com.example.myapplication.model.Order;
import com.example.myapplication.util.android.base.BaseFragment;

import timber.log.Timber;

public class TrackFragment extends BaseFragment<FragmentTrackBinding> {
//    private final String baseUrl = "http://rjtmobile.com/ansari/fos/fosapp/order_track.php?&order_id=";

    private int orderId;
    private Order order;

    private final int[] imageResources = {R.mipmap.pack, R.mipmap.delivery, R.mipmap.fork, R.mipmap.alert};
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("Track");
    }

    private TrackFragment() {}

    public static TrackFragment newInstance() {
        return new TrackFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrackBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.trackSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    orderId = Integer.parseInt(binding.trackEditSearch.getText().toString().trim());
                    binding.trackDetailBlock.setVisibility(View.VISIBLE);
//                    getData(fragView);
                    /*--------insert code to get data---*/
                } catch (Exception e){
                    e.printStackTrace();
                    Timber.d(e);
                    Toast.makeText(getActivity(), "Wrong Id Format. Please Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Bundle orderBundle = this.getArguments();
        if (orderBundle != null) {
            orderId = orderBundle.getInt("OrderId");
//            getData(fragView);
            binding.trackDetailBlock.setVisibility(View.VISIBLE);
        } else {
            binding.trackDetailBlock.setVisibility(View.INVISIBLE);
        }
    }

    //    private void getData(final View view){
//        final String TAG = "TRACK_FRAGMENT";
//        HomePageActivity.showPDialog();
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, buildUrl(), null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                Log.d(TAG, jsonObject.toString());
//
//                try{
//                    JSONArray orderArray = jsonObject.getJSONArray("Order Detail");
//                    for (int i = 0; i < orderArray.length(); i++) {
//                        JSONObject c = orderArray.getJSONObject(i);
//
//                        int id = c.getInt("OrderId");
//                        double totalOrder = c.getDouble("TotalOrder");
//                        String status = c.getString("OrderStatus");
//                        String date = c.getString("OrderDate");
//
////                        order.setId(id);
////                        order.setTotal(totalOrder);
////                        order.setStatus(status);
////                        order.setDate(date);
//                    }
//                    displayData(view);
//                }catch (Exception e){
//                    System.out.println(e);
//                }
//                MainActivity.disPDialog();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                VolleyLog.d(TAG, "ERROR" + volleyError.getMessage());
//                Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
//                HomePageActivity.disPDialog();
//            }
//        });
//        Log.e("URL", jsonObjReq.getUrl());
//        VolleyController.getInstance().addToRequestQueue(jsonObjReq);
//    }
//
//    private String buildUrl() {
//        return baseUrl + orderId;
//    }
//
//    private void displayData(View view) {
//        mTextID = (TextView) view.findViewById(R.id.track_id);
//        mTextDate = (TextView) view.findViewById(R.id.track_date);
//        mTextTotal = (TextView) view.findViewById(R.id.track_total);
//        mTextStatus = (TextView) view.findViewById(R.id.track_status);
//        mImageStatus = (ImageView) view.findViewById(R.id.track_image_status);
//        /*------using parseWord to get the string, parseImage to get the resource id-----*/
//
////        mTextID.setText("" + order.getId());
////        mTextDate.setText(order.getDate());
////        mTextTotal.setText("" + order.getTotal());
////        mTextStatus.setText(parseWord(order.getStatus()));
////        mImageStatus.setImageResource(parseImage(order.getStatus()));
//
//    }

    private String parseWord(String s) {
        switch (s) {
            case "1":
                return "Packing";
            case "2":
                return "On the way";
            case "3":
                return "Delivered";
            default:
                return "Error";
        }
    }

    private int parseImage(String s) {
        switch (s) {
            case "1":
                return imageResources[0];
            case "2":
                return imageResources[1];
            case "3":
                return imageResources[2];
            default:
                return imageResources[3];
        }
    }

}

