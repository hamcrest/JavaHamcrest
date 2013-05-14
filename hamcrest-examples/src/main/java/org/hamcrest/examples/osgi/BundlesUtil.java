package org.hamcrest.examples.osgi;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Tibor Digana (tibor17)
 */
final class BundlesUtil {

    private BundlesUtil() {
    }

    static Collection<String> relativePathFiles(String relativePath, final String... fileNamesRegex) {
        relativePath = relativePath.trim();
        File dir = new File(relativePath).getAbsoluteFile();
        File[] jars = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.isFile()) {
                    String fileName = pathname.getName();
                    for (String regex : fileNamesRegex) {
                        if (fileName.matches(regex)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        ArrayList<String> paths = new ArrayList<String>(jars.length);
        relativePath = relativePath.length() == 0 ? "" : relativePath + "/";
        for (File jar : jars) {
            paths.add(relativePath + jar.getName());
        }
        return paths;
    }
}
