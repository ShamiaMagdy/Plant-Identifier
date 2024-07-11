package com.example.plantapp;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    Interpreter tflite;
    tfliteKda myclassifer;
    List<String> labelList;
    Bundle bundle;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        myclassifer = new tfliteKda();
        final TextView output1 = (TextView) findViewById(R.id.textView5);
        final TextView output2 = (TextView) findViewById(R.id.textView6);
        final TextView output3 = (TextView) findViewById(R.id.textView7);
        final TextView label1 = (TextView) findViewById(R.id.textView8);
        final TextView label2 = (TextView) findViewById(R.id.textView9);
        final TextView label3 = (TextView) findViewById(R.id.textView10);
        ImageView imageView = (ImageView) findViewById(R.id.photo_imageview2);
        Button makePre = (Button) findViewById(R.id.result);
        bundle=getIntent().getExtras();
        if(bundle!=null)
        {

           /* int resid=bundle.getInt("passphoto");
            passphoto.setImageResource(resid);*/
            imageBitmap= getIntent().getParcelableExtra("passphoto") ;
            imageView.setImageBitmap(imageBitmap);
        }
        //imageView.setImageBitmap(imageBitmap);

        try {
            tflite = new Interpreter(loadModelFile());
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        //loading the label
        try {
            labelList = loadLabelList();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //i used it to make sure of the input shape of this model
        int[] shape = tflite.getInputTensor(0).shape();
        String g = ((Integer.toString(shape[0])));
        Toast.makeText(this, g, Toast.LENGTH_SHORT).show();

        // pic.setImageResource(R.mipmap.inverted_color);
        final Bitmap bitmap;
        //bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.inverted_color);
        //bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.scanned);
        //bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.camera);
        bitmap= imageBitmap;
        //bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.from_net);
        //bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.black);
        //bitmap = applyFleaEffect(imageBitmap);
        imageView.setImageBitmap(bitmap);

        if (bitmap == null) {
            Log.e("ERR", "Failed to decode resource - ");
            Toast.makeText(this, "Null Value", Toast.LENGTH_LONG).show();
        }

        //used to resize image
        final Bitmap resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true);

        //also used to resize and preprocess on image and it is not working
        ImageProcessor imageProcessor =
                new ImageProcessor.Builder()
                        .add(new ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
                        .build();
        TensorImage tImage = new TensorImage(DataType.UINT8);

        // Analysis code for every frame
        // Preprocess the image
        tImage.load(resized);
        tImage = imageProcessor.process(tImage);

        //............................................
        final TensorImage finalTImage = tImage;

        makePre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TensorBuffer probabilityBuffer =
                        TensorBuffer.createFixedSize(new int[]{1, 21}, DataType.UINT8);

                float[][] outputtf = new float[1][21];
                ByteBuffer img = myclassifer.convertBitmapToByteBuffer_float(resized, 1, 224, 3);

                //tflite.run(finalTImage.getBuffer(),probabilityBuffer.getBuffer());
                tflite.run(img, outputtf);

                float max1, max2, max3;
                max1 = 0;
                max2 = 0;
                max3 = 0;
                //int l= 0;
                int o1, o2, o3;
                o1 = 0;
                o2 = 0;
                o3 = 0;
//               0 for(int i=0; i<20;i++)
//                {
//                    if(outputtf[0][i] > max)
//                    {
//                        max=outputtf[0][i];
//                        l=i;
//                    }
//                }
                for (int i = 0; i < 20; i++) {
                    /* If current element is greater than first*/
                    if (outputtf[0][i] > max1) {
                        max3 = max2;
                        max2 = max1;
                        max1 = outputtf[0][i];
                        o1 = i;
                    }

                    /* If arr[i] is in between first and second then update second  */
                    else if (outputtf[0][i] > max2) {
                        max3 = max2;
                        max2 = outputtf[0][i];
                        o2 = i;
                    } else if (outputtf[0][i] > max3) {
                        max3 = outputtf[0][i];
                        o3 = i;
                    }
                }

                //float returnOut= outputtf[0][17];
//                output.setText(Float.toString(max));
//                inn.setText(Integer.toString(l));
                String label11 = labelList.get(o1);
                String label22 = labelList.get(o2);
                String label33 = labelList.get(o3);
                float numOne = max1;
                float numTwo = max2;
                float numThree = max3;
                output1.setText(Float.toString(numOne));
                output2.setText(Float.toString(numTwo));
                output3.setText(Float.toString(numThree));

                label1.setText(Integer.toString(o1));
                label2.setText(Integer.toString(o2));
                label3.setText(Integer.toString(o3));

                label1.setText(label11);
                label2.setText(label22);
                label3.setText(label33);
                Toast.makeText(Main2Activity.this, "Done", Toast.LENGTH_SHORT).show();

                /*Intent goTo= new Intent(Main2Activity.this,Api.class);
                String msg= label1.getText().toString();
                goTo.putExtra("salma",msg);
                startActivity(goTo);*/

            }
        });


    }

    private MappedByteBuffer loadModelFile() throws IOException {
        // Memory-map the model file
        AssetFileDescriptor fileDescriptor = getAssets().openFd("quantized_model_high.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    //get label file
    private List<String> loadLabelList() throws IOException {
        List<String> labelList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("Labels.txt")));
        String line;
        while ((line = reader.readLine()) != null) {
            labelList.add(line);
        }
        reader.close();
        return labelList;
    }

    public static final int COLOR_MIN = 0x00;
    public static final int COLOR_MAX = 0xFF;

    public static Bitmap applyFleaEffect(Bitmap bmap) {
        int width = bmap.getWidth();
        int height = bmap.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = bmap.getPixel(x, y);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);
                if (R < 162 && G < 162 && B < 162)
                    bmap.setPixel(x, y, Color.BLACK);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = bmap.getPixel(x, y);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);
                if (R > 162 && G > 162 && B > 162)
                    bmap.setPixel(x, y, Color.WHITE);
            }
        }
        return bmap;

    }
}
