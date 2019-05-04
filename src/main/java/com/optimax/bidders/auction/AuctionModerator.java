package com.optimax.bidders.auction;

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
