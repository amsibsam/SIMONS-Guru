package com.monitoringsiswa.monitoringsiswa.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.monitoringsiswa.monitoringsiswa.MonitoringApplication;
import com.monitoringsiswa.monitoringsiswa.R;
import com.monitoringsiswa.monitoringsiswa.databinding.ActivityLoginBinding;
import com.monitoringsiswa.monitoringsiswa.module.AccountInfoStore;
import com.monitoringsiswa.monitoringsiswa.network.MonitoringService;
import com.monitoringsiswa.monitoringsiswa.pojo.Guru;

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
                        Log.e("amsibsam", "login error "+e.toString());
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onNext(Guru guru) {
                        Log.d("amsibsam", "guru "+guru.getNamaGuru());
                        accountInfoStore.cacheAccountInfo(guru);
                        progressDialog.dismiss();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }
                });
    }
}
