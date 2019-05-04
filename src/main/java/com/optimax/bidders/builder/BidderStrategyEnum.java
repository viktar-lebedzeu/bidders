package com.optimax.bidders.builder;

import org.apache.commons.lang3.StringUtils;

/**
 * Bidder strategy enumeration
 * @author Viktar Lebedzeu
 */
public enum BidderStrategyEnum {
    /** "weighted" stategy */
    WEIGHTED("weighted"),
    /** "equal" value strategy */
    EQUAL("equal"),
    /** Unknown value */
    UNKNOWN(StringUtils.EMPTY);

    /** Stategy type */
    private String type;

    /**
     * enumertion constructor
     * @param type enumeration type value
     */
    BidderStrategyEnum(String type) {
        this.type = type;
    }

    /**
     * Returns strategy enumeration type value
     * @return String value of strategy enumeration type
     */
    public String getType() {
        return type;
    }

    /**
     * Searches strategy enumeration by the given string value of type
     * @param type String type for search
     * @return Strategy enumeration value or UNKNOWN if nothing was found
     */
    public static BidderStrategyEnum findByType(String type) {
        for (BidderStrategyEnum e : BidderStrategyEnum.values()) {
            if (StringUtils.equalsIgnoreCase(type, e.getType())) {
                return e;
            }
        }
        return UNKNOWN;
    }
}