package com.cognitionbox.petra.examples.trailingstoploss.data;

import com.cognitionbox.petra.ast.terms.Base;

@Base
public final class TrailData {

    private State state = State.NO_DIRECTION;
    private Direction direction, newDirection = Direction.NONE;

    private float entry;
    private float close;
    private float mid, newMid;
    private float max;
    private float min;
    private float stop;
    private boolean stopped = false;

    private boolean priceUpdated;
    private boolean directionUpdated;

    public boolean priceNotUpdated(){
        return !priceUpdated;
    }

    public boolean priceUpdated(){
        return mid==newMid && priceUpdated;
    }

    public boolean directionNotUpdated(){
        return !directionUpdated;
    }

    public boolean directionUpdated(){
        return direction==newDirection && directionUpdated;
    }

    public void updateDirection(){
        if (directionNotUpdated()){
            direction = newDirection;
            directionUpdated = true;
            assert(directionUpdated());
        }
    }

    public void resetDirectionUpdate(){
        if (directionUpdated()){
            directionUpdated = false;
            assert(directionNotUpdated());
        }
    }

    public void updatePrice(){
        if (priceNotUpdated()){
            mid = newMid;
            priceUpdated = true;
            assert(priceUpdated());
        }
    }

    public void resetPriceUpdate(){
        if (priceUpdated()){
            priceUpdated = false;
            assert(priceNotUpdated());
        }
    }

    public boolean none(){return direction==Direction.NONE;}
    public boolean buy(){return direction==Direction.BUY;}
    public boolean sell(){return direction==Direction.SELL;}

    public boolean noChange(){return mid <= max || mid >= min;}

    public boolean newMax(){return mid > max;}
    public boolean newMin(){return mid < min;}

    public boolean buyStopUpdated(){return mid == max;}
    public boolean sellStopUpdated(){return mid == min;}
    public boolean hitStop(){return mid<stop || mid>stop;}
    public boolean stopped() {return stopped;}

    public void updateBuyStop(){
        if (newMax()){
            max = mid;
            stop = mid - 5;
            state = State.UPDATED_BUY_STOP;
            System.out.println("TrailData:updateBuyStop");
            assert(buyStopUpdated());
        }
    }

    public void updateSellStop(){
        if (newMin()){
            min = mid;
            stop = mid + 5;
            state = State.UPDATED_SELL_STOP;
            System.out.println("TrailData:updateSellStop");
            assert(sellStopUpdated());
        }
    }

    public void selectBuy(){
        if (none()){
            newDirection = Direction.BUY;
            entry = mid;
            stop = mid - 5;
            state = State.SELECTED_BUY;
            System.out.println("TrailData:selectBuy");
            assert(buy());
        }
    }

    public void selectSell(){
        if (none()){
            newDirection = Direction.SELL;
            entry = mid;
            stop = mid + 5;
            state = State.SELECTED_SELL;
            System.out.println("TrailData:selectSell");
            assert(sell());
        }
    }

    public void stop(){
        if (hitStop()){
            stopped = true;
            close = mid;
            state = State.STOPPED;
            System.out.println("TrailData:stop");
            assert(stopped());
        }
    }


}
