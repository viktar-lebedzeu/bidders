package com.optimax.bidders.auction;

import java.util.List;

/**
 * @author Viktar Lebedzeu
 */
public class DefaultAuctionModerator implements AuctionModerator {
    /** Step of product lots */
    private static final int LOT_STEP = 2;

    /** List of bidders */
    private List<Bidder> bidders;

    /** Initial quantity of product */
    private int initialQuantity;

    /** Initial value of bidder's cash */
    private int initialCash;

    public DefaultAuctionModerator(List<Bidder> bidders, int initialQuantity, int initialCash) {
        this.bidders = bidders;
        this.initialQuantity = initialQuantity;
        this.initialCash = initialCash;
    }

    @Override
    public void initBidders() {
        if (bidders != null) {
            bidders.parallelStream().forEach(b -> b.init(initialQuantity, initialCash));
        }
    }

    @Override
    public boolean nextTurn() {
        return false;
    }

    @Override
    public void findWinner() {

    }
}
