package com.ipaylinks.poss.integration.oss;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 阿里云OSS工具类
 *
 * @author mmzhang
 */
@Component
public class UFSClient {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 获取token服务器地址
     */
    @Value("${ufs.tokenServer:https://fstoken.ipaylinks.com}")
    private String tokenServer;
    /**
     * 每个key都会对应一个根目录
     */
    @Value("${ufs.accessKey:debug_mpsposs_Jr93Mf66r}")
    private String accessKey;
    /**
     * 自定义子目录
     */
//    @Value("${ufs.subPath.channel}")
//    private String subPath;


    public static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

    private static final String TOKEN_BASE_PATH = "basePath";
    private static final String TOKEN_FULL_BASE_PATH = "fullBasePath";
    private static final String TOKEN_BUCKET = "bucket";
    private static final String TOKEN_URL = "url";
    private static final String TOKEN_STS_ENDPOINT = "endpoint";

    private static final String TOKEN_STS_TOKEN = "stsToken";
    private static final String TOKEN_STS_TOKEN_CREDENTIALS = "Credentials";
    private static final String TOKEN_STS_TOKEN_CREDENTIALS_ACCESSKEYID = "AccessKeyId";
    private static final String TOKEN_STS_TOKEN_CREDENTIALS_ACCESSKEYSECRET = "AccessKeySecret";
    private static final String TOKEN_STS_TOKEN_CREDENTIALS_SECURITYTOKEN = "SecurityToken";

    /**
     * 上传文件，并返回可以直接被外部用户访问的url
     *
     * @param key
     * @param file
     * @return
     */
    public String putFileAndSign(String key, File file) {
        String signedUrl = null;
        try {
            JSONObject rawToken = initRawToken();
            OSS client = getOSSClient(rawToken);
            String inputUrl = getBasePath(rawToken) + key;
            client.putObject(getBucket(rawToken), inputUrl, file);
            String uploadPath = getFullBasePath(rawToken) + key;
            //signedUrl就是可以直接被外部用户访问的url
            signedUrl = signURL(uploadPath);
        } catch (Exception e) {
            logger.error("putFileAndSign exception", e);
            return null;
        }
        return signedUrl;
    }

    /**
     * 上传文件
     *
     * @param key
     * @param file
     * @return
     */
    public boolean putFile(String key, File file) {
        try {
            JSONObject rawToken = initRawToken();
            OSS client = getOSSClient(rawToken);
            String inputUrl = getBasePath(rawToken) + key;
            client.putObject(getBucket(rawToken), inputUrl, file);
            return true;
        } catch (Exception e) {
            logger.error("putFile exception", e);
            return false;
        }
    }

    /**
     * 上传文件，并返回可以直接被外部用户访问的url
     *
     * @param key
     * @param input
     * @return
     */
    public String putFileAndSign(String key, InputStream input) {
        String signedUrl = null;
        try {
            JSONObject rawToken = initRawToken();
            OSS client = getOSSClient(rawToken);
            String inputUrl = getBasePath(rawToken) + key;
            client.putObject(getBucket(rawToken), inputUrl, input);
            String uploadPath = getFullBasePath(rawToken) + key;
            //signedUrl就是可以直接被外部用户访问的url
            signedUrl = signURL(uploadPath);
        } catch (Exception e) {
            logger.error("putFileAndSign exception", e);
        }
        return signedUrl;
    }

    /**
     * 上传文件
     *
     * @param key
     * @param input
     * @return
     */
    public boolean putFile(String key, InputStream input) {
        try {
            JSONObject rawToken = initRawToken();
            OSS client = getOSSClient(rawToken);
            String inputUrl = getBasePath(rawToken) + key;
            client.putObject(getBucket(rawToken), inputUrl, input);
            return true;
        } catch (Exception e) {
            logger.error("putFile exception", e);
            return false;
        }
    }

    /**
     * 删除文件,一般不开通删除权限
     *
     * @param key
     */
    @Deprecated
    public boolean deleteFile(String key) {
        try {
            JSONObject rawToken = initRawToken();
            OSS client = getOSSClient(rawToken);
            String inputUrl = getBasePath(rawToken) + key;
            client.deleteObject(getBucket(rawToken), inputUrl);
            return true;
        } catch (Exception e) {
            logger.error("deleteFile exception", e);
            return false;
        }
    }

    /**
     * 可以直接被外部用户访问的url
     *
     * @param key
     * @return
     */
    public String getFileSign(String key) {
        String signedUrl = null;
        try {
            JSONObject rawToken = initRawToken();
            String originalURL = getFullBasePath(rawToken) + key;
            //signedUrl就是可以直接被外部用户访问的url
            signedUrl = signURL(originalURL);
        } catch (Exception e) {
            logger.error("getFileSign exception", e);
        }
        return signedUrl;
    }

    public BufferedReader getReaderFromOss(String ossKey) throws Exception {
        JSONObject rawToken = initRawToken();
        OSS client = getOSSClient(rawToken);
        String inputUrl = getBasePath(rawToken) + ossKey;
        OSSObject obj = client.getObject(getBucket(rawToken), inputUrl);
        return new BufferedReader(new InputStreamReader(obj.getObjectContent(), "GBK"));
    }

    public InputStream getReaderFromOssForStream(String ossKey) throws Exception {
        JSONObject rawToken = initRawToken();
        OSS client = getOSSClient(rawToken);
        String inputUrl = getBasePath(rawToken) + ossKey;
        OSSObject obj = client.getObject(getBucket(rawToken), inputUrl);
        InputStream inputStream = obj.getObjectContent();
        return inputStream;
    }

    /**
     * 获取文件
     *
     * @param key
     * @param output
     * @return
     */
    public boolean getFile(String key, OutputStream output) {
        try {
            JSONObject rawToken = initRawToken();
            OSS client = getOSSClient(rawToken);
            String inputUrl = getBasePath(rawToken) + key;
            OSSObject obj = client.getObject(getBucket(rawToken), inputUrl);
            write(obj.getObjectContent(), output);
            return true;
        } catch (Exception e) {
            logger.error("putFileAndSign exception", e);
            return false;
        }
    }

    /**
     * 获取文件
     *
     * @param key
     * @param file
     * @return
     */
    public boolean getFile(String key, File file) {
        try {
            JSONObject rawToken = initRawToken();
            OSS client = getOSSClient(rawToken);
            String inputUrl = getBasePath(rawToken) + key;
            OSSObject obj = client.getObject(getBucket(rawToken), inputUrl);
            write(obj.getObjectContent(), file);
            return true;
        } catch (Exception e) {
            logger.error("putFileAndSign exception", e);
            return false;
        }
    }

    /*
     * 获取OSSClient, 需要先初始化stsToken
     */
    private OSS getOSSClient(JSONObject rawToken) throws Exception {
        if (rawToken == null) {
            throw new Exception("null token!");
        }
        JSONObject stsToken = JSONObject.parseObject(rawToken.getString(TOKEN_STS_TOKEN));
        JSONObject credentials = JSONObject.parseObject(stsToken.getString(TOKEN_STS_TOKEN_CREDENTIALS));
        String accessKeyId = credentials.getString(TOKEN_STS_TOKEN_CREDENTIALS_ACCESSKEYID);
        String accessKeySecret = credentials.getString(TOKEN_STS_TOKEN_CREDENTIALS_ACCESSKEYSECRET);
        String securityToken = credentials.getString(TOKEN_STS_TOKEN_CREDENTIALS_SECURITYTOKEN);
        String endpoint = rawToken.getString(TOKEN_STS_ENDPOINT);
        return new OSSClient(endpoint, accessKeyId, accessKeySecret, securityToken);
    }

    private String signURL(String originalURL) throws UnsupportedOperationException, IOException, Exception {
        List<NameValuePair> entity = new ArrayList<NameValuePair>();
        entity.add(new BasicNameValuePair("cmd", "signURL"));
        entity.add(new BasicNameValuePair("url", originalURL));
        JSONObject token = postToServer(entity);
        return token.getString(TOKEN_URL);
    }

    private static void safeClose(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }

    private static void safeClose(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 文件流读写
     *
     * @param in
     * @param out
     * @throws Exception
     */
    private void write(InputStream in, OutputStream out) throws Exception {
        BufferedOutputStream outBuffer = null;
        try {
            outBuffer = new BufferedOutputStream(out);

            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int bytesRead = 0;
            while ((bytesRead = in.read(buffer)) != -1) {
                outBuffer.write(buffer, 0, bytesRead);
            }
        } finally {
            safeClose(in);
            safeClose(outBuffer);
        }
    }

    /**
     * 文件流读写
     *
     * @param in
     * @param file
     * @throws Exception
     */
    private void write(InputStream in, File file) throws Exception {
        write(in, new FileOutputStream(file));
    }

    /*
     *
     * 初始化stsToken
     */
    private JSONObject initRawToken() throws ClientProtocolException, IOException, Exception {
        List<NameValuePair> entity = new ArrayList<NameValuePair>();
        entity.add(new BasicNameValuePair("cmd", "getToken"));
//        entity.add(new BasicNameValuePair("subPath", this.subPath));
        return postToServer(entity);
    }

    private JSONObject postToServer(List<NameValuePair> entity) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(this.tokenServer);
        try {
            entity.add(new BasicNameValuePair("accessKey", this.accessKey));
            request.setEntity(new UrlEncodedFormEntity(entity, "UTF-8"));

            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            JSONObject jsonObject = JSONObject.parseObject(result.toString());
            if (!"0".equals(jsonObject.getString("errorCode"))) {
                logger.error("postToServer error, errorCode=" + jsonObject.getString("errorCode") + ", msg=" + jsonObject.getString("errorMsg"));
                throw new Exception("postToServer error, errorCode=" + jsonObject.getString("errorCode"));
            }
            return JSONObject.parseObject(jsonObject.getString("data"));
        } catch (UnsupportedEncodingException e) {
            logger.error("post to server error", e);
            throw new Exception(e.getMessage());
        } catch (ClientProtocolException e) {
            logger.error("post to server error", e);
            throw new Exception(e.getMessage());
        } catch (IOException e) {
            logger.error("post to server error", e);
            throw new Exception(e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.error("post to server error", e);
            }
        }
    }

    private String getBasePath(JSONObject rawToken) {
        return rawToken.getString(TOKEN_BASE_PATH);
    }

    private String getFullBasePath(JSONObject rawToken) {
        return rawToken.getString(TOKEN_FULL_BASE_PATH);
    }

    private String getBucket(JSONObject rawToken) {
        return rawToken.getString(TOKEN_BUCKET);
    }
}
