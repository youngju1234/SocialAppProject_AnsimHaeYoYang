package com.team14.socialapp.ansimhaeyoyang.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProviderUtil {
  private static final String TAG = "ProviderUtil";

  private static File imageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
  private static String imageFileName = "";
  private static String outputPath = "";

  public static File getOutputFile(Uri uri) {
    return getOutputFile(new File(uri.getPath()).getName());
  }

  public static File getOutputFile(String imageFileName) {
    return new File(imageDirectory, imageFileName);
  }

  public static String getOutputFilePath(Uri uri) {
    return getOutputFile(uri).getAbsolutePath();
  }

  public static String getOutputFilePath(String imageFileName) {
    return getOutputFile(imageFileName).getAbsolutePath();
  }

  public static Uri getOutputMediaFileUri(Context context, File file) {
    return FileProvider.getUriForFile(context, "com.team14.socialapp.ansimhaeyoyang.camera", file);
  }

  public static Uri getOutputMediaFileUri(Context context) {
    try {
      return getOutputMediaFileUri(context, getOutputMediaFile());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static File getOutputMediaFile() throws IOException {
      String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
      imageFileName = "IMG_" + timeStamp;
      File storageDir = new File(imageDirectory, "Camera");
      File image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
        );

    outputPath = "file:" + image.getAbsolutePath();
    return image;
  }

}
