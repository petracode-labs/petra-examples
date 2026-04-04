package com.cognitionbox.petra.examples.trailingstoploss.view;

import com.cognitionbox.petra.examples.trailingstoploss.data.TrailData;
import com.cognitionbox.petra.examples.trailingstoploss.data.TrailDataSingleton;

public final class Direction {
    private final TrailData trailData = TrailDataSingleton.get();

    public boolean none(){ return trailData.none();}
    public boolean buy(){ return trailData.buy();}
    public boolean sell(){ return trailData.sell();}

    public void selectBuy(){
        if (none()){
            trailData.selectBuy();
            assert(buy());
        }
    }

    public void selectSell(){
        if (none()){
            trailData.selectSell();
            assert(sell());
        }
    }
}
