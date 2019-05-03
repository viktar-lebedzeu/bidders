package com.optimax.bidders.auction;

/**
 * @author Viktar Lebedzeu
 */
public class DefaultBidder implements Bidder {
    private int quantity = 0;
    private int cash = 0;
    private int quantityPoints = 0;

    @Override
    public void init(int quantity, int cash) {
        this.quantity = quantity;
        this.cash = cash;
    }

    @Override
    public int placeBid(int lotQty) {
        quantity -= lotQty;
        return 0;
    }

    @Override
    public void bids(int own, int other) {
    }

    @Override
    public int getCash() {
        return cash;
    }

    @Override
    public int getQuantityPoints() {
        return quantityPoints;
    }

    @Override
    public void addQuantityPoints(int points) {
        quantityPoints += points;
    }
}
