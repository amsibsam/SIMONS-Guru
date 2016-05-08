package com.monitoringsiswa.monitoringguru.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.monitoringsiswa.monitoringguru.MonitoringApplication;
import com.monitoringsiswa.monitoringguru.R;
import com.monitoringsiswa.monitoringguru.databinding.ActivityLoginBinding;
import com.monitoringsiswa.monitoringguru.module.AccountInfoStore;
import com.monitoringsiswa.monitoringguru.network.MonitoringService;
import com.monitoringsiswa.monitoringguru.pojo.Guru;
import com.monitoringsiswa.monitoringguru.ui.fragment.HomeFragment;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Inject
    MonitoringService monitoringService;

    @Inject
    AccountInfoStore accountInfoStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MonitoringApplication.getComponent().inject(this);

        if (accountInfoStore.hasAccount()){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(binding.username.getText().toString(), binding.password.getText().toString());
            }
        });
    }

    private void login(String username, String password){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Harap Tunggu..");
        progressDialog.show();
        monitoringService.login(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Guru>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("amsibsam", "login error "+e.getMessage());
                        if (e.toString().equals("retrofit.HttpException: HTTP 401 Unauthorized")){
                            Toast.makeText(LoginActivity.this, "Username dan password salah", Toast.LENGTH_SHORT).show();
                        }
                        
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onNext(Guru guru) {
                        progressDialog.dismiss();
                        accountInfoStore.cacheAccountInfo(guru);
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }
                });
    }
}
