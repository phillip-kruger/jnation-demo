package pt.jnation.blockbuster.command;

import picocli.CommandLine;

@io.quarkus.picocli.runtime.annotations.TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = {RunWithGraphQLClient.class, RunWithMPRestClient.class})
public class TopCommand {
}

