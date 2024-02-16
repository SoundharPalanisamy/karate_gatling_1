package com.intuit.karate.resource;

import com.intuit.karate.FileUtils;
import com.intuit.karate.core.Feature;
import com.intuit.karate.core.FeatureCall;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pthomas3
 */
class ResourceUtilsTest {

    static final Logger logger = LoggerFactory.getLogger(ResourceUtilsTest.class);
    
    static File wd = FileUtils.WORKING_DIR;

    @Test
    void testFindFilesByExtension() {
        Collection<Resource> list = ResourceUtils.findResourcesByExtension(wd, "txt", "src/test/java/com/intuit/karate/resource");
        assertEquals(1, list.size());
        Resource resource = list.iterator().next();
        assertTrue(resource.isFile());
        assertFalse(resource.isClassPath());
        assertEquals("src/test/java/com/intuit/karate/resource/test1.txt", resource.getRelativePath());
        assertEquals("src/test/java/com/intuit/karate/resource/test1.txt", resource.getPrefixedPath());
        assertEquals("foo", FileUtils.toString(resource.getStream()));
    }

    @Test
    void testGetFileByPath() {
        Resource resource = ResourceUtils.getResource(wd, "src/test/java/com/intuit/karate/resource/test1.txt");
        assertTrue(resource.isFile());
        assertFalse(resource.isClassPath());
        assertEquals("src/test/java/com/intuit/karate/resource/test1.txt", resource.getRelativePath());
        assertEquals("src/test/java/com/intuit/karate/resource/test1.txt", resource.getPrefixedPath());
        assertEquals("foo", FileUtils.toString(resource.getStream()));
    }

    @Test
    void testResolveFile() {
        Resource temp = ResourceUtils.getResource(wd, "src/test/java/com/intuit/karate/resource/test1.txt");
        Resource resource = temp.resolve("test2.log");
        assertTrue(resource.isFile());
        assertFalse(resource.isClassPath());
        assertEquals("src/test/java/com/intuit/karate/resource/test2.log", resource.getRelativePath());
        assertEquals("src/test/java/com/intuit/karate/resource/test2.log", resource.getPrefixedPath());
        assertEquals("bar", FileUtils.toString(resource.getStream()));
    }

    @Test
    void testResolveRelativeFile() {
        Resource temp = ResourceUtils.getResource(wd, "src/test/java/com/intuit/karate/resource/dir1/dir1.log");
        Resource resource = temp.resolve("../dir2/dir2.log");
        assertTrue(resource.isFile());
        assertFalse(resource.isClassPath());
        assertEquals("src/test/java/com/intuit/karate/resource/dir1/../dir2/dir2.log", resource.getRelativePath());
        assertEquals("src/test/java/com/intuit/karate/resource/dir1/../dir2/dir2.log", resource.getPrefixedPath());
        assertEquals("src.test.java.com.intuit.karate.resource.dir1.dir2.dir2.log", resource.getPackageQualifiedName());
        assertEquals("bar", FileUtils.toString(resource.getStream()));
    }

    @Test
    void testFindClassPathFilesByExtension() {
        Collection<Resource> list = ResourceUtils.findResourcesByExtension(wd, "txt", "classpath:com/intuit/karate/resource");
        assertEquals(1, list.size());
        Resource resource = list.iterator().next();
        assertTrue(resource.isFile());
        assertTrue(resource.isClassPath());
        assertEquals("com/intuit/karate/resource/test1.txt", resource.getRelativePath());
        assertEquals("classpath:com/intuit/karate/resource/test1.txt", resource.getPrefixedPath());
        assertEquals("foo", FileUtils.toString(resource.getStream()));
    }

    @Test
    void testGetClassPathFileByPath() {
        Resource resource = ResourceUtils.getResource(wd, "classpath:com/intuit/karate/resource/test1.txt");
        assertTrue(resource.isFile());
        assertTrue(resource.isClassPath());
        assertEquals("com/intuit/karate/resource/test1.txt", resource.getRelativePath());
        assertEquals("classpath:com/intuit/karate/resource/test1.txt", resource.getPrefixedPath());
        assertEquals("foo", FileUtils.toString(resource.getStream()));
    }

    @Test
    void testResolveClassPathFile() {
        Resource temp = ResourceUtils.getResource(wd, "classpath:com/intuit/karate/resource/test1.txt");
        Resource resource = temp.resolve("test2.log");
        assertTrue(resource.isFile());
        assertTrue(resource.isClassPath());
        assertEquals("com/intuit/karate/resource/test2.log", resource.getRelativePath());
        assertEquals("classpath:com/intuit/karate/resource/test2.log", resource.getPrefixedPath());
        assertEquals("bar", FileUtils.toString(resource.getStream()));
    }

    @Test
    void testResolveRelativeClassPathFile() {
        Resource temp = ResourceUtils.getResource(new File(""), "classpath:com/intuit/karate/resource/dir1/dir1.log");
        Resource resource = temp.resolve("../dir2/dir2.log");
        assertTrue(resource.isFile());
        assertTrue(resource.isClassPath());
        assertEquals("com/intuit/karate/resource/dir1/../dir2/dir2.log", resource.getRelativePath());
        assertEquals("classpath:com/intuit/karate/resource/dir1/../dir2/dir2.log", resource.getPrefixedPath());
        assertEquals("bar", FileUtils.toString(resource.getStream()));
    }

    @Test
    void testGetFeatureWithLineNumber() {
        String path = "classpath:com/intuit/karate/resource/test.feature:6";
        List<FeatureCall> features = ResourceUtils.findFeatureFiles(new File(""), Collections.singletonList(path), null);
        assertEquals(1, features.size());
        assertEquals(6, features.get(0).callLine);
    }
    
    @Test
    void testGetFeatureWithCallTag() {
        String path = "classpath:com/intuit/karate/resource/test.feature@second";
        List<FeatureCall> features = ResourceUtils.findFeatureFiles(new File(""), Collections.singletonList(path), null);
        assertEquals(1, features.size());
        assertEquals("@second", features.get(0).callTag);
    }    

    @Test
    void testClassPathToFileThatExists() {
        File file = ResourceUtils.classPathToFile("com/intuit/karate/resource/test1.txt");
        assertTrue(file.exists());
    }

    @Test
    void testClassPathToFileThatDoesNotExist() {
        File file = ResourceUtils.classPathToFile("com/intuit/karate/resource/nope.txt");
        assertNull(file);
    }    

}
