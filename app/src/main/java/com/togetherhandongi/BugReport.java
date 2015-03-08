package com.togetherhandongi;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by LEWIS on 2015-01-28.
 */
public class BugReport extends Activity implements View.OnClickListener {
    Carrier carrier;
    private final int REQ_CODE_PICK_GALLERY = 900001;
    private final int REQ_CODE_PICK_CAMERA = 900002;
    private final int REQ_CODE_PICK_CROP = 900003;
    Button bug_back_btn, bug_image_btn, bug_cancel_btn, bug_confirm_btn, dialog_okay, dialog_cancel_okay, dialog_cancel_no;
    EditText bug_content;
    ImageView bug_preview_img;
    ImageButton bug_remove_pic_btn;
    BugUpload task;
    Typeface typeface;
    Dialog dialog_register, dialog_cancel, dialog_back;
    final Context context = this;
    LinearLayout dialog_check_background;
    RelativeLayout popup_report_1, popup_report_2, popup_report_3, popup_report_4;

    int has_pic;
    String content, kakao_nick, date, time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bug_report);
        carrier = (Carrier)getIntent().getSerializableExtra("carrier");
        typeface = Typeface.createFromAsset(getAssets(), "KOPUBDOTUM_PRO_MEDIUM.OTF");

        bug_back_btn    = (Button)findViewById(R.id.bug_back_btn);
        bug_image_btn   = (Button)findViewById(R.id.bug_image_btn);
        bug_cancel_btn  = (Button)findViewById(R.id.bug_cancel_btn);
        bug_confirm_btn = (Button)findViewById(R.id.bug_confirm_btn);
        bug_back_btn.setOnClickListener(this);
        bug_image_btn.setOnClickListener(this);
        bug_cancel_btn.setOnClickListener(this);
        bug_confirm_btn.setOnClickListener(this);

        bug_content = (EditText)findViewById(R.id.bug_content);
        bug_content.setTypeface(typeface);
        bug_preview_img = (ImageView)findViewById(R.id.bug_preview_img);
        bug_remove_pic_btn = (ImageButton)findViewById(R.id.bug_remove_pic_btn);
        bug_remove_pic_btn.setOnClickListener(this);

        popup_report_1 = (RelativeLayout)findViewById(R.id.popup_report_1);
        popup_report_2 = (RelativeLayout)findViewById(R.id.popup_report_2);
        popup_report_3 = (RelativeLayout)findViewById(R.id.popup_report_3);
        popup_report_4 = (RelativeLayout)findViewById(R.id.popup_report_4);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bug_back_btn : {                                                                              //뒤로가기
                if(bug_content.getText().toString().length() > 0) {

                    dialog_back = new Dialog(context);
                    dialog_back.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_back.setContentView(R.layout.dialog_back);
                    dialog_back.show();

                    dialog_cancel_okay = (Button) dialog_back.findViewById(R.id.dialog_back_confirm);
                    dialog_cancel_okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_back.dismiss();
                            finish();
                        }
                    });

                    dialog_cancel_no = (Button) dialog_back.findViewById(R.id.dialog_back_cancel);
                    dialog_cancel_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_back.dismiss();
                        }
                    });
                }
                else {
                    finish();
                }

                break;
            }
            case R.id.bug_image_btn : {                                                                             //이미지버튼
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQ_CODE_PICK_GALLERY);
                break;
            }
            case R.id.bug_cancel_btn : {                                                                            //취소버튼
                if(bug_content.getText().toString().length() > 0) {

                    dialog_cancel = new Dialog(context);
                    dialog_cancel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_cancel.setContentView(R.layout.dialog_cancel);
                    dialog_cancel.show();

                    dialog_cancel_okay = (Button) dialog_cancel.findViewById(R.id.dialog_writing_confirm);
                    dialog_cancel_okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new CountDownTimer(1500, 300) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    // do something after 1s
                                    popup_report_1.setVisibility(VISIBLE);
                                    popup_report_2.setVisibility(VISIBLE);
                                    popup_report_4.setVisibility(VISIBLE);
                                }

                                @Override
                                public void onFinish() {
                                    // do something end times 5s
                                    popup_report_1.setVisibility(GONE);
                                    popup_report_2.setVisibility(GONE);
                                    popup_report_4.setVisibility(GONE);
                                    finish();
                                }
                            }.start();
                        }
                    });

                    dialog_cancel_no = (Button) dialog_cancel.findViewById(R.id.dialog_writing_cancel);
                    dialog_cancel_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_cancel.dismiss();
                        }
                    });
                }
                else {
                    finish();
                }

                break;
            }
            case R.id.bug_remove_pic_btn : {                                                                                                        //사진삭제btn
                has_pic = 0;
                ((ImageView) findViewById(R.id.bug_preview_img)).setImageBitmap(null);
                bug_preview_img.setBackgroundResource(R.drawable.writing_picture);
                bug_remove_pic_btn.setVisibility(GONE);
                break;
            }
            case R.id.bug_confirm_btn : {                                                                           //등록버튼
                if(bug_content.getText().toString().length() < 1) {

                    dialog_register = new Dialog(context);
                    dialog_register.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_register.setContentView(R.layout.dialog_writing_check);
                    dialog_register.show();

                    dialog_check_background = (LinearLayout) dialog_register.findViewById(R.id.dialog_check_background);
                    dialog_check_background.setBackgroundResource(R.drawable.dialog_check_content);
                    dialog_okay = (Button)dialog_register.findViewById(R.id.dialog_okay);
                    dialog_okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_register.dismiss();
                        }
                    });
                }
                else {

                    phpCreate();

                    new CountDownTimer(1500, 300) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // do something after 1s
                            popup_report_1.setVisibility(VISIBLE);
                            popup_report_2.setVisibility(VISIBLE);
                            popup_report_3.setVisibility(VISIBLE);
                        }
                        @Override
                        public void onFinish() {
                            // do something end times 5s
                            popup_report_1.setVisibility(GONE);
                            popup_report_2.setVisibility(GONE);
                            popup_report_3.setVisibility(GONE);
                            finish();
                        }
                    }.start();
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(bug_content.getText().toString().length() > 0) {

            dialog_back = new Dialog(context);
            dialog_back.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog_back.setContentView(R.layout.dialog_back);
            dialog_back.show();

            dialog_cancel_okay = (Button) dialog_back.findViewById(R.id.dialog_back_confirm);
            dialog_cancel_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_back.dismiss();
                    finish();
                }
            });

            dialog_cancel_no = (Button) dialog_back.findViewById(R.id.dialog_back_cancel);
            dialog_cancel_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_back.dismiss();
                }
            });
        }
        else {
            finish();
        }
    }

    private class BugUpload extends AsyncTask<String, Integer, String>{
        public BugUpload(){ super();}
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            String posting_id = null;
            int result = 0;
            try {
                //연결 URL설정
                URL url = new URL(urls[0]);
                //커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //연결되었으면
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for(;;){
                            String line = br.readLine();

                            if(line == null) break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                JSONObject root = new JSONObject(jsonHtml.toString());
                JSONArray ja = root.getJSONArray("results");
                JSONObject jo = ja.getJSONObject(0);
                posting_id = jo.getString("id");
                Log.d("postingID", posting_id);
                result = jo.getInt("result");                                                       //php를 통해서 업로드가 되었는지 확인하기 위해 $result의 값을 받아온다.
                Log.d("result", String.valueOf(result));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(has_pic == 1) {
                DoPhotoUpLoad(getSelectedImageFile().getAbsolutePath(), posting_id);
            }
            return posting_id;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public void phpCreate(){

        content = bug_content.getText().toString();
        kakao_nick = carrier.getNickname();
        date = "150510";                                //유진아 여기 오늘 날짜만 구해줘, 시간도 구해줄 수 있으면 좋지만 일단 날짜가 우선!
        time = "111111";

        try {
            content = URLEncoder.encode(content, "UTF-8");
            kakao_nick = URLEncoder.encode(kakao_nick, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


            String bug_task;
            bug_task = "http://hungry.portfolio1000.com/smarthandongi/bug_upload.php?"
                    + "kakao_id=" + carrier.getId()
                    + "&kakao_nick=" + kakao_nick
                    + "&content=" + content
                    + "&date=" + date
                    + "&time=" + time
                    + "&has_pic=" + has_pic;

            task = new BugUpload();
            task.execute(bug_task);
        }


    private void DoPhotoUpLoad(String fileName, String posting_id){
        HttpPhotoUpload("http://hungry.portfolio1000.com/smarthandongi/bug_photo/upload.php?id=" + posting_id, posting_id, fileName);
//        HttpPhotoUpload("http://hungry.portfolio1000.com/smarthandongi/upload.php?id=" + posting_id, posting_id, fileName);
    }

    private void HttpPhotoUpload(String url, String posting_id, String fileName){
//        Log.d("in","HttpPhotoUpload");
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte [] ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("image", ba1));
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(post);
            //HttpEntity entity = response.getEntity();

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("mCropRequested", mCropRequested);
        savedInstanceState.putInt("mCropAspectWidth", mCropAspectWidth);
        savedInstanceState.putInt("mCropAspectHeight", mCropAspectHeight);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCropRequested = savedInstanceState.getBoolean("mCropRequested");
        mCropAspectWidth = savedInstanceState.getInt("mCropAspectWidth");
        mCropAspectHeight = savedInstanceState.getInt("mCropAspectHeight");
    }

    public File getSelectedImageFile() {
        return getTempImageFile();
    }

    private boolean mCropRequested = false;

    /**
     * crop 이 필요한 경우 설정함. 설정하지 않으면 crop 하지 않음.
     * @param width
     *            crop size width.
     * @param height
     *            crop size height.
     */
    private int mCropAspectWidth = 1, mCropAspectHeight = 1;
    public void setCropOption(int aspectX, int aspectY) {
        mCropRequested = true;
        mCropAspectWidth = aspectX;
        mCropAspectHeight = aspectY;
    }

    /**
     * 사용할 이미지의 최대 크기 설정. 가로, 세로 지정한 크기보다 작은 사이즈로 이미지 크기를 조절함. default size :
     * 500
     *
     * @param sizePixel
     *            기본 500
     */
    private int mImageSizeBoundary = 500;

    public void setImageSizeBoundary(int sizePixel) {
        mImageSizeBoundary = sizePixel;
    }

    private boolean checkWriteExternalPermission() {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private boolean checkSDisAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    private File getTempImageFile() {
        File path = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.hungry_handongi/temp/");
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, "tempimage.png");
        Log.d("path", path.toString());
        return file;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d("in", "onActivityResult");
        if (requestCode == REQ_CODE_PICK_GALLERY && resultCode == Activity.RESULT_OK) {
            // 갤러리의 경우 곧바로 data 에 uri가 넘어옴.
//            Log.d("in", "gallery");
            Uri uri = data.getData();
            Log.d("uri", uri.toString());
            copyUriToFile(uri, getTempImageFile());
//            Log.d("in", "afterURIcopy");
            if (mCropRequested) {
                cropImage();
            } else {
                doFinalProcess();
            }
        } else if (requestCode == REQ_CODE_PICK_CAMERA && resultCode == Activity.RESULT_OK) {
            // 카메라의 경우 file 로 결과물이 돌아옴.
            // 카메라 회전 보정.
            correctCameraOrientation(getTempImageFile());
            if (mCropRequested) {
                cropImage();
            } else {
                doFinalProcess();
            }
        } else if (requestCode == REQ_CODE_PICK_CROP && resultCode == Activity.RESULT_OK) {
            // crop 한 결과는 file로 돌아옴.
            doFinalProcess();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void doFinalProcess() {
        // sample size 를 적용하여 bitmap load.
        Bitmap bitmap = loadImageWithSampleSize(getTempImageFile());

        // image boundary size 에 맞도록 이미지 축소.
        bitmap = resizeImageWithinBoundary(bitmap);

        // 결과 file 을 얻어갈 수 있는 메서드 제공.
        saveBitmapToFile(bitmap);

        // show image on ImageView
        Bitmap bm = BitmapFactory.decodeFile(getTempImageFile().getAbsolutePath());
        bug_preview_img.setBackgroundResource(0);
        ((ImageView) findViewById(R.id.bug_preview_img)).setImageBitmap(bm);
        bug_remove_pic_btn.setVisibility(VISIBLE);
        has_pic = 1;
    }

    private void saveBitmapToFile(Bitmap bitmap) {
        File target = getTempImageFile();
        try {
            FileOutputStream fos = new FileOutputStream(target, false);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
    }

    /** 이미지 사이즈 수정 후, 카메라 rotation 정보가 있으면 회전 보정함. */
    private void correctCameraOrientation(File imgFile) {
        Bitmap bitmap = loadImageWithSampleSize(imgFile);
        try {
            ExifInterface exif = new ExifInterface(imgFile.getAbsolutePath());
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int exifRotateDegree = exifOrientationToDegrees(exifOrientation);
            bitmap = rotateImage(bitmap, exifRotateDegree);
            saveBitmapToFile(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap rotateImage(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
            try {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != converted) {
                    bitmap.recycle();
                    bitmap = converted;
                }
            } catch (OutOfMemoryError ex) {
            }
        }
        return bitmap;
    }

    /**
     * EXIF정보를 회전각도로 변환하는 메서드
     *
     * @param exifOrientation
     *            EXIF 회전각
     * @return 실제 각도
     */
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    /** 원하는 크기의 이미지로 options 설정. */
    private Bitmap loadImageWithSampleSize(File file) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int width = options.outWidth;
        int height = options.outHeight;
        int longSide = Math.max(width, height);
        int sampleSize = 1;
        if (longSide > mImageSizeBoundary) {
            sampleSize = longSide / mImageSizeBoundary;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        options.inPurgeable = true;
        options.inDither = false;

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return bitmap;
    }

    /**
     * mImageSizeBoundary 크기로 이미지 크기 조정. mImageSizeBoundary 보다 작은 경우 resize하지
     * 않음.
     */
    private Bitmap resizeImageWithinBoundary(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        if (width > height) {
            if (width > mImageSizeBoundary) {
                bitmap = resizeBitmapWithWidth(bitmap, mImageSizeBoundary);
            }
        } else {
            if (height > mImageSizeBoundary) {
                bitmap = resizeBitmapWithHeight(bitmap, mImageSizeBoundary);
            }
        }
        return bitmap;
    }

    private Bitmap resizeBitmapWithHeight(Bitmap source, int wantedHeight) {
        if (source == null)
            return null;

        int width = source.getWidth();
        int height = source.getHeight();

        float resizeFactor = wantedHeight * 1f / height;

        int targetWidth, targetHeight;
        targetWidth = (int) (width * resizeFactor);
        targetHeight = (int) (height * resizeFactor);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, true);

        return resizedBitmap;
    }

    private Bitmap resizeBitmapWithWidth(Bitmap source, int wantedWidth) {
        if (source == null)
            return null;

        int width = source.getWidth();
        int height = source.getHeight();

        float resizeFactor = wantedWidth * 1f / width;

        int targetWidth, targetHeight;
        targetWidth = (int) (width * resizeFactor);
        targetHeight = (int) (height * resizeFactor);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, true);

        return resizedBitmap;
    }

    private void copyUriToFile(Uri srcUri, File target) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            // 스트림 생성
            inputStream = (FileInputStream) getContentResolver().openInputStream(srcUri);
            outputStream = new FileOutputStream(target);

            // 채널 생성
            fcin = inputStream.getChannel();
            fcout = outputStream.getChannel();

            // 채널을 통한 스트림 전송
            long size = fcin.size();
            fcin.transferTo(0, size, fcout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fcout.close();
            } catch (IOException ioe) {
            }
            try {
                fcin.close();
            } catch (IOException ioe) {
            }
            try {
                outputStream.close();
            } catch (IOException ioe) {
            }
            try {
                inputStream.close();
            } catch (IOException ioe) {
            }
        }
    }

    private void cropImage() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> cropToolLists = getPackageManager().queryIntentActivities(intent, 0);
        int size = cropToolLists.size();
        if (size == 0) {
            // crop 을 처리할 앱이 없음. 곧바로 처리.
            doFinalProcess();
        } else {
            intent.setData(Uri.fromFile(getTempImageFile()));
            intent.putExtra("aspectX", mCropAspectWidth);
            intent.putExtra("aspectY", mCropAspectHeight);
            intent.putExtra("output", Uri.fromFile(getTempImageFile()));
            Intent i = new Intent(intent);
            ResolveInfo res = cropToolLists.get(0);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, REQ_CODE_PICK_CROP);
        }
    }
}
