package com.optimax.bidders.auction.impl;

import com.optimax.bidders.auction.VerboseBidder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Viktar Lebedzeu
 */
@Slf4j
public abstract class BaseVerboseBidder implements VerboseBidder {
    /** Initial value of product units (QU) */
    protected int initialQuantity = 0;
    /** Initial value of cash (MU) */
    protected int initialCash = 0;
    /** The rest of auctioned units (QU) */
    protected int quantity = 0;
    /** The rest of cash */
    protected int cash = 0;
    /** Qty points that won bidder */
    protected int quantityPoints = 0;
    /** Verbose logging flag */
    protected boolean verbose;
    /** Goal quantity points */
    protected int goalQuantity = 0;

    @Setter
    @Getter
    protected String bidderId;

    @Override
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public void init(int quantity, int cash) {
        // Initialization clauses could be written in a couple of single lines but has been split for better reading
        this.initialQuantity = quantity;
        this.quantity = quantity;

        this.initialCash = cash;
        this.cash = cash;
        this.quantityPoints = 0;

        goalQuantity = quantity / 2;
        if (goalQuantity % 2 == 0) {
            goalQuantity++;
        }
    }

    @Override
    public abstract int placeBid();

    @Override
    public void bids(int own, int other) {
        int points = 0;
        int qPoints = (quantity > 1) ? 2 : 1;
        if (own > other) {
            // +2 QU if our bid is bigger than other's one. See the rules of auction for details.
            points = qPoints;
        }
        else if (own == other && quantity > 1) {
            // +1 QU if both bids are equal
            points = 1;
        }
        quantityPoints += points;
        quantity -= qPoints;
        if (verbose) {
            log.info("QU : {}", quantityPoints);
        }
    }
}
