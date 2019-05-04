package com.optimax.bidders.builder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test suite to test features of BidderStrategyEnum class
 * @author Viktar Lebedzeu
 */
@Slf4j
public class BidderStrategyEnumTest {
    @Test
    public void testFindByType() {
        for (BidderStrategyEnum s : BidderStrategyEnum.values()) {
            BidderStrategyEnum strategy = BidderStrategyEnum.findByType(s.getType().toUpperCase());
            Assert.assertEquals(s, strategy);
        }

        Assert.assertEquals(BidderStrategyEnum.UNKNOWN, BidderStrategyEnum.findByType("Wrong value"));
        Assert.assertEquals(BidderStrategyEnum.UNKNOWN, BidderStrategyEnum.findByType(StringUtils.EMPTY));
        Assert.assertEquals(BidderStrategyEnum.UNKNOWN, BidderStrategyEnum.findByType(null));
    }
}
