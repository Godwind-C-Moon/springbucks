package priv.patrick.springBucks.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.*;

@Slf4j
public class HttpUtils {

    // 编码格式。发送编码格式统一用UTF-8
    private static final String ENCODING = "UTF-8";

    // 设置连接超时时间，单位毫秒。
    private static final int CONNECT_TIMEOUT = 6000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private static final int SOCKET_TIMEOUT = 6000;

    private static final int DEFAULT_POOL_SIZE = 100;

    private static RequestConfig requestConfig;
    private static CloseableHttpClient httpClient;

    static {
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(DEFAULT_POOL_SIZE);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_POOL_SIZE);
        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();
    }

    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    /**
     * 发送get请求；带请求参数
     *
     * @param url    请求地址
     * @param params 请求参数集合
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {
        return doGet(url, null, params);
    }

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return
     */
    public static String doGet(String url, Map<String, String> headers, Map<String, Object> params) {
        // 创建访问的地址
        URIBuilder uriBuilder = null;
        HttpGet httpGet = null;
        try {
            uriBuilder = new URIBuilder(url);
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    uriBuilder.setParameter(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            // 创建http对象
            httpGet = new HttpGet(uriBuilder.build());
            httpGet.setConfig(requestConfig);
        } catch (URISyntaxException e) {
            log.error("错误的URI格式：{}", url);
        }

        // 设置请求头
        packageHeader(headers, httpGet);

        // 执行请求并获得响应结果
        return getHttpClientResult(httpClient, httpGet);
    }

    /**
     * 发送post请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     */
    public static String doPost(String url) {
        return doPost(url, null, null);
    }

    /**
     * 发送post请求；带请求参数
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     */
    public static String doPost(String url, Map<String, Object> params) {
        return doPost(url, null, params);
    }

    /**
     * 发送post请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return
     */
    public static String doPost(String url, Map<String, String> headers, Map<String, Object> params) {
        // 创建http对象
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        // 设置请求头
		/*httpPost.setHeader("Cookie", "");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");*/
        packageHeader(headers, httpPost);

        // 封装请求参数
        packageParam(params, httpPost);

        // 执行请求并获得响应结果
        return getHttpClientResult(httpClient, httpPost);
    }

    /**
     * 请求带json格式的参数
     *
     * @param url
     * @param data
     * @return
     */
    public static String postJson(String url, String data) {
        return postJson(url, null, data);
    }

    /**
     * 请求带json格式的参数
     *
     * @param url
     * @param headers
     * @param data
     * @return
     */
    public static String postJson(String url, Map<String, String> headers, String data) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", "application/json");
        packageHeader(headers, httpPost);
        httpPost.setEntity(new StringEntity(data, ENCODING));
        return getHttpClientResult(httpClient, httpPost);
    }

    /**
     * 发送put请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     */
    public static String doPut(String url) {
        return doPut(url, null);
    }

    /**
     * 发送put请求；带请求参数
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     */
    public static String doPut(String url, Map<String, Object> params) {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(requestConfig);

        packageParam(params, httpPut);

        return getHttpClientResult(httpClient, httpPut);
    }

    /**
     * 发送delete请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     */
    public static String doDelete(String url) {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(requestConfig);
        return getHttpClientResult(httpClient, httpDelete);
    }

    /**
     * 发送delete请求；带请求参数
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     */
    public static String doDelete(String url, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }

        params.put("_method", "delete");
        return doPost(url, params);
    }

    /**
     * Description: 封装请求头
     *
     * @param params
     * @param httpMethod
     */
    public static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Description: 封装请求参数
     *
     * @param params
     * @param httpMethod
     */
    public static void packageParam(Map<String, Object> params, HttpEntityEnclosingRequestBase httpMethod) {
        // 封装请求参数
        if (params != null) {
            List<NameValuePair> nvps = new ArrayList<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }

            // 设置到请求的http对象中
            try {
                httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
            } catch (UnsupportedEncodingException e) {
                log.error("异常的编码：{}", e.toString());
            }
        }
    }

    /**
     * Description: 获得响应结果
     *
     * @param httpClient
     * @param httpMethod
     * @return
     */
    public static String getHttpClientResult(CloseableHttpClient httpClient, HttpRequestBase httpMethod) {
        // 执行请求
        try (CloseableHttpResponse httpResponse = httpClient.execute(httpMethod)) {
            // 获取返回结果
            if (httpResponse != null && httpResponse.getStatusLine() != null) {
                if (httpResponse.getEntity() != null) {
                    return EntityUtils.toString(httpResponse.getEntity(), ENCODING);
                }
            }
        } catch (Exception e) {
//            log.error("发送请求到：{}失败，异常：{}", httpMethod.getURI(), e.toString());
        }
        return null;
    }

}

