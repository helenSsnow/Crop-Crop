package mts.teta.resizer;

import mts.teta.resizer.imageprocessor.BadAttributesException;

import java.io.File;
import java.io.IOException;

public class Format {
    private String      _format;
    String[]            _files;
    File                inputFile;
    File                outputFile;

    void                doFormat() throws IOException, BadAttributesException {
        String          tmpStr;
        boolean         success;
        String          changeFile;
        String          extension;
        int             index;

        tmpStr = inputFile.getCanonicalPath();
        index = tmpStr.lastIndexOf(".");
        if(index == tmpStr.length() - 1)
            throw new BadAttributesException("Incorrect filename extension");
        extension = tmpStr.substring(index, tmpStr.length());
        if(_format.equals("JPEG"))
            changeFile = tmpStr.replace(extension, ".jpeg");
        else
            changeFile = tmpStr.replace(extension, ".png");

        File file  = new File(tmpStr);
        System.out.println("tmpStr: " + tmpStr);
        File file2 = new File(changeFile);
        System.out.println("changeFile: " + changeFile);
        System.out.println(file2.getAbsolutePath());
        System.out.println(file.getAbsolutePath());
        success = file.renameTo(new File(changeFile));
        if(!success){
            throw new BadAttributesException("no conversion possible");
        }
    }

    Format(String format, String[] fileParams) throws BadAttributesException, IOException {
        if(fileParams.length != 2 || fileParams[0].isEmpty()  || fileParams[1].isEmpty() || (!format.equals("JPEG") && !format.equals("PNG")))
            throw new BadAttributesException("Please check params!");
        _format = format;
        _files = fileParams;

        this.inputFile = new File(_files[0]);
        this.outputFile = new File(_files[1]);
        doFormat();

    }

}
