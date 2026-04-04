package com.cognitionbox.petra.examples.trailingstoploss.view;

import com.cognitionbox.petra.examples.trailingstoploss.data.TrailData;
import com.cognitionbox.petra.examples.trailingstoploss.data.TrailDataSingleton;

public final class PriceUpdated {
    private final TrailData trailData = TrailDataSingleton.get();

    public boolean priceNotUpdated(){ return trailData.priceNotUpdated();}

    public boolean priceUpdated(){ return trailData.priceUpdated();}

    public void updatePrice(){
        if (priceNotUpdated()){
            trailData.updatePrice();
            assert(priceUpdated());
        }
    }

    public void resetPriceUpdate(){
        if (priceUpdated()){
            trailData.resetPriceUpdate();
            assert(priceNotUpdated());
        }
    }
}
