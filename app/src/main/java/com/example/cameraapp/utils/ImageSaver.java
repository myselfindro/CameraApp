package com.example.cameraapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageSaver {
  private Context context;
  private String directoryName = "images";
  private boolean external;
  private String fileName = "image.png";

  public ImageSaver(Context context2) {
    this.context = context2;
  }

  public ImageSaver setFileName(String fileName2) {
    this.fileName = fileName2;
    return this;
  }

  public ImageSaver setExternal(boolean external2) {
    this.external = external2;
    return this;
  }

  public ImageSaver setDirectoryName(String directoryName2) {
    this.directoryName = directoryName2;
    return this;
  }

  public void save(Bitmap bitmapImage) {
    FileOutputStream fileOutputStream = null;
    try {
      fileOutputStream = new FileOutputStream(createFile());
      bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
      try {
        fileOutputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (Exception e2) {
      e2.printStackTrace();
      if (fileOutputStream != null) {
        try {
          fileOutputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (Throwable th) {
      if (fileOutputStream != null) {
        try {
          fileOutputStream.close();
        } catch (IOException e3) {
          e3.printStackTrace();
        }
      }
      throw th;
    }
  }

  private File createFile() {
    File directory;
    if (this.external) {
      directory = getAlbumStorageDir(this.directoryName);
    } else {
      directory = this.context.getDir(this.directoryName, 0);
    }
    if (!directory.exists() && !directory.mkdirs()) {
      Log.e("ImageSaver", "Error creating directory " + directory);
    }
    return new File(directory, this.fileName);
  }

  private File getAlbumStorageDir(String albumName) {
    return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
  }

  public static boolean isExternalStorageWritable() {
    return "mounted".equals(Environment.getExternalStorageState());
  }

  public static boolean isExternalStorageReadable() {
    String state = Environment.getExternalStorageState();
    return "mounted".equals(state) || "mounted_ro".equals(state);
  }

  public Bitmap load() {
    FileInputStream inputStream = null;
    try {
      FileInputStream inputStream2 = new FileInputStream(createFile());
      Bitmap decodeStream = BitmapFactory.decodeStream(inputStream2);
      try {
        inputStream2.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return decodeStream;
    } catch (Exception e2) {
      e2.printStackTrace();
      if (inputStream == null) {
        return null;
      }
      try {
        inputStream.close();
        return null;
      } catch (IOException e3) {
        e3.printStackTrace();
        return null;
      }
    } catch (Throwable th) {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e4) {
          e4.printStackTrace();
        }
      }
      throw th;
    }
  }
}
