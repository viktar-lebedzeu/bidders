package com.optimax.bidders;

import com.optimax.bidders.auction.AuctionModerator;
import com.optimax.bidders.auction.Bidder;
import com.optimax.bidders.builder.BidderBuilder;
import com.optimax.bidders.builder.BidderStrategyEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BiddersApplication implements CommandLineRunner {

    // Command line parameters
    private static final String PARAM_BIDDER1 =     "-bidder1";
    private static final String PARAM_BIDDER2 =     "-bidder2";
    private static final String PARAM_QUANTITY =    "-qty";
    private static final String PARAM_CASH =        "-cash";

    /** Required CMD parameters */
    private static final String[] REQUIRED_PARAMS = new String[] {
            PARAM_BIDDER1, PARAM_BIDDER2, PARAM_CASH, PARAM_QUANTITY
    };

    /** CLI options */
    private Options options = new Options();

    /** Auction moderator */
    private AuctionModerator moderator;
    /** Verbose mode flag */
    private boolean verbose = false;

    public static void main(String[] args) {
        SpringApplication.run(BiddersApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final String bidderPossibleValues = BidderStrategyEnum.possibleValues();
        options.addOption(
                Option.builder("b1")
                        .longOpt("bidder1")
                        .desc("Type of the first bidder " + bidderPossibleValues)
                        .hasArg()
                        .argName("BIDDER 1")
                        .required(true)
                        .build()
        );
        options.addOption(
                Option.builder("b2")
                        .longOpt("bidder2")
                        .desc("Type of the second bidder " + bidderPossibleValues)
                        .hasArg()
                        .argName("BIDDER 2")
                        .required(true)
                        .build()
        );
        options.addOption(
                Option.builder("qty")
                        .longOpt("quantity")
                        .desc("Quantity of product items to be auctioned")
                        .hasArg()
                        .argName("QUANTITY UNITS (QU)")
                        .required(true)
                        .type(Integer.class)
                        .build()
        );
        options.addOption(
                Option.builder("c")
                        .longOpt("cash")
                        .desc("Initial amount of money")
                        .hasArg()
                        .argName("MONETARY UNITS (MU)")
                        .required(true)
                        .type(Integer.class)
                        .build()
        );
        options.addOption(
                Option.builder("v")
                        .longOpt("verbose")
                        .desc("Turn on verbose messages")
                        .required(false)
                        .build()
        );
        options.addOption(
                Option.builder("h")
                        .longOpt("help")
                        .desc("Prints help message")
                        .required(false)
                        .build()
        );
        parseParameters(args);
        runAuction();
    }

    private void runAuction() {
        if (moderator != null) {
            while (moderator.nextTurn());
        }
    }

    private void parseParameters(String... args) {
        CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine line = parser.parse(options, args);
            if (line.hasOption("h")) {
                printHelp();
            }

            final String bidderType1 = line.getOptionValue("b1");
            final String bidderType2 = line.getOptionValue("b2");

            log.info("bidder 1 : {}", bidderType1);
            log.info("bidder 2 : {}", line.getOptionValue("b2"));
            log.info("qty      : {}", line.getOptionValue("qty"));
            log.info("cash     : {}", line.getOptionValue("c"));
            log.info("verbose  : {}", line.hasOption("v"));

            moderator = AuctionModerator.create(bidderType1, bidderType2,
                    Integer.valueOf(line.getOptionValue("qty")), Integer.valueOf(line.getOptionValue("c")));
            moderator.setVerbose(line.hasOption("v"));
        }
        catch (ParseException e) {
            // log.error("Cannot parse command line parameters. ", e);
            log.error(e.getMessage());
            printHelp();
        }
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        final String separator = StringUtils.repeat("=", 100);
        final String header = separator + "\n" + "Bidders application options\n" + separator + "\n";
        formatter.printHelp(150, "java -jar bidder.jar", header, options, separator, true);
    }

    private Bidder createBidder(String bidderType) {
        return BidderBuilder.createBidderByType(bidderType);
    }
}
