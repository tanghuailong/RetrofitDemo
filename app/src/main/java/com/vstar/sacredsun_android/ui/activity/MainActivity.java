package com.vstar.sacredsun_android.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.vstar.sacredsun_android.R;
import com.vstar.sacredsun_android.adapter.StoveAdapter;
import com.vstar.sacredsun_android.entity.DeviceEntity;
import com.vstar.sacredsun_android.service.SacredsunService;
import com.vstar.sacredsun_android.util.rest.HttpMethods;
import com.vstar.sacredsun_android.util.rxjava.RxHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;

/**
 *看板的主界面
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.kanban_name)
    TextView kanbanName;
    @BindView(R.id.all_stove)
    RecyclerView recyclerView;

    private static List<DeviceEntity> list = new ArrayList<>();
    private Subscription subscription = null;
    private String workShopCode = "";
    private StoveAdapter adapter;

    private static final String LOG_TAG = "MainActivity";
    private static final String TAG = "assetsCode";
    private static final String TAG2 = "work_shop_code";
    private static final String TAG3 = "first_start";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if(sharedPreferences.contains(TAG2)) {

            workShopCode = sharedPreferences.getString(TAG2,"01");
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(10, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.getItemAnimator().setChangeDuration(0);
            adapter = new StoveAdapter(list, this, (view, code) -> {
                //跳转到详细页面
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(TAG, code);
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);

            if (subscription == null || subscription.isUnsubscribed()) {
                initData();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean firstStart = preferences.getBoolean(TAG3,false);
        if(!firstStart) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(TAG3,true);
            editor.commit();
            Intent intent = new Intent(MainActivity.this,SettingActivity.class);
            //将会将现在Activity 杀死，然后保证下次一定调用onCreate
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void initData(){
        //轮询获取数据
        subscription =  HttpMethods.getInstance().getService(SacredsunService.class)
                .getDeviceBasicData(workShopCode)
                .compose(RxHelper.io_main())
                .retryWhen(errors -> errors.flatMap(error -> Observable.timer(10, TimeUnit.SECONDS)))
                .repeatWhen(completed -> completed.delay(10, TimeUnit.SECONDS))
                .subscribe((r) -> {
                    Log.d(LOG_TAG,"onNext");
                    kanbanName.setText(r.getItem().getWorkshopName());
                    list.clear();
                    list.addAll(r.getItems());
                    adapter.notifyItemRangeChanged(0,list.size());
                },(e) -> {
                    e.printStackTrace();
                },() -> {
                    Log.d(LOG_TAG,"completed");
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription!=null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    //    //进入的时候订阅，并且更新数据
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//    }

//    //退出时候解除订阅
//    @Override
//    protected void onStop() {
//        super.onStop();
////        解除订阅
//        if(subscription!=null && !subscription.isUnsubscribed()) {
//            subscription.unsubscribe();
//        }
//    }

    /**
     * 制造随机数据用来测试，random-beans是一个更好的选择，
     * 但无奈何Android N只支持java8部分功能,而random-bean要求java8
     */
//    private List<DeviceEntity> initData(){
//        PodamFactory factory = new PodamFactoryImpl();
//        StoveItem myPojo = factory.manufacturePojo(StoveItem.class);
//    }
}
