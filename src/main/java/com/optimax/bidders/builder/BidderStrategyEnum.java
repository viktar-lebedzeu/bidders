package com.optimax.bidders.builder;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Bidder strategy enumeration
 * @author Viktar Lebedzeu
 */
public enum BidderStrategyEnum {
    /** "weighted" strategy */
    WEIGHTED("weighted"),
    /** "weighted with correction" strategy */
    WEIGHTED_CORRECTION("weighted-correction"),
    /** "equal" value strategy */
    EQUAL("equal"),
    /** Unknown value */
    UNKNOWN(StringUtils.EMPTY);

    /** Strategy type */
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

    /**
     * Returns string of all possible bidder strategy types
     * @return String of possible values
     */
    public static String possibleValues() {
        return Arrays.stream(BidderStrategyEnum.values())
                        .filter(v -> v != UNKNOWN)
                        .map(BidderStrategyEnum::getType)
                        .sorted()
                        .collect(Collectors.toList())
                .toString();
    }
}
