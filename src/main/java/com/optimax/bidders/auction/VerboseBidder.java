package com.optimax.bidders.auction;

/**
 * Verbose-able bidder interface
 * @author Viktar Lebedzeu
 */
public interface VerboseBidder extends Bidder {
    /**
     * Set verbose flag.
     * @param verbose Initial value to set. True means that bidder should print verbose messages, false otherwise.
     */
    void setVerbose(boolean verbose);
}
