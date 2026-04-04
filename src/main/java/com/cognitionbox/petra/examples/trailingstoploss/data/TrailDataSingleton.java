package com.cognitionbox.petra.examples.trailingstoploss.data;

import com.cognitionbox.petra.ast.terms.External;

@External
public final class TrailDataSingleton {
    private volatile static TrailData data = new TrailData();
    public static TrailData get(){
        return data;
    }
}
