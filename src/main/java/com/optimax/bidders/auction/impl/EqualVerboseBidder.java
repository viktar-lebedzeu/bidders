package com.optimax.bidders.auction.impl;

import com.optimax.bidders.builder.BidderStrategyEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Viktar Lebedzeu
 */
@Slf4j
public class EqualVerboseBidder extends BaseVerboseBidder {
    @Override
    public BidderStrategyEnum getStrategyEnum() {
        return BidderStrategyEnum.EQUAL;
    }

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
        int value = 0;
        if (goalQuantity > quantityPoints) {
            value = (int) Math.ceil((double) cash / (double) turns);
            if (value > cash) {
                value = cash;
            }
        }
        if (verbose) {
            log.info("[{}] Placed bid: {} / {} ({}) = {}", getBidderId(), value, cash, initialCash, cash - value);
        }
        cash -= value;
        return value;
    }

    @Override
    public void bids(int own, int other) {
        if (verbose) {
            log.info("bids : {} : {} ({})", own, other, quantity);
        }
        super.bids(own, other);
    }
}
