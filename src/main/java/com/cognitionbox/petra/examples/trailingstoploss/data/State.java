package com.cognitionbox.petra.examples.trailingstoploss.data;

import com.cognitionbox.petra.ast.terms.External;

@External
public enum State {
    NO_DIRECTION,
    SELECTED_BUY,
    SELECTED_SELL,
    NEW_MAX,
    NEW_MIN,
    UPDATED_BUY_STOP,
    UPDATED_SELL_STOP,
    HIT_STOP,
    STOPPED
}
