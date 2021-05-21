package mts.teta.resizer;


import mts.teta.resizer.imageprocessor.BadAttributesException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyTest {
    private static final String CAT_COVER_SOURCE_NAME = "cat.jpg";
    private static final String CAT_COVER_TARGET_NAME = "cat.preview.jpg";
    private static final String CAT_COVER_SOURCE_NAME2 = "cat1.jpg";

    @Test
    public void testIncorrectFormatAttributes() throws Exception {

        URL res = getClass().getClassLoader().getResource(CAT_COVER_SOURCE_NAME2);
        assert res != null;

        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(CAT_COVER_SOURCE_NAME2, CAT_COVER_TARGET_NAME);

        File resFile = new File(absolutePathOutput);
        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(resFile);
        app.setFormat("TXT");
        BadAttributesException generatedException = null;
        try {
            app.call();
        } catch (BadAttributesException e) {
            generatedException = e;
        }

        assertEquals("Please check params!", generatedException.getMessage());
        assertEquals(BadAttributesException.class, generatedException.getClass());
    }

    @Test
    public void testBadAttributesSize() throws Exception {
        URL res = getClass().getClassLoader().getResource(CAT_COVER_SOURCE_NAME2);
        assert res != null;

        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(CAT_COVER_SOURCE_NAME2, CAT_COVER_TARGET_NAME);


        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        app.setResizeHeight(-123);
        app.setResizeWidth(123);
        app.setQuality(50);
        BadAttributesException generatedException = null;
        try {
            app.call();
        } catch (BadAttributesException e) {
            generatedException = e;
        }

        assertEquals("Please check params!", generatedException.getMessage());
        assertEquals(BadAttributesException.class, generatedException.getClass());
    }

    @Test
    public void testBadAttributesQuality() throws Exception {
        URL res = getClass().getClassLoader().getResource(CAT_COVER_SOURCE_NAME2);
        assert res != null;

        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(CAT_COVER_SOURCE_NAME2, CAT_COVER_TARGET_NAME);


        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        app.setResizeHeight(123);
        app.setResizeWidth(123);
        app.setQuality(500000);
        BadAttributesException generatedException = null;
        try {
            app.call();
        } catch (BadAttributesException e) {
            generatedException = e;
        }

        assertEquals("Please check params!", generatedException.getMessage());
        assertEquals(BadAttributesException.class, generatedException.getClass());
    }

    @Test
    public void testBadAttributesCrop() throws Exception {

        URL res = getClass().getClassLoader().getResource(CAT_COVER_SOURCE_NAME2);
        assert res != null;

        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(CAT_COVER_SOURCE_NAME2, CAT_COVER_TARGET_NAME);


        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        BadAttributesException generatedException = null;
        app.setCrop(-300,400,450,450);
        try {
            app.call();
        } catch (BadAttributesException e) {
            generatedException = e;
        }

        assertEquals("Please check params!", generatedException.getMessage());
        assertEquals(BadAttributesException.class, generatedException.getClass());
    }


}

