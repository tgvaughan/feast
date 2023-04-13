/*
 * Copyright (c) 2023 Tim Vaughan
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
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FeastMain {
    public static void main(String[] args) {

        System.out.println("Looking for services embedded in classpath:");

        String[] cpEntries = System.getProperty("java.class.path").split(File.pathSeparator);

        for (String cpEntry : cpEntries) {
            File file = new File(cpEntry);
            if (file.isDirectory()) {
                try (Stream<Path> stream = Files.walk(file.toPath())) {
                    stream.forEach(f -> {
                        System.out.println("Saw " + f);

                        if (f.getFileName().toString().equals("beast_services.xml")) {
                            System.out.println("Loading...");

                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            Document doc;
                            try {
                                doc = factory.newDocumentBuilder().parse(f.toFile());
                            } catch (SAXException | IOException |
                                     ParserConfigurationException e) {
                                throw new RuntimeException(e);
                            }
                            BEASTClassLoader.classLoader.addServices(
                                    doc.getDocumentElement().getAttribute("name"),
                                    PackageManager.parseServices(doc));
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if(file.isFile()) {
                System.out.println("Processing jar file: " + cpEntry);

                try (FileSystem fs = FileSystems.newFileSystem(file.toPath())) {
                    try (Stream<Path> stream = Files.walk(fs.getPath("/"))) {
                        stream.filter(f -> f.toString().contains("beast_services.xml")).forEach(f -> {
                            System.out.println("Saw " + f);

                            Document doc;
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            try {
                                InputStream is = Files.newInputStream(f);
                                doc = factory.newDocumentBuilder().parse(is);
                            } catch (IOException |
                                     ParserConfigurationException |
                                     SAXException e) {
                                throw new RuntimeException(e);
                            }

                            BEASTClassLoader.classLoader.addServices(
                                    doc.getDocumentElement().getAttribute("name"),
                                    PackageManager.parseServices(doc));
                        });
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        BeastMain.main(args);
    }
}
