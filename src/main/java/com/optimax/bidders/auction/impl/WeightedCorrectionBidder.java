package com.optimax.bidders.auction.impl;

import com.optimax.bidders.builder.BidderStrategyEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Viktar Lebedzeu
 */
@Slf4j
public class WeightedCorrectionBidder extends WeightedBidder {
    /** Count of ties (draws) */
    private int tieCount = 0;
    /** Competitor's cash */
    private int oppositeCash = 0;
    /** Competitor's quantity */
    private int oppositeQuantity = 0;

    @Override
    public BidderStrategyEnum getStrategyEnum() {
        return BidderStrategyEnum.WEIGHTED_CORRECTION;
    }

    @Override
    public void init(int quantity, int cash) {
        super.init(quantity, cash);
        oppositeCash = cash;
        oppositeQuantity = quantity;
    }

    @Override
    public int placeBid() {
        int bid = super.placeBid();
        if (goalQuantity > quantityPoints) {
            final int diff = goalQuantity - quantityPoints;
            if (diff <= 2) {
                bid += cash;
                cash = 0;
            }
        }
        return bid;
    }

    @Override
    public void bids(int own, int other) {
        int points = quantityPoints;
        super.bids(own, other);
        points = quantityPoints - points;
        // log.info("Points : {}", points);

        oppositeCash -= other;
        oppositeQuantity = initialQuantity - quantity;
        if (own == other) {
            tieCount++;
        }
    }
}
