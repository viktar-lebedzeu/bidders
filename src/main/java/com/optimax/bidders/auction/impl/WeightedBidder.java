package com.optimax.bidders.auction.impl;

import com.optimax.bidders.builder.BidderStrategyEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * Bidder that uses "Weighted" strategy. It means that the main goal is achieving more than 50% of QU
 * @author Viktar Lebedzeu
 */
@Slf4j
public class WeightedBidder extends BaseVerboseBidder {
    @Override
    public BidderStrategyEnum getStrategyEnum() {
        return BidderStrategyEnum.WEIGHTED;
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
        if (verbose) {
            log.info("Goal : {} QU", (goalQuantity > quantityPoints) ? goalQuantity - quantityPoints : 0);
        }
        int value = 0;
        if (goalQuantity > quantityPoints) {
            double goalQU = (double) (goalQuantity - quantityPoints);
            double currentCash = (double) cash;
            double val = currentCash / goalQU * (double) initialTurns / (double) turns;
            int eqValue = (int) Math.ceil((double) cash / (double) turns);
            value = (int) Math.ceil(val);
            if (value < eqValue) {
                value = eqValue;
            }
            if (value > currentCash) {
                value = cash;
            }
            // value = (int) Math.ceil((double) cash / (double) (goalQuantity - quantityPoints));
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
