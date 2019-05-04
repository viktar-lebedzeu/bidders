package com.optimax.bidders;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
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

    public static void main(String[] args) {
        SpringApplication.run(BiddersApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        options.addOption(
                Option.builder("b1")
                        .longOpt("bidder1")
                        .desc("Type of the first bidder")
                        .hasArg()
                        .argName("BIDDER 1")
                        .required(true)
                        .build()
        );
        options.addOption(
                Option.builder("b2")
                        .longOpt("bidder2")
                        .desc("Type of the second bidder")
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
    }

    private void parseParameters(String... args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h")) {
                printHelp();
            }

            log.info("bidder 1 : {}", line.getOptionValue("b1"));
            log.info("bidder 2 : {}", line.getOptionValue("b2"));
            log.info("qty      : {}", line.getOptionValue("qty"));
            log.info("cash     : {}", line.getOptionValue("c"));
            log.info("verbose  : {}", line.hasOption("v"));
        }
        catch (ParseException e) {
            // log.error("Cannot parse command line parameters. ", e);
            log.error(e.getMessage());
            printHelp();
        }
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("bidder", options);
    }
}
