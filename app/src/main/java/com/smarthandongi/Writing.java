package com.smarthandongi;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

/**
 * Created by LEWIS on 2015-01-23.
 */
public class Writing extends Activity implements View.OnClickListener {
    Carrier carrier;
    private final int REQ_CODE_PICK_GALLERY = 900001;
    private final int REQ_CODE_PICK_CAMERA = 900002;
    private final int REQ_CODE_PICK_CROP = 900003;
    private int has_pic = 0;

    //Category Buttons
    Button writing_notice_btn, writing_outer_btn, writing_seminar_btn, writing_recruit_btn, writing_agora_btn;
    Button writing_confirm_btn, writing_image_btn;

    EditText writing_title, writing_content, writing_link_text;
    PhpUpload task;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing);
        carrier = (Carrier)getIntent().getSerializableExtra("carrier");

        //Category Buttons
        writing_notice_btn  = (Button) findViewById(R.id.writing_notice_btn);
        writing_outer_btn   = (Button) findViewById(R.id.writing_outer_btn);
        writing_seminar_btn = (Button) findViewById(R.id.writing_seminar_btn);
        writing_recruit_btn = (Button) findViewById(R.id.writing_recruit_btn);
        writing_agora_btn   = (Button) findViewById(R.id.writing_agora_btn);

        writing_notice_btn.setOnClickListener(this);
        writing_outer_btn.setOnClickListener(this);
        writing_seminar_btn.setOnClickListener(this);
        writing_recruit_btn.setOnClickListener(this);
        writing_agora_btn.setOnClickListener(this);


        //Category buttons done

        writing_title = (EditText)findViewById(R.id.writing_title);
        writing_content  = (EditText)findViewById(R.id.writing_content);
        writing_link_text = (EditText)findViewById(R.id.writing_link_text);

        writing_image_btn = (Button)findViewById(R.id.writing_image_btn);
        writing_confirm_btn = (Button)findViewById(R.id.writing_confirm_btn);

        writing_image_btn.setOnClickListener(this);
        writing_confirm_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //Category buttons
            case R.id.writing_notice_btn:{                //카테고리를 0으로 시작해서 처음에는 아무것도 선택되지 않은 상태에서 확인을 누를 시 완료하지 않았다는 경고메시지가 뜸
                //카테고리 번호 지정
                carrier.setCategory(1);
                //배경 초기화
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn_on);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn);

                break;
            }
            case R.id.writing_outer_btn:{
                carrier.setCategory(2);
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn_on);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn);
                break;
            }
            case R.id.writing_seminar_btn:{
                carrier.setCategory(3);
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn_on);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn);
                break;
            }
            case R.id.writing_recruit_btn:{
                carrier.setCategory(4);
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn_on);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn);
                break;
            }
            case R.id.writing_agora_btn:{
                carrier.setCategory(5);
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn_on);
                break;
            }
            //Category buttons done
            case R.id.writing_confirm_btn:{
                phpCreate();
                break;
            }//TODO 카테고리 번호가 0인지 체크하는 코드 필요

            case R.id.writing_image_btn:{

                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQ_CODE_PICK_GALLERY);
            }
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("carrier", carrier);
        setResult(0, intent);
        finish();
    }

    private class PhpUpload extends AsyncTask<String, Integer, String> {

        public PhpUpload(){ super();}
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            String posting_id = null;
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
            //TODO 확인 혹은 취소 하는 팝업 표시
        }
    }

    public void phpCreate(){
        String title, content, upload_url, kakao_nick, link;
        String group_code = "", group_name = "";

        title = writing_title.getText().toString();
        content = writing_content.getText().toString();
        kakao_nick = carrier.getNickname();
        link = writing_link_text.getText().toString();
        try {
            title = URLEncoder.encode(title, "UTF-8");
            content = URLEncoder.encode(content, "UTF-8");
            kakao_nick = URLEncoder.encode(kakao_nick, "UTF-8");
            link = URLEncoder.encode(link, "UTF-8");
            } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        carrier.setTitle(title);
        carrier.setContent(content);
        carrier.setLink(link);

        carrier.setPosting_date("150126");  //TODO 나중에 이 부분에 날짜를 넘겨 받아서 저장한다.
        carrier.setStart_date("20150127");
        carrier.setEnd_date("20150130");

            //TODO 개인일 때와 단체 일 때를 구분하여 다른 케이스를 준다.
 //       if((carrier.getGroup_code() == null) && (carrier.getGroup_name() == null))          //개인인 경우
            upload_url = "http://hungry.portfolio1000.com/smarthandongi/posting_upload.php?"
                    + "kakao_id=" + carrier.getId()
                    + "&kakao_nick=" + kakao_nick
                    + "&category=" + carrier.getCategory()
                    + "&group_code=" + group_code
                    + "&group_name=" + group_name
                    + "&title=" + carrier.getTitle()
                    + "&content=" + carrier.getContent()
                    + "&link=" + carrier.getLink()
                    + "&posting_date=" + carrier.getPosting_date()
                    + "&start_date=" + carrier.getStart_date()
                    + "&end_date=" + carrier.getEnd_date()
                    + "&has_pic=" + has_pic;
            task = new PhpUpload();
            task.execute(upload_url);


    }

    private void DoPhotoUpLoad(String fileName, String posting_id){
        HttpPhotoUpload("http://hungry.portfolio1000.com/smarthandongi/photo/upload.php?id=" + posting_id, posting_id, fileName);
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
        ((ImageView) findViewById(R.id.writing_preview_img)).setImageBitmap(bm);
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
