package com.monitoringsiswa.monitoringguru.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.monitoringsiswa.monitoringguru.pojo.Guru;
import com.monitoringsiswa.monitoringguru.pojo.KategoriPelanggaran;
import com.monitoringsiswa.monitoringguru.pojo.Pelanggaran;
import com.monitoringsiswa.monitoringguru.pojo.PoinPelanggaran;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.BaseUrl;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by root on 09/04/16.
 */
public class MonitoringService {
    private interface KilapApi {
        @FormUrlEncoded
        @POST("guru/login")
        Observable<LoginResponse> login(@Field("username") String username,
                                        @Field("password") String password);
        @GET("guru/pelanggaran/{id}")
        Observable<PelanggaranResponse> getPelanggaran(@Path("id") int id);

        @GET("guru/kategori-pelanggaran")
        Observable<KategoriPelanggaranResponse> getKategoriPelanggaran();

        @GET("guru/poin-pelanggaran/{id}")
        Observable<PoinPelanggaranResponse> getPoinPelanggaran(@Path("id") int id);

        @FormUrlEncoded
        @POST("guru/pelanggaran")
        Observable<PostPelanggaranResponse> postPelanggaran(@Field("tanggal") String tanggal,
                                                            @Field("catatan") String catatan,
                                                            @Field("nis") String nis,
                                                            @Field("guru_id") int guruId,
                                                            @Field("poin_pelanggaran_id") int poinPelanggaranId);

    }

    private KilapApi kilapApi;

    private Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public MonitoringService(Context context){
        // 1. Prepare the baseUrl. We will dynamically resolve this from the SharedPreferences.
        BaseUrl baseUrl = new BaseUrl() {
            @Override
            public HttpUrl url() {
                final String baseUrl = "http://si-monitoring.herokuapp.com/api/";
                return HttpUrl.parse(baseUrl);
            }
        };

        // 2. Create and prepare the Client that will be the "backend".
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(120, TimeUnit.SECONDS);
        client.setReadTimeout(120, TimeUnit.SECONDS);

        // 3. Create and prepare the logging interceptor. This is mainly for debugging purpose.
        // TBD: Is it better to use Stetho instead?
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("retrofit", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(loggingInterceptor);

        // 4. Almost done. Now, we can create the Retrofit instance.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        // 5. Finally, we can create the model of the API.
        kilapApi = retrofit.create(KilapApi.class);


    }

    public Observable<Guru> login(String username, String password){
        return kilapApi.login(username, password)
                .map(new Func1<LoginResponse, Guru>() {
                    @Override
                    public Guru call(LoginResponse loginResponse) {
                        return loginResponse.data.guru.toGuruPojo();
                    }
                });
    }

    public Observable<Pelanggaran> getPelanggaran(int guruId){
        return kilapApi.getPelanggaran(guruId)
                .flatMap(new Func1<PelanggaranResponse, Observable<PelanggaranResponse.Data.Pelanggaran>>() {
                    @Override
                    public Observable<PelanggaranResponse.Data.Pelanggaran> call(PelanggaranResponse pelanggaranResponse) {
                        return Observable.from(pelanggaranResponse.data.pelanggaran);
                    }
                })
                .map(new Func1<PelanggaranResponse.Data.Pelanggaran, Pelanggaran>() {
                    @Override
                    public Pelanggaran call(PelanggaranResponse.Data.Pelanggaran pelanggaran) {
                        return pelanggaran.toPelanggaranPojo();
                    }
                });
    }

    public Observable<KategoriPelanggaran> getKategoriPelanggaran(){
        return kilapApi.getKategoriPelanggaran()
                .flatMap(new Func1<KategoriPelanggaranResponse, Observable<KategoriPelanggaranResponse.Data.Kategori>>() {
                    @Override
                    public Observable<KategoriPelanggaranResponse.Data.Kategori> call(KategoriPelanggaranResponse kategoriPelanggaranResponse) {
                        return Observable.from(kategoriPelanggaranResponse.data.kategoriPelanggaran);
                    }
                })
                .map(new Func1<KategoriPelanggaranResponse.Data.Kategori, KategoriPelanggaran>() {
                    @Override
                    public KategoriPelanggaran call(KategoriPelanggaranResponse.Data.Kategori kategori) {
                        return kategori.toKategoriPelanggaranPojo();
                    }
                });
    }

    public Observable<PoinPelanggaran> getPoinPelanggaran(int id){
        return kilapApi.getPoinPelanggaran(id)
                .flatMap(new Func1<PoinPelanggaranResponse, Observable<PoinPelanggaranResponse.Data.PoinPelanggaran>>() {
                    @Override
                    public Observable<PoinPelanggaranResponse.Data.PoinPelanggaran> call(PoinPelanggaranResponse poinPelanggaranResponse) {
                        return Observable.from(poinPelanggaranResponse.data.poinPelanggaran);
                    }
                })
                .map(new Func1<PoinPelanggaranResponse.Data.PoinPelanggaran, PoinPelanggaran>() {
                    @Override
                    public PoinPelanggaran call(PoinPelanggaranResponse.Data.PoinPelanggaran poinPelanggaran) {
                        return poinPelanggaran.toPoinPelanggaranPojo();
                    }
                });
    }

    public Observable<String> postPelanggaran(String catatan, String nis, int guruId, final int poinPelanggaranId){
//        TODO:make date not a hardcoded
        return kilapApi.postPelanggaran("2016-04-21", catatan, nis, guruId, poinPelanggaranId)
                .map(new Func1<PostPelanggaranResponse, String>() {
                    @Override
                    public String call(PostPelanggaranResponse postPelanggaranResponse) {
                        return postPelanggaranResponse.message;
                    }
                });
    }

    private class LoginResponse{
        Data data;

        class Data{
            Guru guru;

            class Guru{
                int id;
                String nip;
                String namaGuru;
                String jenisKelamin;
                String nomorHp;
                String jabatan;
                String username;

                com.monitoringsiswa.monitoringguru.pojo.Guru toGuruPojo(){
                    return new com.monitoringsiswa.monitoringguru.pojo.Guru(
                            id, nip, namaGuru, jenisKelamin, nomorHp, jabatan
                            , username);
                }
            }
        }

    }


    private class PelanggaranResponse{
        Data data;

        class Data{
            List<Pelanggaran> pelanggaran;

            class Pelanggaran{
                int id;
                String tanggal;
                int siswaId;
                int nis;
                String namaSiswa;
                String namaKelas;
                String namaPoinPelanggaran;
                int poin;

                com.monitoringsiswa.monitoringguru.pojo.Pelanggaran toPelanggaranPojo(){
                    return new com.monitoringsiswa.monitoringguru.pojo.Pelanggaran(id, tanggal,siswaId, nis, namaSiswa, namaKelas, namaPoinPelanggaran, poin);
                }
            }
        }
    }

    private class KategoriPelanggaranResponse{
        String status;
        Data data;

        class Data{
            List<Kategori> kategoriPelanggaran;

            class Kategori{
                int id;
                String namaKategoriPelanggaran;

                KategoriPelanggaran toKategoriPelanggaranPojo(){
                    return new KategoriPelanggaran(id, namaKategoriPelanggaran);
                }
            }


        }

    }

    private class PoinPelanggaranResponse{
        String status;
        Data data;

        class Data{
            List<PoinPelanggaran> poinPelanggaran;

            class PoinPelanggaran{
                int id;
                String namaPoinPelanggaran;
                int poin;
                int kategoriPelanggaranId;

                com.monitoringsiswa.monitoringguru.pojo.PoinPelanggaran toPoinPelanggaranPojo(){
                    return new com.monitoringsiswa.monitoringguru.pojo.PoinPelanggaran(id, namaPoinPelanggaran, poin, kategoriPelanggaranId);
                }
            }
        }
    }

    private class PostPelanggaranResponse{
        String status;
        String message;
    }



}
