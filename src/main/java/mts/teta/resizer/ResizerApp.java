package mts.teta.resizer;

import mts.teta.resizer.imageprocessor.BadAttributesException;
import picocli.CommandLine;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "resizer", mixinStandardHelpOptions = true, version = "resizer 0.0.1", description = "...")

class ConsoleAttributes{
    @CommandLine.Parameters
    String[] fileParams;
    @CommandLine.Option(names = {"--resize"}, split = ",", description = "resize")
    Integer [] sizeOp;
    @CommandLine.Option(names = {"--quality"},  description = "JPEG/PNG compression level")
    double qualityOp;
    @CommandLine.Option(names = {"--crop"},split = ",",  description = " cut out one rectangular area of the image")
    Integer [] cropOp;
    @CommandLine.Option(names = {"--blur"},  description = " reduce image noise detail levels")
    int blurOp;
    @CommandLine.Option(names = {"--format"},  description = "the image format type")
    String formatOp;
}


public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {
    File inputFile;
    File outputFile;
    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    @Override
    public Integer call() throws Exception {
            if(inputFile != null)
            {
                ImageProcessor imageProcessor = new ImageProcessor();
                imageProcessor.processImage(ImageIO.read(inputFile), this);
                return 0;
            }
            else
                throw new IIOException("Can't read input file!");
    }

    public void setInputFile(File file) {
       if(file.exists()) {
           this.inputFile = file;
           if(fileParams == null){
               fileParams = new String[2];
           }
           fileParams[0] = file.getName();
       }
    }

    public void setOutputFile(File file) {
        outputFile = file;
        if(fileParams == null){
            fileParams = new String[2];
        }
        fileParams[1] = file.getName();
    }

    public void setResizeWidth(Integer reducedPreviewWidth) {
        if(sizeOp == null)
            sizeOp = new Integer[2];
            sizeOp[0] = reducedPreviewWidth;
    }

    public void setResizeHeight(Integer reducedPreviewHeight) {
        if(sizeOp == null)
            sizeOp = new Integer[2];
        sizeOp[1] = reducedPreviewHeight;
    }

    public void setQuality(double quality) {
        qualityOp = quality;
    }
    public void setBlurRadius(int blur) {
        blurOp = blur;
    }

    public void setFormat(String format) {
        formatOp = format;
    }

    public void setCrop(Integer width,Integer height,Integer x,Integer y) {
        if(cropOp == null)
            cropOp = new Integer[4];
        cropOp[0] = width;
        cropOp[1] = height;
        cropOp[2] = x;
        cropOp[3] = y;
    }
}

class ImageProcessor extends ResizerApp {
    void processImage(BufferedImage input, ResizerApp app) throws BadAttributesException, IOException {
        int flag = 0;
        if((app.sizeOp != null || app.qualityOp > 0 )&& flag++ >= 0) {
            ResizeAndQuality resize = new  ResizeAndQuality(app.sizeOp,app.qualityOp,  app.inputFile, app.outputFile,  app.fileParams);
        }
        if(app.cropOp != null  && flag++ >= 0) {
            Crop crop = new Crop(app.cropOp, app.inputFile, app.outputFile, app.fileParams[1]);
        }
        if(app.blurOp > 0  && flag++ >= 0) {
            Blur blur = new Blur(app.blurOp, app.inputFile, app.outputFile, app.fileParams[1]);
        }
        if(app.formatOp != null  && flag++ >= 0){
            Format format = new Format(app.formatOp, app.fileParams);
        }
        if(flag == 0)
            throw new BadAttributesException("Please check params!");
    }
}
