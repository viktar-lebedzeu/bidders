package com.optimax.bidders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Bean of bid info
 * @author Viktar Lebedzeu
 */
@Data
@AllArgsConstructor
public class BidInfo {
    /** Auction bid 1 */
    private int bidValue1;
    /** Auction bid 2 */
    private int bidValue2;

    /** Win points 1 */
    private int winPoints1;
    /** Win points 2 */
    private int winPoints2;
}
