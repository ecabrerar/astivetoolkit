/* 
 * Copyright (C) 2010-2012 PhonyTive LLC
 * http://www.phonytive.com/astive
 *
 * This file is part of Astive Toolkit
 *
 * Astive is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Astive is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Astive.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.phonytive.astive.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import org.apache.log4j.Logger;

import com.phonytive.astive.server.admin.AdminCommand;
import com.phonytive.astive.server.admin.AdminDaemon;
import com.phonytive.astive.server.admin.AdminDaemonClient;
import com.phonytive.astive.server.appmanager.DeployerManager;
import com.phonytive.astive.server.monitor.ConnectionMonitor;
import com.phonytive.astive.server.monitor.FastAgiConnectionMonitor;
import com.phonytive.astive.server.utils.InitOutput;
import com.phonytive.astive.server.utils.ServiceProperties;
import com.phonytive.astive.server.utils.ServicePropertiesImpl;
import com.phonytive.astive.util.AppLocale;

/**
 *
 * @since 1.0.0 
 * @see AbstractAstiveServer
 */
public class AstiveServer extends AbstractAstiveServer {
    // A usual logging class

    private static final Logger logger = Logger.getLogger(AstiveServer.class);
    private static ServiceProperties adminDaemonSP;
    private static ServiceProperties astivedSP;
    private static ServiceProperties telnedSP;
    private static String ASTIVED_PROPERTIES =
            AbstractAstiveServer.ASTIVE_HOME + "/conf/astived.properties";
    private static String ADMIN_DAEMON_PROPERTIES =
            AbstractAstiveServer.ASTIVE_HOME + "/conf/admin.properties";
    private static String TELNED_PROPERTIES =
            AbstractAstiveServer.ASTIVE_HOME + "/conf/telned.properties";
    private ExecutorService executorService;

    /**
     * Creates a new AstiveServer object.
     *
     * @param port DOCUMENT ME!
     * @param backlog DOCUMENT ME!
     * @param bindAddr DOCUMENT ME!
     *
     * @throws SystemException DOCUMENT ME!
     * @throws IOException DOCUMENT ME!
     */
    public AstiveServer(int port, int backlog, InetAddress bindAddr)
            throws SystemException, IOException {
        super(port, backlog, bindAddr);
        //DOMConfigurator.configure("conf/log4j.xml");
    }

    private static ServiceProperties getServiceProperties(String propPath, String serviceName)
            throws SystemException, IOException {
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(propPath));

            return new ServicePropertiesImpl(prop, serviceName);
        } catch (FileNotFoundException ex) {
            throw new SystemException(AppLocale.getI18n("unableToReadFile",
                    new Object[]{propPath, ex.getMessage()}));
        }
    }

    private static boolean isCommand(String cmd) {
        AdminCommand ac = AdminCommand.get(cmd);
        if(ac == null) {
            return false;
        }
        return true;
    }

    private static boolean isFileJar(String file) {
        if (file.endsWith(".jar")) {
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void launchConnectionMonitor() {
        ConnectionMonitor monitor = new FastAgiConnectionMonitor(this, astivedSP.getBacklog());
        executorService.execute(monitor);
    }

    public static void main(String[] args) throws Exception {
        astivedSP = getServiceProperties(ASTIVED_PROPERTIES, "astived");
        adminDaemonSP = getServiceProperties(ADMIN_DAEMON_PROPERTIES, "admin thread");
        telnedSP = getServiceProperties(TELNED_PROPERTIES, "telned");

        ArrayList<ServiceProperties> serviceProperties = new ArrayList();
        serviceProperties.add(astivedSP);
        serviceProperties.add(adminDaemonSP);
        serviceProperties.add(telnedSP);

        if ((args.length == 0) || args[0].equals("-h") || args[0].equals("--help")) {
            printUsage();
            System.exit(1);
        }

        // Create a Parser
        CommandLineParser parser = new BasicParser();

        Options start = new Options();
        start.addOption("h", "help", false, AppLocale.getI18n("cli.option.printUsage"));
        start.addOption("v", "version", false, "Prints the Astive Server version and exits.");
        start.addOption("d", "debug", false, AppLocale.getI18n("cli.option.debug"));
        start.addOption("q", "quiet", false, AppLocale.getI18n("cli.option.daemonMode"));
        start.addOption(OptionBuilder.hasArg(true).withArgName("host").withLongOpt("admin-bind").withDescription(""
                + AppLocale.getI18n("cli.option.bind",
                new Object[]{"admin"})).create());
        start.addOption(OptionBuilder.hasArg(true).withArgName("port").withLongOpt("admin-port").withDescription(""
                + AppLocale.getI18n("cli.option.port",
                new Object[]{"admin"})).create());
        start.addOption(OptionBuilder.hasArg(true).withArgName("port").withLongOpt("astived-port").withDescription(""
                + AppLocale.getI18n("cli.option.port",
                new Object[]{"astived"})).create());

        start.addOption(OptionBuilder.hasArg(true).withArgName("host").withLongOpt("astived-host").withDescription(""
                + AppLocale.getI18n("cli.option.bind",
                new Object[]{"astived"})).create());
        start.addOption(OptionBuilder.hasArg(true).withArgName("port").withLongOpt("telned-port").withDescription(""
                + AppLocale.getI18n("cli.option.port",
                new Object[]{"telned"})).create());

        start.addOption(OptionBuilder.hasArg(true).withArgName("host").withLongOpt("telned-host").withDescription(""
                + AppLocale.getI18n("cli.option.host",
                new Object[]{"telned"})).create());

        Options stop = new Options();
        stop.addOption(OptionBuilder.withLongOpt("--help").withDescription("" + AppLocale.getI18n("cli.option.printUsage")).create());

        stop.addOption("h", "host", false,
                AppLocale.getI18n("cli.option.stop.host",
                new Object[]{DEFAULT_AGI_SERVER_BIND_ADDR}));

        stop.addOption("p", "port", false,
                AppLocale.getI18n("cli.option.stop.port",
                new Object[]{DEFAULT_AGI_SERVER_PORT}));

        Options deploy = new Options();
        deploy.addOption("h", "help", false, AppLocale.getI18n("cli.option.printUsage"));

        Options undeploy = new Options();
        undeploy.addOption("h", "help", false, AppLocale.getI18n("cli.option.printUsage"));

        if (args.length == 0) {
            printUsage();
            System.exit(1);
        } else if (!isCommand(args[0])) {
            printUnavailableCmd(args[0]);
            System.exit(1);
        }

        AdminCommand cmd = AdminCommand.get(args[0]);

        if (cmd.equals(AdminCommand.DEPLOY) && ((args.length < 2) || !isFileJar(args[1]))) {
            logger.error(AppLocale.getI18n("cli.invalid.app"));
            printUsage(AdminCommand.DEPLOY, deploy);
            System.exit(1);
        }

        if (cmd.equals(AdminCommand.UNDEPLOY) && ((args.length < 2) || !isFileJar(args[1]))) {
            printUsage(AdminCommand.UNDEPLOY, undeploy);
            System.exit(1);
        }

        // Parse the program arguments
        try {
            if (cmd.equals(AdminCommand.START)) {
                CommandLine commandLine = parser.parse(start, args);

                if (commandLine.hasOption('h')) {
                    printUsage(cmd, start);
                    System.exit(0);
                }

                if (commandLine.hasOption('v')) {
                    // Them start the server without noise
                }

                if (commandLine.hasOption("astived-bind")) {
                    astivedSP.setBindAddr(InetAddress.getByName(commandLine.getOptionValue("astived-port")));
                }

                if (commandLine.hasOption("astived-port")) {
                    astivedSP.setPort(Integer.parseInt(commandLine.getOptionValue("astived-port")));
                }

                if (commandLine.hasOption("admin-bind")) {
                    adminDaemonSP.setBindAddr(InetAddress.getByName(commandLine.getOptionValue("admin-bind")));
                }

                if (commandLine.hasOption("admin-port")) {
                    adminDaemonSP.setPort(Integer.parseInt(commandLine.getOptionValue("admin-port")));
                }

                if (commandLine.hasOption("telned-bind")) {
                    telnedSP.setBindAddr(InetAddress.getByName(commandLine.getOptionValue("telned-bind")));
                }

                if (commandLine.hasOption("telned-port")) {
                    adminDaemonSP.setPort(Integer.parseInt(commandLine.getOptionValue("telned-port")));
                }

                InitOutput.printInit(serviceProperties);

                AstiveServer server =
                        new AstiveServer(astivedSP.getPort(), astivedSP.getBacklog(), astivedSP.getBindAddr());
                server.start();
            }

            if (cmd.equals(AdminCommand.STOP)) {
                CommandLine commandLine = parser.parse(stop, args);

                if (commandLine.hasOption("--help")) {
                    printUsage(cmd, stop);
                    System.exit(0);
                }

                if (commandLine.hasOption('h')) {
                    if (commandLine.getOptionValue('h') == null) {
                        printUsage(cmd, stop);
                        System.exit(0);
                    }

                    astivedSP.setBindAddr(InetAddress.getByName(commandLine.getOptionValue('h')));
                }

                if (commandLine.hasOption('p')) {
                    if (commandLine.getOptionValue('p') == null) {
                        printUsage(cmd, stop);
                        System.exit(0);
                    }

                    astivedSP.setPort(Integer.parseInt(commandLine.getOptionValue('p')));
                }

                AdminDaemonClient adClient =
                        new AdminDaemonClient(adminDaemonSP.getBindAddr(), adminDaemonSP.getPort());
                adClient.stop();
            }

            // TODO: This needs to be researched before a full implementation.
            // for now is only possible to do deployments into the local server.
            if (cmd.equals(AdminCommand.DEPLOY)) {
                CommandLine commandLine = parser.parse(deploy, args);

                //if (commandLine.hasOption('h')) {
                //    printUsage(cmd, deploy);
                //    System.exit(0);
                //}                
                AdminDaemonClient adClient =
                        new AdminDaemonClient(adminDaemonSP.getBindAddr(), adminDaemonSP.getPort());
                adClient.deploy(args[1]);
            }

            if (cmd.equals(AdminCommand.UNDEPLOY)) {
                CommandLine commandLine = parser.parse(undeploy, args);

                //if (commandLine.hasOption('h')) {
                //    printUsage(cmd, undeploy);
                //    System.exit(0);
                //}                
                AdminDaemonClient adClient =
                        new AdminDaemonClient(adminDaemonSP.getBindAddr(), adminDaemonSP.getPort());
                adClient.undeploy(args[1]);
            }
        } catch (java.net.ConnectException ex) {
            logger.info("serverNotRunning");
        } catch (Exception ex) {
            logger.error(AppLocale.getI18n("unexpectedError", new Object[]{ex.getMessage()}));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Support methods">
    private static void printUnavailableCmd(String cmd) {
        System.out.println(AppLocale.getI18n("cli.unavailableCommand", new Object[]{cmd}));
        System.out.println(AppLocale.getI18n("cli.availableCommands"));
    }
    
    private static void printUsage() {
        System.out.println(AppLocale.getI18n("cli.usage"));
        System.out.println(AppLocale.getI18n("cli.availableCommands"));
        System.out.println(AppLocale.getI18n("cli.help"));
        System.out.println(AppLocale.getI18n("cli.footer"));
    }

    private static void printUsage(AdminCommand ac, Options options) {
        String command = ac.getCommand();
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.setWidth(80);
        helpFormatter.printHelp(AppLocale.getI18n("cli." + command + ".usage"),
                AppLocale.getI18n("cli.header"), options, AppLocale.getI18n("cli.footer"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() throws SystemException {
        super.start();

        // Load properties
        InetAddress adminBindAddr = adminDaemonSP.getBindAddr();
        int adminPort = adminDaemonSP.getPort();
        int backlog = adminDaemonSP.getBacklog();

        // Load apps already in "apps"
        DeployerManager.getInstance();

        executorService = Executors.newFixedThreadPool(2);
        launchConnectionMonitor();

        /*executorService.execute(new Runnable() {
        @Override
        public void run() {
        launchConnectionMonitor();
        }
        });*/
        try {
            AdminDaemon admin;
            admin = new AdminDaemon(adminPort, backlog, adminBindAddr, this);
            executorService.execute(admin);
        } catch (IOException ex) {
            // XXX:
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() throws SystemException {
        executorService.shutdown();
        super.stop();
    }
    // </editor-fold>
}