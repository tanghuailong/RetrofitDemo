package com.vstar.sacredsun_android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

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
 *�����������
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.all_stove)
    RecyclerView recyclerView;

    private static List<DeviceEntity> list = new ArrayList<>();
    private Subscription subscription;
    private static final String WORK_SHOP_NAME = "��������";
    private StoveAdapter adapter;

    private static final String LOG_TAG = "MainActivity";
    private static final String TAG = "assetsCode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL));
        adapter = new StoveAdapter(list,this, (view,code) -> {
            //��ת����ϸҳ��
            Intent intent = new Intent(MainActivity.this,DetailActivity.class);
            intent.putExtra(TAG,code);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    private void initData(){

        //��ѯ��ȡ����
         subscription = HttpMethods.getInstance().getService(SacredsunService.class)
                .getDeviceBasicData(WORK_SHOP_NAME)
                .compose(RxHelper.io_main())
                .retryWhen(errors -> errors.flatMap(error -> Observable.timer(5, TimeUnit.SECONDS)))
                .repeatWhen(completed -> completed.delay(5, TimeUnit.SECONDS))
                .subscribe((r) -> {
                    //���list���������
                    list.clear();
                    list.addAll(r.getItems());
                },(e) -> {
                    e.printStackTrace();
                },() -> {
                    //֪ͨ���ݸı�
                    adapter.notifyDataSetChanged();
                    Log.d(LOG_TAG,"completed");
                });
    }

    //�����ʱ���ģ����Ҹ�������
    @Override
    protected void onStart() {
        super.onStart();
        if(subscription == null || subscription.isUnsubscribed()) {
            initData();
        }
    }

    //�˳�ʱ��������
    @Override
    protected void onStop() {
        super.onStop();
        //�������
        if(subscription!=null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    /**
     * ������������������ԣ�random-beans��һ�����õ�ѡ��
     * �����κ�Android Nֻ֧��java8���ֹ���,��random-beanҪ��java8
     */
//    private List<DeviceEntity> initData(){
//        PodamFactory factory = new PodamFactoryImpl();
//        StoveItem myPojo = factory.manufacturePojo(StoveItem.class);
//    }
}
