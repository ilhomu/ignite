/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.commandline.cdc;

import org.apache.ignite.IgniteLogger;
import org.apache.ignite.internal.client.GridClientConfiguration;
import org.apache.ignite.internal.commandline.AbstractCommand;
import org.apache.ignite.internal.commandline.CommandArgIterator;

/**
 * CDC command.
 */
public class CdcCommand extends AbstractCommand<Object> {
    /** Cdc sub-command to execute. */
    private AbstractCommand<?> cmd;

    /** {@inheritDoc} */
    @Override public Object execute(GridClientConfiguration clientCfg, IgniteLogger log) throws Exception {
        return cmd.execute(clientCfg, log);
    }

    /** {@inheritDoc} */
    @Override public void parseArguments(CommandArgIterator argIter) {
        cmd = CdcSubcommands.of(argIter.nextArg("Expected CDC sub-command.")).subCommand();

        cmd.parseArguments(argIter);
    }

    /** {@inheritDoc} */
    @Override public Object arg() {
        return cmd.arg();
    }

    /** {@inheritDoc} */
    @Override public String confirmationPrompt() {
        return cmd == null ? null : cmd.confirmationPrompt();
    }

    /** {@inheritDoc} */
    @Override public void printUsage(IgniteLogger log) {
        for (CdcSubcommands cmd : CdcSubcommands.values())
            cmd.subCommand().printUsage(log);
    }

    /** {@inheritDoc} */
    @Override public String name() {
        return "cdc";
    }

    /** {@inheritDoc} */
    @Override public boolean experimental() {
        return true;
    }
}
