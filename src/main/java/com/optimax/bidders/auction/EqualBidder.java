package com.optimax.bidders.auction;

/**
 * @author Viktar Lebedzeu
 */
public class EqualBidder implements Bidder {
    private int quantity = 0;
    private int cash = 0;
    private int quantityPoints = 0;

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
