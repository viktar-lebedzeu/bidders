package com.optimax.bidders.auction.impl;

import com.optimax.bidders.auction.VerboseBidder;

/**
 * @author Viktar Lebedzeu
 */
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
    private int goalQuantity = 0;

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

        int goalQuantity = quantity / 2;
        if (goalQuantity % 2 != 0) {
            goalQuantity++;
        }
    }

    @Override
    public abstract int placeBid();

    @Override
    public void bids(int own, int other) {
        if (own > other) {
            // +2 QU if our bid is bigger than other's one. See the rules of auction for details.
            quantityPoints += 2;
        }
        else if (own == other) {
            // +1 QU if both bids are equal
            quantityPoints++;
        }
    }
}
