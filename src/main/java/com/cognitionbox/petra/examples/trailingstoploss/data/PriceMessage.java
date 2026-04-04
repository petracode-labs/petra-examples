package com.cognitionbox.petra.examples.trailingstoploss.data;

import com.cognitionbox.petra.ast.terms.External;
// import io.micronaut.serde.annotation.Serdeable;

// @Serdeable
@External public class PriceMessage {
    private float price;

    // Add a no-argument constructor (required for serialization)
    public PriceMessage() {
    }

    // Your existing constructor
    public PriceMessage(float price) {
        this.price = price;
    }

    public float getPrice() { return this.price; }
    public void setPrice(float price) { this.price = price; }
}