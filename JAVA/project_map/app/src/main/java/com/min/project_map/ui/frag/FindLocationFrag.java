package com.min.project_map.ui.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.min.project_map.R;
import com.min.project_map.data.vo.BookVo;
import com.min.project_map.data.vo.BooksVo;
import com.min.project_map.utils.GpsTracker;

public class FindLocationFrag extends Fragment implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnCameraIdleListener {
    public static final String TAG = FindLocationFrag.class.getSimpleName();

    // view
    private View mView;
    private TextView tvAAddress, tvBAddress;
    private Button btnV;

    private GoogleMap mMap;
    private GpsTracker gpsTracker;
    private int btnVCount = 0;
    private String markerAddress;
    private BooksVo mapBooksVo;

    public static FindLocationFrag newInstance() {
        Bundle args = new Bundle();

        FindLocationFrag fragment = new FindLocationFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frag_find_location, container, false);

        tvAAddress = mView.findViewById(R.id.tv_a_address);
        tvBAddress = mView.findViewById(R.id.tv_b_address);
        btnV = mView.findViewById(R.id.btn_v);

        gpsTracker = new GpsTracker(getContext());
        mapBooksVo = new BooksVo();
        initMap();
        return mView;
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        double lat = gpsTracker.getLatitude();
        double lng = gpsTracker.getLongitude();
        mMap = googleMap;

        // Map 드래그 후 기능 추가시 사용
//        mMap.setOnCameraIdleListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(lat, lng), 16));
    }

    @Override
    public void onCameraIdle() {
        // Map 드래그 후 기능 필요시 추가
    }

    @Override
    public void onClick(View v) {
        if(v == btnV) {

        }
    }

    private void btnVClickAction(String address, int btnVCount) {
        LatLng centerLatLng = getCenterLatLng();
        double centerLat = centerLatLng.latitude;
        double centerLng = centerLatLng.longitude;
        String btnText = "";
        BookVo bookVo = new BookVo(centerLat, centerLng, 0, getAddress(centerLat, centerLng));

        switch (btnVCount) {
            case 0:
                btnText = getString(R.string.btn_v_text_a);
                mapBooksVo.setA(bookVo);
                break;
            case 1:
                btnText = getString(R.string.btn_v_text_b);
                mapBooksVo.setB(bookVo);
                break;
            case 2:
                btnText = getString(R.string.btn_v_text_book);
                // TODO: 2022-03-28 add fragment replace code
                break;
            default:
                btnVCount = 0;
        }

        btnV.setText(btnText);
        tvAAddress.setText(address);
        this.btnVCount++;
    }

    private LatLng getCenterLatLng() {
        return mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
    }

    private String getAddress(double lat, double lng) {
        return gpsTracker.getCurrentAddress(getContext(), lat, lng);
    }

}
