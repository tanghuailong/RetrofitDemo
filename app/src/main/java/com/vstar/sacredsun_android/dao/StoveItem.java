package com.vstar.sacredsun_android.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by tangh on 2017/1/9.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoveItem {
    //炉子编号
    private String stoveNum;
    //运行状态
    private String runState;
    //产品型号
    private String productModel;
    //订单数量
    private String orderNum;
    //剩余时间
    private int timeLeft;
    //设定值和实际值
    private int firstSetting;
    private int firstAcutal;
    private int secondSetting;
    private int secondActual;
    private int thirdSetting;
    private int thirdActual;
    private int fourSetting;
    private int fourActual;
}
