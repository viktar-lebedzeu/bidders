package com.optimax.bidders.builder;

import com.optimax.bidders.auction.Bidder;
import com.optimax.bidders.auction.impl.EqualVerboseBidder;
import com.optimax.bidders.auction.impl.WeightedBidder;
import com.optimax.bidders.auction.impl.WeightedCorrectionBidder;
import lombok.extern.slf4j.Slf4j;

/**
 * Builds the specific implementation of Bidder class according to the given parameters
 * @author Viktar Lebedzeu
 */
@Slf4j
public class BidderBuilder {
    /**
     * Creates bidder instance by the given
     * @param type String of bidder strategy type
     * @return Instance of bidder
     */
    public static Bidder createBidderByType(String type) {
        BidderStrategyEnum strategy = getBidderStrategyEnum(type);
        switch (strategy) {
            case WEIGHTED:
                return new WeightedBidder();

            case WEIGHTED_CORRECTION:
                return new WeightedCorrectionBidder();

            case EQUAL:
                return new EqualVerboseBidder();

            default:
                throw new IllegalArgumentException("Unknown bidder strategy type \"" + type + "\"");
        }
    }

    /**
     * Returns bidder strategy enum by the given type string
     * @param type Type string
     * @return Bidder strategy enum
     */
    private static BidderStrategyEnum getBidderStrategyEnum(String type) {
        BidderStrategyEnum strategyEnum = BidderStrategyEnum.findByType(type);
        if (strategyEnum == null || strategyEnum == BidderStrategyEnum.UNKNOWN) {
            throw new IllegalArgumentException("Unknown bidder strategy type \"" + type + "\"");
        }
        return strategyEnum;
    }
}
