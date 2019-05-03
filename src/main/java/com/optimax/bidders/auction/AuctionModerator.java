package com.optimax.bidders.auction;

import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Moderates auction using internal rules
 * @author Viktar Lebedzeu
 */
public interface AuctionModerator {
    /** Initializes bidders before auction */
    void initBidders();

    /**
     * Runs next auction turn
     * @return True if auction could be continued (remains QU), false otherwise.
     */
    boolean nextTurn();

    void findWinner();
}
