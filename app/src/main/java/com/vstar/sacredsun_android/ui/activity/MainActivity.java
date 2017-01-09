package com.vstar.sacredsun_android.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.vstar.sacredsun_android.R;
import com.vstar.sacredsun_android.adapter.StoveAdapter;
import com.vstar.sacredsun_android.dao.StoveItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.all_stove)
    RecyclerView recyclerView;

    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL));
        List<StoveItem> list = initData();
        StoveAdapter adapter = new StoveAdapter(list,this);
        recyclerView.setAdapter(adapter);

    }

    private List<StoveItem> initData(){
        PodamFactory factory = new PodamFactoryImpl();
        StoveItem myPojo = factory.manufacturePojo(StoveItem.class);
        myPojo.setFirstAcutal(12);
        myPojo.setSecondActual(89);
        myPojo.setThirdActual(42);
        myPojo.setFourActual(69);
        List<StoveItem> list = new ArrayList<>();
        list.addAll(Arrays.asList(myPojo,myPojo,myPojo,myPojo,myPojo,myPojo,myPojo,myPojo,myPojo,myPojo));
        return list;
    }
}
