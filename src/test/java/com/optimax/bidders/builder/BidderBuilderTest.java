package com.optimax.bidders.builder;

import com.optimax.bidders.auction.Bidder;
import com.optimax.bidders.auction.impl.EqualVerboseBidder;
import com.optimax.bidders.auction.impl.WeightedBidder;
import com.optimax.bidders.auction.impl.WeightedCorrectionBidder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test suite to test features of BidderBuilder class
 * @author Viktar Lebedzeu
 */
@Slf4j
public class BidderBuilderTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateBidder() {
        Bidder bidder = BidderBuilder.createBidderByType("wEiGhTeD");
        Assert.assertNotNull(bidder);
        Assert.assertTrue(bidder instanceof WeightedBidder);

        bidder = BidderBuilder.createBidderByType("wEiGhTeD-cORRecTIOn");
        Assert.assertNotNull(bidder);
        Assert.assertTrue(bidder instanceof WeightedCorrectionBidder);

        bidder = BidderBuilder.createBidderByType("EqUaL");
        Assert.assertNotNull(bidder);
        Assert.assertTrue(bidder instanceof EqualVerboseBidder);
    }

    @Test
    public void testIncorrectBidderType() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Unknown bidder strategy type \"xXx\"");
        BidderBuilder.createBidderByType("xXx");
    }
}
