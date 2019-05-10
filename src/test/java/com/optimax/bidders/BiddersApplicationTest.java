package com.optimax.bidders;

import com.optimax.bidders.builder.BidderStrategyEnum;
import com.optimax.bidders.config.BiddersApplicationTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ "unittests" })
@SpringBootTest(classes = { BiddersApplication.class } )
@Slf4j
public class BiddersApplicationTest {
    @Autowired
    private BiddersApplication application;

    @Autowired
    private BiddersApplicationTestConfig config;

    @Test
    public void testApplication() throws Exception {
        log.info("Running testApplication()...");
        application.run("-b1", BidderStrategyEnum.WEIGHTED.getType(), "-b2", BidderStrategyEnum.EQUAL.getType(),
                "-qty", "21", "-c", "100", "-v");
    }
}
