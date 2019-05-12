package com.optimax.bidders;

import com.optimax.bidders.auction.Bidder;
import com.optimax.bidders.auction.impl.BaseVerboseBidder;
import com.optimax.bidders.builder.BidderStrategyEnum;
import com.optimax.bidders.dto.AuctionResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Test suite to run and check auction in different configurations
 * @author Viktar Lebedzeu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ "unittests" })
@SpringBootTest
@Slf4j
public class BiddersApplicationTest {
    @Autowired
    private BiddersApplication application;

    /**
     * Tests "weighted" vs "equal" bidding strategies
     */
    @Test
    public void testWeightedVsEqual() {
        application.run(
                "-b1", BidderStrategyEnum.WEIGHTED.getType(),
                "-b2", BidderStrategyEnum.EQUAL.getType(),
                "-qty", "20", "-c", "500" /*, "-v", "-h" */);

        final AuctionResultInfo result = application.getAuctionResult();
        Assert.assertNotNull(result);
        final List<Bidder> winners = result.getWinners();
        Assert.assertEquals(1, winners.size());
        Assert.assertTrue(winners.get(0) instanceof BaseVerboseBidder);
        BaseVerboseBidder bidder = (BaseVerboseBidder) winners.get(0);
        Assert.assertEquals(BidderStrategyEnum.WEIGHTED, bidder.getStrategyEnum());
        Assert.assertEquals("Bidder 1", bidder.getBidderId());
    }

    /**
     * Tests "weighted" vs "weighted" bidding strategies
     */
    @Test
    public void testWeightedVsWeighted() {
        application.run(
                "-b1", BidderStrategyEnum.WEIGHTED.getType(),
                "-b2", BidderStrategyEnum.WEIGHTED.getType(),
                "-qty", "20", "-c", "500" /*, "-v" */);

        final AuctionResultInfo result = application.getAuctionResult();
        Assert.assertNotNull(result);
        final List<Bidder> winners = result.getWinners();
        Assert.assertEquals(2, winners.size());
        Assert.assertTrue(winners.get(0) instanceof BaseVerboseBidder);
        BaseVerboseBidder bidder1 = (BaseVerboseBidder) winners.get(0);
        Assert.assertEquals(BidderStrategyEnum.WEIGHTED, bidder1.getStrategyEnum());
        Assert.assertEquals("Bidder 1", bidder1.getBidderId());

        Assert.assertTrue(winners.get(1) instanceof BaseVerboseBidder);
        BaseVerboseBidder bidder2 = (BaseVerboseBidder) winners.get(1);
        Assert.assertEquals(BidderStrategyEnum.WEIGHTED, bidder2.getStrategyEnum());
        Assert.assertEquals("Bidder 2", bidder2.getBidderId());
    }

    /**
     * Tests "weighted" vs "weighted-correction" bidding strategies
     */
    @Test
    public void testWeightedVsWeightedCorrection() {
        application.run(
                "-b1", BidderStrategyEnum.WEIGHTED.getType(),
                "-b2", BidderStrategyEnum.WEIGHTED_CORRECTION.getType(),
                "-qty", "20", "-c", "500" /*, "-v" */);

        final AuctionResultInfo result = application.getAuctionResult();
        Assert.assertNotNull(result);
        final List<Bidder> winners = result.getWinners();
        Assert.assertEquals(1, winners.size());
        Assert.assertTrue(winners.get(0) instanceof BaseVerboseBidder);
        BaseVerboseBidder bidder = (BaseVerboseBidder) winners.get(0);
        Assert.assertEquals(BidderStrategyEnum.WEIGHTED_CORRECTION, bidder.getStrategyEnum());
        Assert.assertEquals("Bidder 2", bidder.getBidderId());
    }

    /**
     * Tests "equal" vs "weighted-correction" bidding strategies
     */
    @Test
    public void testEqualVsWeightedCorrection() {
        application.run(
                "-b1", BidderStrategyEnum.EQUAL.getType(),
                "-b2", BidderStrategyEnum.WEIGHTED_CORRECTION.getType(),
                "-qty", "20", "-c", "500" /*, "-v" */);

        final AuctionResultInfo result = application.getAuctionResult();
        Assert.assertNotNull(result);
        final List<Bidder> winners = result.getWinners();
        Assert.assertEquals(1, winners.size());
        Assert.assertTrue(winners.get(0) instanceof BaseVerboseBidder);
        BaseVerboseBidder bidder = (BaseVerboseBidder) winners.get(0);
        Assert.assertEquals(BidderStrategyEnum.WEIGHTED_CORRECTION, bidder.getStrategyEnum());
        Assert.assertEquals("Bidder 2", bidder.getBidderId());
    }

    /**
     * Tests "weighted-correction" vs "weighted-correction" bidding strategies
     */
    @Test
    public void testWeightedCorrectionVsWeightedCorrection() {
        application.run(
                "-b1", BidderStrategyEnum.WEIGHTED_CORRECTION.getType(),
                "-b2", BidderStrategyEnum.WEIGHTED_CORRECTION.getType(),
                "-qty", "20", "-c", "500" /*, "-v" */);

        final AuctionResultInfo result = application.getAuctionResult();
        Assert.assertNotNull(result);
        final List<Bidder> winners = result.getWinners();
        Assert.assertEquals(2, winners.size());
        Assert.assertTrue(winners.get(0) instanceof BaseVerboseBidder);
        BaseVerboseBidder bidder1 = (BaseVerboseBidder) winners.get(0);
        Assert.assertEquals(BidderStrategyEnum.WEIGHTED_CORRECTION, bidder1.getStrategyEnum());
        Assert.assertEquals("Bidder 1", bidder1.getBidderId());

        Assert.assertTrue(winners.get(1) instanceof BaseVerboseBidder);
        BaseVerboseBidder bidder2 = (BaseVerboseBidder) winners.get(1);
        Assert.assertEquals(BidderStrategyEnum.WEIGHTED_CORRECTION, bidder2.getStrategyEnum());
        Assert.assertEquals("Bidder 2", bidder2.getBidderId());
    }
}
