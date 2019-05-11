package com.optimax.bidders.dto;

import com.optimax.bidders.auction.Bidder;
import com.optimax.bidders.auction.impl.BaseVerboseBidder;
import com.optimax.bidders.builder.BidderStrategyEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Viktar Lebedzeu
 */
@Data
@NoArgsConstructor
public class AuctionResultInfo {
    public static final String BID_HEADER_1 = "Bid 1";
    public static final String BID_HEADER_2 = "Bid 2";
    public static final String WIN_POINTS_HEADER_1 = "Win 1";
    public static final String WIN_POINTS_HEADER_2 = "Win 2";

    /** List of bids */
    private List<BidInfo> bidInfos;
    /** Totals record */
    private BidInfo total;
    /** List of winner(s) */
    private List<Bidder> winners = new ArrayList<>(2);
    /** List of bidders */
    private List<Bidder> bidders;

    /** Initial quantity of product */
    private int initialQuantity;
    /** Initial value of bidder's cash */
    private int initialCash;

    private int stepLength = 0;
    private int bid1FieldLength = 0;
    private int bid2FieldLength = 0;
    private int winPoints1FieldLength = 0;
    private int winPoints2FieldLength = 0;


    private Bidder bidder1;
    private Bidder bidder2;
    private BidderStrategyEnum bidder1Type = BidderStrategyEnum.UNKNOWN;
    private BidderStrategyEnum bidder2Type = BidderStrategyEnum.UNKNOWN;


    public void init(int initialQuantity, int initialCash, List<Bidder> bidders, List<BidInfo> bidInfos) {
        this.initialQuantity = initialQuantity;
        this.initialCash = initialCash;
        this.bidders = bidders;
        this.bidInfos = bidInfos;

        stepLength = (StringUtils.EMPTY + bidInfos.size()).length();

        bidder1 = bidders.get(0);
        bidder2 = bidders.get(1);
        if (bidder1 instanceof BaseVerboseBidder) {
            bidder1Type = ((BaseVerboseBidder) bidder1).getStrategyEnum();
        }
        if (bidder2 instanceof BaseVerboseBidder) {
            bidder2Type = ((BaseVerboseBidder) bidder2).getStrategyEnum();
        }

        final int totalBids1 = bidInfos.parallelStream().mapToInt(BidInfo::getBidValue1).sum();
        final int totalBids2 = bidInfos.parallelStream().mapToInt(BidInfo::getBidValue2).sum();
        final int totalWinPoints1 = bidInfos.parallelStream().mapToInt(BidInfo::getWinPoints1).sum();
        final int totalWinPoints2 = bidInfos.parallelStream().mapToInt(BidInfo::getWinPoints2).sum();
        total = new BidInfo(totalBids1, totalBids2, totalWinPoints1, totalWinPoints2);

        bid1FieldLength = (BID_HEADER_1.length() > (StringUtils.EMPTY + totalBids1).length())
                ? BID_HEADER_1.length() : (StringUtils.EMPTY + totalBids1).length();
        bid2FieldLength = (BID_HEADER_2.length() > (StringUtils.EMPTY + totalBids2).length())
                ? BID_HEADER_2.length() : (StringUtils.EMPTY + totalBids2).length();
        winPoints1FieldLength = (WIN_POINTS_HEADER_1.length() > (StringUtils.EMPTY + totalWinPoints1).length())
                ? WIN_POINTS_HEADER_1.length() : (StringUtils.EMPTY + totalWinPoints1).length();
        winPoints2FieldLength = (WIN_POINTS_HEADER_2.length() > (StringUtils.EMPTY + totalWinPoints2).length())
                ? WIN_POINTS_HEADER_2.length() : (StringUtils.EMPTY + totalWinPoints2).length();

        if (totalWinPoints1 > totalWinPoints2) {
            // Bidder 1 wins
            winners.add(bidder1);
        }
        else if (totalWinPoints2 > totalWinPoints1) {
            // Bidder 2 wins
            winners.add(bidder2);
        }
        else {
            if (totalBids1 < totalBids2) {
                winners.add(bidder1);
            }
            else if (totalBids2 < totalBids1) {
                winners.add(bidder2);
            }
            else {
                // equal result
                winners.add(bidder1);
                winners.add(bidder2);
            }
        }
    }
}
