package com.example.plantapp;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//...........................
//import com.google.android.gms.tasks.Task;
//import com.google.android.gms.tasks.Tasks.call;
//..........................


public class tfliteKda {

    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;

    public ByteBuffer convertBitmapToByteBuffer_float(Bitmap bitmap, int
            BATCH_SIZE, int inputSize, int PIXEL_SIZE)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * BATCH_SIZE *
                inputSize * inputSize * PIXEL_SIZE); //float_size = 4 bytes

        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[inputSize * inputSize];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0,
                bitmap.getWidth(), bitmap.getHeight());
        int pixel = 0;
        for (int i = 0; i < inputSize; ++i)
        {
            for (int j = 0; j < inputSize; ++j)
            {
                final int val = intValues[pixel++];

                //14
//                byteBuffer.putFloat( ((val >> 16) & 0xFF)* (1.f/255.f));
//                byteBuffer.putFloat( ((val >> 8) & 0xFF)* (1.f/255.f));
//                byteBuffer.putFloat( (val & 0xFF)* (1.f/255.f));
                //14
//                byteBuffer.putFloat( ((val >> 16) & 0xFF) /255.f);
//                byteBuffer.putFloat( ((val >> 8) & 0xFF) /255.f);
//                byteBuffer.putFloat( (val & 0xFF) /255.f );
                //1
                byteBuffer.putFloat( ((val >> 16) & 0xFF) - (float)123.68);
                byteBuffer.putFloat( ((val >> 8) & 0xFF) - (float)116.779);
                byteBuffer.putFloat( ((val & 0xFF)) - (float)103.939);
                //14
//                byteBuffer.putFloat((((val >> 16) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
//                byteBuffer.putFloat((((val >> 8) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
//                byteBuffer.putFloat(((val & 0xFF) - IMAGE_MEAN) / IMAGE_STD);

//                floatValues[i * 3 + 0] = (((val >> 16) & 0xFF) - (float)123.68);
//                floatValues[i * 3 + 1] = (((val >> 8) & 0xFF) - (float)116.779);
//                floatValues[i * 3 + 2] = (((val & 0xFF)) - (float)103.939);
            }
        }
        return byteBuffer;
    }
}
