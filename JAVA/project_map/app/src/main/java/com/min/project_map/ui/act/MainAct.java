package com.min.project_map.ui.act;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.min.project_map.BaseApp;
import com.min.project_map.R;
import com.min.project_map.data.api.RetrofitApi;
import com.min.project_map.data.api.RetrofitModule;
import com.min.project_map.data.vo.BooksVo;
import com.min.project_map.ui.frag.FindLocationFrag;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainAct extends AppCompatActivity {
    private static final String TAG = "MainAct";
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private View mainFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        mainFrame = findViewById(R.id.main_frame);

        permissionCheck();
        onFragmentReplace(R.id.main_frame, FindLocationFrag.newInstance(), FindLocationFrag.TAG);
    }

    private void onFragmentReplace(int frameId, Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(frameId, fragment, tag)
                .commit();
    }

    private void getBooks(BooksVo booksVo) {
        RetrofitApi api = new RetrofitModule().provideApiService();

        api.getBooks(booksVo).enqueue(new Callback<BooksVo>() {
            @Override
            public void onResponse(Call<BooksVo> call, Response<BooksVo> response) {
                BooksVo result = response.body();

                Log.e("ddddddd", new Gson().toJson(result));
            }

            @Override
            public void onFailure(Call<BooksVo> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }


    // permission
    private void permissionCheck() {
        boolean permissionCheck = true;

        for (String permissionStr : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permissionStr)
                    != PackageManager.PERMISSION_GRANTED) {

                permissionCheck = false;

            }
        }

        if (!permissionCheck) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            boolean check_result = true;

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            //?????? ?????? ??????
            if (check_result) {
                // ?????? ????????????, ?????? ?????? ??? ?????? ?????? ??????
                onFragmentReplace(R.id.main_frame, FindLocationFrag.newInstance(), FindLocationFrag.TAG);
            } else { // ?????? ?????????
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                    Toast.makeText(this, "???????????? ?????????????????????. ?????? ?????? ???????????? ???????????? ??????????????????.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "???????????? ?????????????????????. ??????(??? ??????)?????? ???????????? ???????????? ?????????. ", Toast.LENGTH_LONG).show();
                }
                ActivityCompat.requestPermissions(MainAct.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }
    }
}