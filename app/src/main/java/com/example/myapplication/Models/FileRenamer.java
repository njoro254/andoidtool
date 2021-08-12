package com.example.myapplication.Models;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class FileRenamer extends File {
    private  String uploadfileName;
    public FileRenamer(@NonNull URI uri) {
        super(uri);
    }
    public  void setUploadFileName( String filename)
    {
        uploadfileName=filename;
    }

    public String getUploadFileNme()
    {
        return this.uploadfileName;
    }
    public static void rename(File from, File to) throws IOException, InterruptedException {
        if (from.exists()) {
            // Files.move(Paths.get(from.toString()), Paths.get(to.toString()));

            for (int i = 0; i < 200; i++) {
                if (from.renameTo(to)) {
                    return;
                }

                Thread.sleep(10);
            }
        }

        throw new IOException(
                "Could not rename '" + from.getAbsolutePath() + "' to '" + to.getAbsolutePath() + "'");
    }
}
