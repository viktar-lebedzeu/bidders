package com.optimax.bidders.auction;

import com.optimax.bidders.builder.BidderStrategyEnum;

/**
 * Verbose-able bidder interface
 * @author Viktar Lebedzeu
 */
public interface VerboseBidder extends Bidder {
    /**
     * Sets verbose flag.
     * @param verbose Initial value to set. True means that bidder should print verbose messages, false otherwise.
     */
    void setVerbose(boolean verbose);

    /**
     * Returns bidder strategy enum field
     * @return Bidder's strategy enum field
     */
    BidderStrategyEnum getStrategyEnum();
}
