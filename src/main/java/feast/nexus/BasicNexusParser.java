/*
 * Copyright (c) 2023 ETH Zurich
 *
 * This file is part of feast.
 *
 * feast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * feast is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with feast. If not, see <https://www.gnu.org/licenses/>.
 */

package feast.nexus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BasicNexusParser {

    BufferedReader fileReader;

    public class NexusCommand {
        public String name, args;

        public NexusCommand(String name, String args) {
            this.name = name;
            this.args = args;
        }
    }

    public class NexusBlock {
        public String blockType;
        public List<NexusCommand> commands;

        public NexusBlock(String blockType, List<NexusCommand> commands) {
            this.blockType = blockType.toLowerCase();
            this.commands = commands;
        }
    }

    public BasicNexusParser(BufferedReader fileReader) throws IOException {
        this.fileReader = fileReader;

        String header = this.fileReader.readLine().trim().toLowerCase();
        if (!header.equals("#nexus"))
            throw new IllegalArgumentException("Input file is not a NEXUS file.");
    }

    public NexusCommand getNextCommand() throws IOException {
        StringBuilder cmdStrBuilder = new StringBuilder();

        while (true) {
            int nextInt = fileReader.read();
            if (nextInt<0)
                return null;

            char nextChar = (char)nextInt;
            if (nextChar == ';')
                break;

            cmdStrBuilder.append(nextChar);
        }

        String fullCmdString = cmdStrBuilder.toString();
        String cmdName, cmdArgs = null;

        int idx = fullCmdString.indexOf(" ");
        if (idx<0)
            cmdName = fullCmdString.trim().toLowerCase();
        else {
            cmdName = fullCmdString.substring(0, idx).trim().toLowerCase();
            cmdArgs = fullCmdString.substring(idx + 1).trim();
        }

        return new NexusCommand(cmdName, cmdArgs);
    }

    public NexusBlock getNextBlock() throws IOException {

        NexusCommand command;
        List<NexusCommand> blockCommandList = new ArrayList<>();

        command = getNextCommand();
        while (!command.name.equals("begin")) {
            command = getNextCommand();

            if (command == null)
                return null;
        }

        String blockType = command.args;

        command = getNextCommand();
        while (!command.name.equals("end")) {
            blockCommandList.add(command);
            command = getNextCommand();

            if (command == null)
                throw new RuntimeException("File ended in the middle of a NEXUS file block.");
        }

        return new NexusBlock(blockType, blockCommandList);
    }

    public NexusBlock getNextBlockMatching(String blockType) throws IOException {
        NexusBlock block;

        do {
            block = getNextBlock();
        } while (block != null && !block.blockType.equals(blockType));

        return block;
    }
}
