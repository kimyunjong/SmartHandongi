package com.smarthandongi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.smarthandongi.database.Picture;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by user on 2015-02-04.
 */
public class PostImageTask extends AsyncTask<Integer, Integer,Integer> {
    private Picture picture;
    private int post_id;
    ImageView post_img;
    int pic_width;
    int temp=1;


    public PostImageTask(Picture picture, int post_id,ImageView post_img, int pic_width, int temp)  {
        this.temp = temp;
        this.picture=picture;
        this.post_id=post_id;
        this.post_img=post_img;
        this.pic_width=pic_width;
    }
    @Override
    protected Integer doInBackground(Integer... params){
        downloadBitmap(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        post_img.setImageBitmap(picture.getPicture());
        post_img.getLayoutParams().height=picture.getPicture().getHeight()*pic_width/picture.getPicture().getWidth();


    }

    public void downloadBitmap(int position){
        InputStream bis;

        try{
            //수영 수정
            if(temp==2)
            bis = new java.net.URL("http://hungry.portfolio1000.com/smarthandongi/group_photo/" + post_id +".jpg").openStream();
            else
            bis = new java.net.URL("http://hungry.portfolio1000.com/smarthandongi/photo/" + post_id +".jpg").openStream();
            //
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            Bitmap image = BitmapFactory.decodeStream(new FlushedInputStream(bis),null,options);
            picture.setPicture(image);
            Log.d("사진저장장장장장자앚앚앚아장자앚앚","됫다다다다다다다다다다");
            bis.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int bite = read();

                    if (bite < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1;   // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }



}
