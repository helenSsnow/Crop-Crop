package mts.teta.resizer;

import mts.teta.resizer.imageprocessor.BadAttributesException;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class ResizeAndQuality {
    private Integer []      _size;
    private double          _quality;
    File                    inputFile;
    File                    outputFile;

    void doResize(int flag) throws IOException {
        if(flag == 1){
            Thumbnails.of(inputFile)
                    .size(_size[0], _size[1])
                    .keepAspectRatio(false)
                    .toFile(outputFile);
        }
        else if(flag == 2){
            Thumbnails.of(inputFile)
                    .outputQuality(_quality)
                    .toFile(outputFile);
        }
        else if(flag == 3){
            Thumbnails.of(inputFile)
                    .size(_size[0], _size[1])
                    .keepAspectRatio(false)
                    .outputQuality(_quality)
                    .toFile(outputFile);
        }
    }

    ResizeAndQuality(Integer [] size, double quality, File inputFile, File outputFile, String[] fileParams) throws BadAttributesException, IOException {
       int flag = 0;
        if(size.length != 2 || inputFile == null || quality > 100 ||
                size[0] < 0 || size[0] > Integer.MAX_VALUE || size[1] < 0 || size[1] > Integer.MAX_VALUE)
            throw new BadAttributesException("Please check params!");
        _size = size;
        _quality = quality / 100.0;
        this.inputFile = inputFile;
        if(size != null && quality < 0.0)
            flag = 1;
        else if(size == null && quality >= 0.0)
            flag = 2;
        else if(size != null && quality >= 0.0)
            flag = 3;
        if(outputFile == null)
            this.outputFile = new File(fileParams[1]);
        else
            this.outputFile = outputFile;
        doResize(flag);
    }
}
