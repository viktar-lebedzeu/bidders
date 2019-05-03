package com.optimax.bidders.auction;

/**
 * Represents a bidder for the action.
 */
public interface Bidder {

    /**
     * Initializes the bidder with the production quantity and the allowed cash limit.
     * @param quantity the quantity
     * @param cash the cash limit
     */
    void init(int quantity, int cash);

    /**
     * Retrieves the next bid for the product, which may be zero.
     * @return the next bid
     * @param lotQty Product quantity in the auction lot
     */
    int placeBid(int lotQty);

    /**
     * Shows the bids of the two bidders.
     * @param own the bid of this bidder
     * @param other the bid of the other bidder
     */
    void bids(int own, int other);

    // ================================================================================

    /**
     * Returns the rest of bidder's cash
     * @return Bidder's cash
     */
    int getCash();

    /**
     * Returns product quantity points
     * @return Quantity points
     */
    int getQuantityPoints();

    /**
     * Adds product quantity points
     * @param points Product quantity points to add
     */
    void addQuantityPoints(int points);
}
