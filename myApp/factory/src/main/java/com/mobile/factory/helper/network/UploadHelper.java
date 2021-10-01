package com.mobile.factory.helper.network;

import android.content.Context;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import com.mobile.util.utils.Encryptor;

import org.jasypt.util.text.BasicTextEncryptor;

import java.util.Date;

/**
 * 上传文件到oss，部署在香港了
 * 加密方法：ENCRYPTOR.getEncryptor().encrypt(str);
 * 解密方法: ENCRYPTOR.getEncryptor().decrypt(str);
 */
public class UploadHelper {
    private static final String TAG = "UploadHelper";
    private static final String ENDPOINT = "http://oss-cn-hongkong.aliyuncs.com";
    private static final String BUCKET_NAME = "mobile-oss1";
    public static final Encryptor ENCRYPTOR = new Encryptor("123456");


    /**
     * !! 不要改密钥
     *
     * @param context
     * @return
     */
    private static OSS getClient(Context context) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(
                "LTAI5tDYzdfSKyDrZP5brm6y",
                "M6CSJJ7WLN6vvc3pHC2CKpbmJYdhFi");
        return new OSSClient(context, ENDPOINT, credentialProvider);
    }

    /**
     * 上传后返回一个存储路径
     *
     * @param objKey 上传上去后，在服务器上的独立的KEY
     * @param path   需要上传的文件的路径
     * @return 存储的地址
     */
    private static String upload(String objKey, String path, Context context) {
        // 构造一个上传请求
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME,
                objKey, path);

        try {
            OSS client = getClient(context);
            PutObjectResult result = client.putObject(request);
            String url = client.presignPublicObjectURL(BUCKET_NAME, objKey);
            // 格式打印输出
            Log.d(TAG, String.format("ObjectURL:%s", url));
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            // 如果有异常则返回空
            return null;
        }
    }


    /**
     * 上传音频
     *
     * @param path
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String uploadImage(String path, Context context) throws Exception {
        String key = getImgObjKey(path);
        return upload(key, path, context);
    }

    /**
     * 上传头像
     *
     * @param path
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String uploadPortrait(String path, Context context) throws Exception {
        String key = getPortraitObjKey(path);
        return upload(key, path, context);
    }

    /**
     * 上传音频
     *
     * @param path
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String uploadAudio(String path, Context context) throws Exception {
        String key = getAudioObjKey(path);
        return upload(key, path, context);
    }

    private static String getImgObjKey(String path) throws Exception {
        String[] key = getKey(path);
        return String.format("image/%s/%s.png", key[0], key[1]);
    }

    private static String getPortraitObjKey(String path) throws Exception {
        String[] key = getKey(path);
        return String.format("portrait/%s/%s.png", key[0], key[1]);
    }

    private static String getAudioObjKey(String path) throws Exception {
        String[] key = getKey(path);
        return String.format("audio/%s/%s.mp3", key[0], key[1]);
    }

    public static String[] getKey(String path) {
        BasicTextEncryptor textEncryptor = ENCRYPTOR.getEncryptor();
        String fileMd5 = textEncryptor.encrypt(path);
        return new String[]{DateFormat.format("yyyyMM", new Date()).toString(), fileMd5};
    }


}
