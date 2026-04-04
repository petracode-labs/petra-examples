package com.cognitionbox.petra.examples.trailingstoploss.view;

import com.cognitionbox.petra.examples.trailingstoploss.data.TrailData;
import com.cognitionbox.petra.examples.trailingstoploss.data.TrailDataSingleton;

public final class DirectionUpdated {
    private final TrailData trailData = TrailDataSingleton.get();

    public boolean directionNotUpdated(){ return trailData.directionNotUpdated();}

    public boolean directionUpdated(){ return trailData.directionUpdated();}

    public void updateDirection(){
        if (directionNotUpdated()){
            trailData.updateDirection();
            assert(directionUpdated());
        }
    }

    public void resetDirectionUpdate(){
        if (directionUpdated()){
            trailData.resetDirectionUpdate();
            assert(directionNotUpdated());
        }
    }
}
