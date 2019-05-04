package com.optimax.bidders.builder;

import com.optimax.bidders.auction.Bidder;
import com.optimax.bidders.auction.EqualBidder;
import com.optimax.bidders.auction.WeightedBidder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Viktar Lebedzeu
 */
@Slf4j
public class BidderBuilderTest {

    @Test
    public void testCreateBidder() {
        Bidder bidder = BidderBuilder.createBidderByType("wEiGhTeD");
        Assert.assertNotNull(bidder);
        Assert.assertTrue(bidder instanceof WeightedBidder);

        bidder = BidderBuilder.createBidderByType("EqUaL");
        Assert.assertNotNull(bidder);
        Assert.assertTrue(bidder instanceof EqualBidder);
    }
}
