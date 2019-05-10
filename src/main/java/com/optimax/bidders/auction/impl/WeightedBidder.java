package com.optimax.bidders.auction.impl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Viktar Lebedzeu
 */
@Slf4j
public class WeightedBidder extends BaseVerboseBidder {
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
        super.bids(own, other);
        if (verbose) {
            log.info("bids : {} : {}", own, other);
        }
    }
}
