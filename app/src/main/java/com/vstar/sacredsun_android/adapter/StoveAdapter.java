package com.vstar.sacredsun_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vstar.sacredsun_android.R;
import com.vstar.sacredsun_android.entity.DeviceEntity;
import com.vstar.sacredsun_android.util.StatusMap;

import java.util.List;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tangh on 2017/1/9.
 */

public class StoveAdapter extends RecyclerView.Adapter<StoveAdapter.StoveHolder>{


    private List<DeviceEntity> lists;
    private Context context;
    private OnRecyclerViewItemClickListener listener;

    public StoveAdapter(List<DeviceEntity> lists, Context context,OnRecyclerViewItemClickListener listener) {
        this.lists = lists;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public StoveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_stove, parent, false);
        StoveHolder holder = new StoveHolder(view);
        view.setOnClickListener(v ->  {
            listener.onItemClick(v,lists.get(holder.getAdapterPosition()).getAssetsCode());
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(StoveHolder holder, int position) {
        DeviceEntity stoveItem = lists.get(position);
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

        private DeviceEntity mStoveItem;

        public StoveHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }

        public void bindStoveItem(DeviceEntity stoveItem) {
            mStoveItem = stoveItem;
            stoveNum.setText(stoveItem.getAssetsCode());
            runState.setText(stoveItem.getStatus().name());
            runStateBlock.setBackgroundResource(StatusMap.statusAndView.get(stoveItem.getStatus().name()));
            timeLeft.setText(stoveItem.getRedidualTime());
            firstSetting.setText(stoveItem.getTemperature());
            firstActual.setValue(Float.parseFloat(stoveItem.getTemperature1()));
            secondSetting.setText(stoveItem.getTemperature());
            secondActual.setValue(Float.parseFloat(stoveItem.getTemperature2()));
            thirdSetting.setText(stoveItem.getHumidity());
            thirdActual.setValue(Float.parseFloat(stoveItem.getHumidity1()));
            fourSetting.setText(stoveItem.getHumidity());
            fourActual.setValue(Float.parseFloat(stoveItem.getHumidity2()));
            productModel.setText(stoveItem.getMaterialCode());
            orderNum.setText(stoveItem.getQuantity());
        }
    }
}
