package com.optimax.bidders.auction.impl;

import com.optimax.bidders.auction.VerboseBidder;

/**
 * @author Viktar Lebedzeu
 */
public class EqualVerboseBidder extends BaseVerboseBidder {
    private int quantityPoints = 0;

    @Override
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public void init(int quantity, int cash) {
        super.init(quantity, cash);
    }

    @Override
    public int placeBid() {
        return 0;
    }

    @Override
    public void bids(int own, int other) {
    }
}
