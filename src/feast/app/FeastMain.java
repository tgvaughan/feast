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

package feast.app;

import beast.pkgmgmt.BEASTClassLoader;
import beast.pkgmgmt.PackageManager;
import beastfx.app.beast.BeastMain;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class FeastMain {
    public static void main(String[] args) {

        System.out.println("************************************");
        System.out.println("**** FeastMain BEAST 2 Launcher ****");
        System.out.println("************************************\n");
        System.out.println("Hunting for beast_services.xml files embedded in classpath:");

        String[] cpEntries = System.getProperty("java.class.path").split(File.pathSeparator);

        Arrays.stream(cpEntries)
                .map(File::new)
                .filter(f -> f.isDirectory() || (f.isFile() && f.toString().endsWith(".jar")))
                .map(f -> {
                    if (f.isDirectory())
                        return f.toPath();
                    else {
                        try {
                            return FileSystems.newFileSystem(f.toPath()).getPath("/");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .forEach(p -> {
                    try (Stream<Path> stream = Files.walk(p)) {
                        stream.filter(p2 -> p2.getFileName() != null)
                                .filter(p2 -> p2.getFileName().toString().equals("beast_services.xml"))
                                .forEach(p2 -> {
                            System.out.println("- Processing " + p2 + "...");

                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            Document doc;

                            try {
                                InputStream is = Files.newInputStream(p2);
                                doc = factory.newDocumentBuilder().parse(is);
                            } catch (SAXException | IOException |
                                     ParserConfigurationException e) {
                                throw new RuntimeException(e);
                            }

                            BEASTClassLoader.classLoader.addServices(
                                    doc.getDocumentElement().getAttribute("name"),
                                    PackageManager.parseServices(doc));
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        System.out.println("Done. Handing over to BeastMain...");

        BeastMain.main(args);
    }
}
