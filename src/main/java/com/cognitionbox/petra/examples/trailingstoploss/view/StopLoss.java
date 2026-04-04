package com.cognitionbox.petra.examples.trailingstoploss.view;

import com.cognitionbox.petra.examples.trailingstoploss.data.TrailData;
import com.cognitionbox.petra.examples.trailingstoploss.data.TrailDataSingleton;

public final class StopLoss {
    private final TrailData trailData = TrailDataSingleton.get();

    public boolean noChange(){ return trailData.noChange();}

    public boolean newMax(){ return trailData.newMax();}

    public boolean newMin(){ return trailData.newMin();}

    public boolean hitStop(){ return trailData.hitStop();}

    public boolean buyStopUpdated(){ return trailData.buyStopUpdated();}

    public boolean sellStopUpdated(){ return trailData.sellStopUpdated();}

    public void updateBuyStop(){
        if (newMax()){
            trailData.updateBuyStop();
            assert(buyStopUpdated());
        }
    }

    public void updateSellStop(){
        if (newMin()){
            trailData.updateSellStop();
            assert(sellStopUpdated());
        }
    }

    public void stop(){
        if (hitStop()){
            trailData.stop();
            assert(stopped());
        }
    }

    public boolean stopped() {return trailData.stopped();}
}
