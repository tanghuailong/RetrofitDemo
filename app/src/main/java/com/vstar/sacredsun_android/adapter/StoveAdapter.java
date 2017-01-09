package com.vstar.sacredsun_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vstar.sacredsun_android.R;
import com.vstar.sacredsun_android.dao.StoveItem;

import java.util.List;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tangh on 2017/1/9.
 */

public class StoveAdapter extends RecyclerView.Adapter<StoveAdapter.StoveHolder> {


    private List<StoveItem> lists;
    private Context context;

    public StoveAdapter(List<StoveItem> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public StoveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_stove, parent, false);
        return new StoveHolder(view);
    }

    @Override
    public void onBindViewHolder(StoveHolder holder, int position) {
        StoveItem stoveItem = lists.get(position);
        if(stoveItem != null) {
            holder.bindStoveItem(stoveItem);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class StoveHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.stove_num)
        public TextView stoveNum;
        @BindView(R.id.stove_run_status)
        public TextView runState;
        @BindView(R.id.stove_run_status_block)
        public View runStateBlock;
        @BindView(R.id.stove_left_time)
        public TextView timeLeft;
        @BindView(R.id.product_model)
        public TextView productModel;
        @BindView(R.id.order_num)
        public TextView orderNum;
        @BindView(R.id.first_setting)
        public TextView firstSetting;
        @BindView(R.id.first_actual)
        public CircleProgressView firstActual;
        @BindView(R.id.second_setting)
        public TextView secondSetting;
        @BindView(R.id.second_actual)
        public CircleProgressView secondActual;
        @BindView(R.id.third_setting)
        public TextView thirdSetting;
        @BindView(R.id.third_actual)
        public CircleProgressView thirdActual;
        @BindView(R.id.four_setting)
        public TextView fourSetting;
        @BindView(R.id.four_actual)
        public CircleProgressView fourActual;

        private StoveItem mStoveItem;

        public StoveHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }

        public void bindStoveItem(StoveItem stoveItem) {
            mStoveItem = stoveItem;
            stoveNum.setText(stoveItem.getStoveNum());
            runState.setText(stoveItem.getRunState());
//            runStateBlock.setBackgroundResource(StatusMap.statusAndView.get(stoveItem.getRunState()));
            runStateBlock.setBackgroundResource(R.drawable.status_bar_dry);
            timeLeft.setText(String.valueOf(stoveItem.getTimeLeft()));
            firstSetting.setText(String.valueOf(stoveItem.getFirstSetting()));
            firstActual.setValue(stoveItem.getFirstAcutal());
            secondSetting.setText(String.valueOf(stoveItem.getFirstSetting()));
            secondActual.setValue(stoveItem.getSecondActual());
            thirdSetting.setText(String.valueOf(stoveItem.getThirdSetting()));
            thirdActual.setValue(stoveItem.getThirdActual());
            fourSetting.setText(String.valueOf(stoveItem.getFourSetting()));
            fourActual.setValue(stoveItem.getFourActual());
            productModel.setText(stoveItem.getProductModel());
            orderNum.setText(stoveItem.getOrderNum());
        }
    }
}
