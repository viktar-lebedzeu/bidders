package com.optimax.bidders;

import com.optimax.bidders.auction.AuctionModerator;
import com.optimax.bidders.auction.AuctionResultPrinter;
import com.optimax.bidders.builder.BidderStrategyEnum;
import com.optimax.bidders.dto.AuctionResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashSet;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.optimax.bidders")
public class BiddersApplication implements CommandLineRunner {
    /** CLI options */
    private Options options = new Options();

    /** Auction moderator */
    private final AuctionModerator moderator;
    /** Auction result printer */
    private final AuctionResultPrinter resultPrinter;

    /** Verbose mode flag */
    private boolean verbose = false;
    /** Spring's environment */
    private final Environment env;

    public static void main(String[] args) {
        SpringApplication.run(BiddersApplication.class, args);
    }

    @Autowired
    public BiddersApplication(AuctionModerator moderator, AuctionResultPrinter resultPrinter, Environment env) {
        this.moderator = moderator;
        this.resultPrinter = resultPrinter;
        this.env = env;
    }

    @Override
    public void run(String... args) throws Exception {
        final String bidderPossibleValues = BidderStrategyEnum.possibleValues();

        HashSet<String> profilesSet = new HashSet<>(Arrays.asList(env.getActiveProfiles()));
        if ((args == null || args.length == 0) && profilesSet.contains("unittests")) {
            // Skipping running application without arguments in unit tests
            return;
        }

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
        if (parseParameters(args)) {
            runAuction();
        }
    }

    private void runAuction() {
        while (moderator.nextTurn());
        final AuctionResultInfo auctionResult = moderator.calculateAuctionResult();
        resultPrinter.print(auctionResult);
    }

    /**
     * Parses application arguments
     * @param args Application arguments
     * @return True if all required parameters are set, false otherwise
     */
    private boolean parseParameters(String... args) {
        CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine line = parser.parse(options, args);
            if (line.hasOption("h")) {
                printHelp();
            }

            final String bidderType1 = line.getOptionValue("b1");
            final String bidderType2 = line.getOptionValue("b2");

            if (verbose) {
                log.info("bidder 1 : {}", bidderType1);
                log.info("bidder 2 : {}", bidderType2);
                log.info("qty      : {}", line.getOptionValue("qty"));
                log.info("cash     : {}", line.getOptionValue("c"));
                log.info("verbose  : {}", line.hasOption("v"));
            }

            moderator.create(bidderType1, bidderType2,
                    Integer.valueOf(line.getOptionValue("qty")), Integer.valueOf(line.getOptionValue("c")));
            moderator.setVerbose(line.hasOption("v"));
        }
        catch (ParseException e) {
            // log.error("Cannot parse command line parameters. ", e);
            log.error(e.getMessage());
            printHelp();
            return false;
        }
        return true;
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        final String separator = StringUtils.repeat("=", 100);
        final String header = separator + "\n" + "Bidders application options\n" + separator + "\n";
        formatter.printHelp(150, "java -jar bidder.jar", header, options, separator, true);
    }
}
