package com.example.andras.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Andras_Nemeth on 2015.10.26..
 */
public class CameraActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 666;
    private static final String TAG = CameraActivity.class.getSimpleName();
    private static final String STATE_FILE_NAME_URI = "filenameUri";
    private static final float TARGET_IMAGE_WIDTH = 1920f;
    //currently this is turned off
    private static final float TARGET_IMAGE_HEIGHT = Float.MAX_VALUE;

    private ImageView imageView;
    private Uri fileNameUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        imageView = findViewById(R.id.imageView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCameraButtonClick();
            }
        });
        if (savedInstanceState != null) {
            fileNameUri = savedInstanceState.getParcelable(STATE_FILE_NAME_URI);
            processImage();
        }
    }

    private void onCameraButtonClick() {
        File imageFile = createImageFile();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (imageFile != null && takePictureIntent.resolveActivity(getPackageManager()) != null) {
            fileNameUri = Uri.fromFile(imageFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileNameUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            processImage();

        }
    }

    /**
     * Bitmap is really a bitmap so be careful because yue can run into OutOfMemoryException on devices with high res camera and little memory
     */
    private void processImage() {
        //setImageFromResult(data);
        checkExifData();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(fileNameUri.getPath(), bmOptions);
        bitmap = resizeImage(bitmap, bmOptions);
        bitmap = rotateImageByExif(bitmap);
        saveImage(bitmap, fileNameUri.getPath());

        imageView.setImageBitmap(bitmap);
        checkExifData();
    }

    private void checkExifData()  {
        try {
            ExifInterface exif = new ExifInterface(fileNameUri.getPath());
            Log.d(TAG, "xxx orientation " + exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED) +
                    " size: " + exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, -1) +
                    "*" + exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, -1));
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private Bitmap resizeImage(Bitmap source, BitmapFactory.Options bitmapOptions) {
        int imageWidth = bitmapOptions.outWidth;
        int imageHeight = bitmapOptions.outHeight;
        float scaleFactor = Math.min(TARGET_IMAGE_WIDTH/imageWidth, TARGET_IMAGE_HEIGHT/imageHeight);
        if (scaleFactor >= 1) {
            return source;
        }
        return Bitmap.createScaledBitmap(source, (int)(imageWidth * scaleFactor), (int)(imageHeight * scaleFactor), true);
    }

    private Bitmap rotateImageByExif(Bitmap source) {
        try {
            ExifInterface exif = new ExifInterface(fileNameUri.getPath());
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            int rotationAngle = 0;
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90 : rotationAngle = 90; break;
                case ExifInterface.ORIENTATION_ROTATE_180 : rotationAngle = 180; break;
                case ExifInterface.ORIENTATION_ROTATE_270 : rotationAngle = 270; break;
            }
            if (rotationAngle != 0) {
                Matrix matrix = new Matrix();
                matrix.preRotate(rotationAngle);
                //Note: This width/heigth parameters don't scale but crop
                return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
            } else {
                return source;
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    //from google
    private void resizeForImageView() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileNameUri.getPath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(fileNameUri.getPath(), bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    /**
     * This will work when you don't add EXTRA_OUTPUT and the image will be filled directly into the result Intent.
     * It is a really basic method it doesn't really good for anything
     * @param data
     */
    private void setImageFromResult(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        imageView.setImageBitmap(imageBitmap);
    }

    private File createImageFile() {
        try {
            File storageDir = Environment.getExternalStorageDirectory();
            File imageFile = File.createTempFile("temp666",".jpg",storageDir);

            // Save a file: path for use with ACTION_VIEW intents
            // mCurrentPhotoPath = "file:" + imageFile.getAbsolutePath();
            return imageFile;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_FILE_NAME_URI, fileNameUri);
    }

    private void saveImage(Bitmap bmp, String filename) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            // in theory PNG is a lossless format, the compression factor (100) is ignored
            // but it seems it doesn't
            //bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            bmp.compress(Bitmap.CompressFormat.JPEG, 70, out);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

}
