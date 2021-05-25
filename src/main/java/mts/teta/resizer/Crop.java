package mts.teta.resizer;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import mts.teta.resizer.imageprocessor.BadAttributesException;

import java.io.File;

import static marvinplugins.MarvinPluginCollection.crop;
public class Crop {
    private Integer []  _crop;
    File                inputFile;
    File                outputFile;
    MarvinImage         _image;

    void doCrop(String fileParams){
        if(outputFile == null) {
            outputFile = new File(fileParams);
            _image = MarvinImageIO.loadImage(outputFile.getName());
        }
        else
            _image = MarvinImageIO.loadImage(fileParams);
        crop(_image.clone(), _image, _crop[0], _crop[1], _crop[2], _crop[3]);
        MarvinImageIO.saveImage(_image,outputFile.getName());
    }
    Crop(Integer[] crop, File inputFile, File outputFile, String fileParams) throws BadAttributesException {
        if(crop.length != 4 || crop[0] < 0 || crop[0] > Integer.MAX_VALUE
                || crop[1] < 0 ||crop[1] > Integer.MAX_VALUE ||  inputFile == null)
            throw new BadAttributesException("Please check params!");
        _crop = crop;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        doCrop(fileParams);
    }
}
