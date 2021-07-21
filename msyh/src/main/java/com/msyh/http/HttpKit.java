package com.msyh.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


/**
 * @author 13778
 */
public class HttpKit {
	private static PoolingHttpClientConnectionManager cm;
	private static String EMPTY_STR = "";
	/**
	 * // 整个连接池最大连接数
	 * // 每路由最大连接数，默认值是2
	 */
	private static void init() {
		if (cm == null) {
			cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(50);
			cm.setDefaultMaxPerRoute(5);
		}
	}

	/**
	 * 通过连接池获取HttpClient
	 */
	private static CloseableHttpClient getHttpClient() {
		init();
		return HttpClients.custom().setConnectionManager(cm).build();
	}

	public static String get(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		return getResult(httpGet);
	}

	public static String get(String url, Map<String, Object> params)
			throws Exception {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		return getResult(httpGet);
	}

	public static String getHeader(String url, Map<String, Object> headers)
			throws Exception {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		HttpGet httpGet = new HttpGet(ub.build());
		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}
		return getResult(httpGet);
	}

	public static String get(String url, Map<String, Object> headers,
			Map<String, Object> params) throws Exception {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}
		return getResult(httpGet);
	}

	public static String post(String url) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		return getResult(httpPost);
	}

	public static String post(String url, Map<String, Object> params,String json) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		StringEntity entity = new StringEntity(json, "UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
		return getResult(httpPost);
	}

	public static String post(String url, String json) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(json, "UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		return getResult(httpPost);

	}
	public static String post(String url, JSONArray jsonArray) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonArray.toString(), "UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		return getResult(httpPost);

	}
	public static String post(String url,String token, String json) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Authorization", token);
		StringEntity entity = new StringEntity(json, "UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		return getResult(httpPost);

	}

	private static ArrayList<NameValuePair> covertParams2NVPS(
			Map<String, Object> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			pairs.add(new BasicNameValuePair(param.getKey(), String
					.valueOf(param.getValue())));
		}

		return pairs;
	}

	/**
	 * 处理Http请求
	 * 
	 * @param request
	 * @return
	 */
	private static String getResult(HttpRequestBase request) throws Exception {
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String result = EntityUtils.toString(entity);
			response.close();
			return result;
		}
		return EMPTY_STR;
	}

	/* 向指定URL发送GET方法的请求 */
	public static String sendGetToken(String pathUrl) {
		StringBuffer result = new StringBuffer("");
		try {
			URL url = new URL(pathUrl);
			// 打开和url之间的连接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestProperty("Content-Type",
					"application/json;charset=UTF-8");
			connection.setRequestProperty("Charset", "utf-8");
			connection.setRequestMethod("GET");
			connection.connect();
			// 读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			if (reader != null) {
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}



	/** 分隔符 */
	public static final String FILE_BOUNDARY = "-----";

	/**
	 * @param file
	 * @return
	 */
	private static byte[] getStartData(File file) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("--");
		sb.append(FILE_BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data; \r\n name=\"1\"; filename=\""
				+ file.getName() + "\"\r\n");
		sb.append("Content-Type: msoffice\r\n\r\n");
		return sb.toString().getBytes("UTF-8");
	}



	public static String sendPostJson(String pathUrl, String jsonStr,
			String token) {
		StringBuffer sb = new StringBuffer("");
		HttpURLConnection connection = null;
		try {
			URL url = new URL(pathUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(100 * 1000);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("Content-Type",
					"application/json;charset=UTF-8");
			connection.connect();
			// POST请求
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			byte[] b = jsonStr.getBytes("UTF-8");
			out.write(b);
			out.flush();
			out.close();
			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));

			String lines;
			while ((lines = reader.readLine()) != null) {
				sb.append(lines);
			}
			reader.close();
			connection.disconnect();
		} catch (Exception e) {
			try {
				String code = String.valueOf(connection.getResponseCode());
				InputStream result = connection.getErrorStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(result, "UTF-8"));
				String linesd;
				sb.append(code + ":");
				while ((linesd = reader.readLine()) != null) {
					sb.append(linesd);
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return sb.toString();

	}


	/**
	 *
	 * @param obj json对象
	 * @param url 地址
	 * @param token 头部token
	 * @return
	 * @throws Exception
	 */
	public static String sendPost(JSONObject obj, String url, String token) throws Exception{
		String parm = obj.toString();
		String result = "";
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL realUrl;
			realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestMethod("POST");
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			if(token!=null && token!=""){
				conn.addRequestProperty("token",token);
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(parm);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

}
