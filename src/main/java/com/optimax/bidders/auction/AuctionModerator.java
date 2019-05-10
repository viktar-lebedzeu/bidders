package com.optimax.bidders.auction;

import com.optimax.bidders.auction.impl.BaseVerboseBidder;
import com.optimax.bidders.builder.BidderBuilder;
import com.optimax.bidders.builder.BidderStrategyEnum;
import com.optimax.bidders.dto.BidInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * Auctionist or auction moderator class.
 * @author Viktar Lebedzeu
 */
@Slf4j
public class AuctionModerator {
    /** Step of product lots */
    private static final int LOT_STEP = 2;

    /** List of bidders */
    private ArrayList<Bidder> bidders = new ArrayList<>(2);

    private ArrayList<BidInfo> bidInfos;

    /** Initial quantity of product */
    private int initialQuantity;

    /** Initial value of bidder's cash */
    private int initialCash;

    private int quantity = 0;

    /** Verbose flag */
    private boolean verbose;

    protected AuctionModerator() {
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
        bidders.parallelStream().forEach(b -> {
            if (b instanceof BaseVerboseBidder) {
                ((BaseVerboseBidder) b).setVerbose(verbose);
            }
        });
    }

    public boolean nextTurn() {
        if (quantity <= 0) {
            return false;
        }
        final Bidder bidder1 = bidders.get(0);
        final Bidder bidder2 = bidders.get(1);
        final int bid1 = bidder1.placeBid();
        final int bid2 = bidder2.placeBid();
        int lotValue = (quantity > 1) ? 2 : 1;

        if (verbose) {
            log.info("Next turn : qty = {}/{}; bid1 = {}; bid2 = {}", lotValue, quantity, bid1, bid2);
        }
        bidder1.bids(bid1, bid2);
        bidder2.bids(bid2, bid1);

        quantity -= lotValue;
        return (quantity > 0);
    }

    /**
     * Creates new instance of auction moderator with the given parameters
     * @param bidder1 first bidder
     * @param bidder2 Second bidder
     * @param initialQuantity Initial value of QU (quantity units)
     * @param initialCash Initial value of MU (monetary units)
     * @return New instance of auction moderator
     */
    public static AuctionModerator create(Bidder bidder1, Bidder bidder2, int initialQuantity, int initialCash) {
        // All bidders must be set
        if (bidder1 == null || bidder2 == null) {
            throw new IllegalArgumentException("bidder1 or bigger2 can not be NULL");
        }
        // Initializes bidders
        bidder1.init(initialQuantity, initialCash);
        bidder2.init(initialQuantity, initialCash);

        AuctionModerator moderator = new AuctionModerator();
        moderator.bidders.add(bidder1);
        moderator.bidders.add(bidder2);
        moderator.initialQuantity = initialQuantity;
        moderator.initialCash = initialCash;
        moderator.quantity = initialQuantity;
        int bidsCount = (int) Math.ceil((double) initialQuantity / (double) LOT_STEP);
        moderator.bidInfos = new ArrayList<>(bidsCount);
        return moderator;
    }

    /**
     * Creates new instance of auction moderator with the given parameters
     * @param bidderType1 Type of the first bidder
     * @param bidderType2 Type of the second bidder
     * @param initialQuantity Initial value of QU (quantity units)
     * @param initialCash Initial value of MU (monetary units)
     * @return New instance of auction moderator
     */
    public static AuctionModerator create(String bidderType1, String bidderType2,
                                          int initialQuantity, int initialCash) {
        Bidder bidder1 = BidderBuilder.createBidderByType(bidderType1);
        Bidder bidder2 = BidderBuilder.createBidderByType(bidderType2);
        return create(bidder1, bidder2, initialQuantity, initialCash);
    }
}
