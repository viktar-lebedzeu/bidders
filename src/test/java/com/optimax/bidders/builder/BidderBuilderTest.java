package com.optimax.bidders.builder;

import com.optimax.bidders.auction.Bidder;
import com.optimax.bidders.auction.impl.EqualVerboseBidder;
import com.optimax.bidders.auction.impl.WeightedBidder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test suite to test features of BidderBuilder class
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
        Assert.assertTrue(bidder instanceof EqualVerboseBidder);
    }
}
