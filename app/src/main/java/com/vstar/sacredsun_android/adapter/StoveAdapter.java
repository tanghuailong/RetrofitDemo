package com.vstar.sacredsun_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.vstar.sacredsun_android.R;
import com.vstar.sacredsun_android.entity.DeviceEntity;
import com.vstar.sacredsun_android.util.StatusMap;
import com.vstar.sacredsun_android.util.TextHelper;

import java.util.List;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tangh on 2017/1/9.
 */

public class StoveAdapter extends RecyclerView.Adapter<StoveAdapter.StoveHolder> {


    private List<DeviceEntity> lists;
    private Context context;
    private OnRecyclerViewItemClickListener listener;

    public StoveAdapter(List<DeviceEntity> lists, Context context) {
        this(lists, context, null);
    }

    public StoveAdapter(List<DeviceEntity> lists, Context context, OnRecyclerViewItemClickListener listener) {
        this.lists = lists;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public StoveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_stove, parent, false);
        StoveHolder holder = new StoveHolder(view);
        view.setOnClickListener(v -> {
            listener.onItemClick(v, lists.get(holder.getAdapterPosition()).getAssetsCode());
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(StoveHolder holder, int position) {
        DeviceEntity stoveItem = lists.get(position);
        if (stoveItem != null) {

            holder.stoveDevice.setBackgroundResource(StatusMap.statusAndView.get(stoveItem.getStatus().name()));
            holder.stoveNum.setText(stoveItem.getAssetsCode());
            holder.runState.setText(StatusMap.abbreAndDesc.get(stoveItem.getStatus().name()));
            holder.runStateBlock.setMax(stoveItem.getTotalTime());
            if (stoveItem.getTotalTime() == 0) {
                holder.runStateBlock.setProgress(0);
            } else {
                holder.runStateBlock.setProgress(stoveItem.getRemainingTime());
            }
            //设备是否报警
            if(stoveItem.getIsWarning() > 0) {
                holder.warningStatus.setImageResource(R.drawable.alarmopen);
            }else {
                holder.warningStatus.setImageResource(R.drawable.alarmclose);
            }
            holder.timeLeft.setText(String.valueOf(stoveItem.getRemainingTime()));
            holder.firstSetting.setText(stoveItem.getTemperature());
            holder.firstActual.setValue(TextHelper.strTransFromFloat(stoveItem.getTemperature1()));
            holder.secondSetting.setText(stoveItem.getTemperature());
            holder.secondActual.setValue(TextHelper.strTransFromFloat(stoveItem.getTemperature2()));
            holder.thirdSetting.setText(stoveItem.getHumidity());
            holder.thirdActual.setValue(TextHelper.strTransFromFloat(stoveItem.getHumidity1()));
            holder.fourSetting.setText(stoveItem.getHumidity());
            holder.fourActual.setValue(TextHelper.strTransFromFloat(stoveItem.getHumidity2()));
            holder.productModel.setText(stoveItem.getMaterialCode());
            holder.orderNum.setText(Integer.toString(stoveItem.getQuantity()));
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class StoveHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.stove_device)
        LinearLayout stoveDevice;
        @BindView(R.id.stove_num)
        TextView stoveNum;
        @BindView(R.id.stove_run_status)
        TextView runState;
        @BindView(R.id.stove_run_status_block)
        NumberProgressBar runStateBlock;
        @BindView(R.id.stove_left_time)
        TextView timeLeft;
        @BindView(R.id.product_model)
        TextView productModel;
        @BindView(R.id.order_num)
        TextView orderNum;
        @BindView(R.id.first_setting)
        TextView firstSetting;
        @BindView(R.id.first_actual)
        CircleProgressView firstActual;
        @BindView(R.id.second_setting)
        TextView secondSetting;
        @BindView(R.id.second_actual)
        CircleProgressView secondActual;
        @BindView(R.id.third_setting)
        TextView thirdSetting;
        @BindView(R.id.third_actual)
        CircleProgressView thirdActual;
        @BindView(R.id.four_setting)
        TextView fourSetting;
        @BindView(R.id.four_actual)
        CircleProgressView fourActual;
        @BindView(R.id.warning_status)
        ImageView warningStatus;


        public StoveHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }
}
