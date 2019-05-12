package com.optimax.bidders.auction;

import com.optimax.bidders.auction.impl.BaseVerboseBidder;
import com.optimax.bidders.dto.AuctionResultInfo;
import com.optimax.bidders.dto.BidInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.optimax.bidders.dto.AuctionResultInfo.BID_HEADER_1;
import static com.optimax.bidders.dto.AuctionResultInfo.BID_HEADER_2;
import static com.optimax.bidders.dto.AuctionResultInfo.WIN_POINTS_HEADER_1;
import static com.optimax.bidders.dto.AuctionResultInfo.WIN_POINTS_HEADER_2;

/**
 * Service that pints results of auction
 * @author Viktar Lebedzeu
 */
@Slf4j
@Service
public class AuctionResultPrinter {
    /** Default width of line of printed text */
    private static final int LINE_WIDTH = 100;

    /**
     * Prints auction results into the log
     * @param info Auction result info bean
     */
    public void print(AuctionResultInfo info) {
        log.info(StringUtils.repeat("=", LINE_WIDTH));
        log.info(StringUtils.center("Auction results", LINE_WIDTH));
        log.info(StringUtils.repeat("=", LINE_WIDTH));
        log.info(StringUtils.repeat(".", LINE_WIDTH));
        log.info("Quantity : {}", info.getInitialQuantity());
        log.info("Cash     : {}", info.getInitialCash());
        log.info("Bidder 1 : ({})", info.getBidder1Type().getType());
        log.info("Bidder 2 : ({})", info.getBidder2Type().getType());
        log.info(StringUtils.repeat(".", LINE_WIDTH));

        log.info("{} : {} : {} : {} : {} :",
                StringUtils.repeat(" ", info.getStepLength()),
                StringUtils.center(BID_HEADER_1, info.getBid1FieldLength()),
                StringUtils.center(BID_HEADER_2, info.getBid2FieldLength()),
                StringUtils.center(WIN_POINTS_HEADER_1, info.getWinPoints1FieldLength()),
                StringUtils.center(WIN_POINTS_HEADER_2, info.getWinPoints2FieldLength())
        );
        log.info(StringUtils.repeat(".", LINE_WIDTH));

        int step = 1;
        for (BidInfo bid : info.getBidInfos()) {
            String stepString = StringUtils.leftPad(StringUtils.EMPTY + step, info.getStepLength());
            log.info("{} : {} : {} : {} : {} :",
                    stepString,
                    StringUtils.rightPad(StringUtils.EMPTY + bid.getBidValue1(), info.getBid1FieldLength()),
                    StringUtils.rightPad(StringUtils.EMPTY + bid.getBidValue2(), info.getBid2FieldLength()),
                    StringUtils.rightPad(
                            (bid.getWinPoints1() > 0) ? StringUtils.EMPTY + bid.getWinPoints1() : StringUtils.EMPTY,
                            info.getWinPoints1FieldLength()),
                    StringUtils.rightPad(
                            (bid.getWinPoints2() > 0) ? StringUtils.EMPTY + bid.getWinPoints2() : StringUtils.EMPTY,
                            info.getWinPoints2FieldLength())
            );
            step++;
        }
        log.info(StringUtils.repeat(".", LINE_WIDTH));
        final BidInfo total = info.getTotal();
        log.info("{} : {} : {} : {} : {} :",
                StringUtils.repeat(" ", info.getStepLength()),
                StringUtils.rightPad(StringUtils.EMPTY + total.getBidValue1(), info.getBid1FieldLength()),
                StringUtils.rightPad(StringUtils.EMPTY + total.getBidValue2(), info.getBid2FieldLength()),
                StringUtils.rightPad(StringUtils.EMPTY + total.getWinPoints1(), info.getWinPoints1FieldLength()),
                StringUtils.rightPad(StringUtils.EMPTY + total.getWinPoints2(), info.getWinPoints2FieldLength())
        );
        log.info(StringUtils.repeat(".", LINE_WIDTH));
        log.info("Winner(s) :");
        for (Bidder winner : info.getWinners()) {
            log.info("            {}",
                    (winner instanceof BaseVerboseBidder)
                            ? ((BaseVerboseBidder) winner).getBidderId() +
                                " (" + ((BaseVerboseBidder) winner).getStrategyEnum().getType() + ")"
                            : winner.toString());
        }

        log.info(StringUtils.repeat("=", LINE_WIDTH));
    }
}
