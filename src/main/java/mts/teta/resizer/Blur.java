package mts.teta.resizer;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import mts.teta.resizer.imageprocessor.BadAttributesException;

import java.io.File;

import static marvinplugins.MarvinPluginCollection.gaussianBlur;

public class Blur {
    private int     _blur;
    File            inputFile;
    File            outputFile;
    MarvinImage     _image;

    void doBlur(String fileParams){
        if(outputFile == null) {
            outputFile = new File(fileParams);
            _image = MarvinImageIO.loadImage(outputFile.getName());
        }
        else
            _image = MarvinImageIO.loadImage(fileParams);
        gaussianBlur(_image.clone(), _image, _blur);
        MarvinImageIO.saveImage(_image, outputFile.getName());
    }
    Blur(int blur, File inputFile, File outputFile, String fileParams) throws BadAttributesException {
        if(blur < 0 || blur > Integer.MAX_VALUE || inputFile == null)
            throw new BadAttributesException("Please check params!");
        _blur = blur;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        doBlur(fileParams);
    }
}
