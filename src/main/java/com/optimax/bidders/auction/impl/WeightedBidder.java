package com.optimax.bidders.auction.impl;

import com.optimax.bidders.auction.VerboseBidder;

/**
 * @author Viktar Lebedzeu
 */
public class WeightedBidder implements VerboseBidder {
    private int quantity = 0;
    private int cash = 0;
    private int quantityPoints = 0;
    private boolean verbose;

    @Override
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public void init(int quantity, int cash) {
        this.quantity = quantity;
        this.cash = cash;
    }

    @Override
    public int placeBid() {
        return 0;
    }

    @Override
    public void bids(int own, int other) {
    }
}
