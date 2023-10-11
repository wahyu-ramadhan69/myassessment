package com.recruitment.myassessment.handler;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestCapture {

    public static String allRequest(WebRequest request, Object object) {
        String headerName = "";
        String paramName = "";
        Map<String, Object> m = new HashMap<>();
        Iterator<String> headerNames = request.getHeaderNames();
        while (headerNames.hasNext()) {
            headerName = headerNames.next();
            m.put(headerName, request.getHeader(headerName));
        }

        Iterator<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasNext()) {
            paramName = parameterNames.next();
            m.put(paramName, request.getParameter(paramName));
        }
        m.put("contextPath", request.getContextPath());
        m.put("sessionID", request.getSessionId());
        m.put("remoteUser", request.getRemoteUser());
        m.put("locale", request.getLocale());
        m.put("principal", request.getUserPrincipal());
        m.put("isSecure", request.isSecure());
        if (object != null) {
            m.put("body", new Gson().toJson(object));
        }

        String strValue = new JSONObject(m).toString();
        // System.out.println(strValue);
        return strValue;
    }

    public static String allRequest(HttpServletRequest requestx) {
        ContentCachingRequestWrapper request = new ContentCachingRequestWrapper(requestx);
        String headerName = "";
        String paramName = "";
        String test = "";
        Map<String, Object> requestz = new HashMap<>();
        Map<String, Object> reqParam = new HashMap<>();
        Map<String, Object> reqHeader = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            headerName = headerNames.nextElement();
            reqHeader.put(headerName, request.getHeader(headerName));
        }

        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            paramName = params.nextElement();
            reqParam.put(paramName, request.getParameter(paramName));
        }
        requestz.put("authType", request.getAuthType());
        requestz.put("method", request.getMethod());
        requestz.put("serverName", request.getServerName());
        requestz.put("serverPort", request.getServerPort());
        requestz.put("session", request.getSession());
        requestz.put("queryString", request.getQueryString());
        requestz.put("remoteAddr", request.getRemoteAddr());
        requestz.put("requestSessionId", request.getRequestedSessionId());
        requestz.put("serverPort", request.getServerPort());
        requestz.put("pathInfo", request.getPathInfo());
        requestz.put("remoteHost", request.getRemoteHost());
        requestz.put("locale", request.getLocale());
        requestz.put("principal", request.getUserPrincipal());
        requestz.put("isSecure", request.isSecure());
        requestz.put("reqHeader", reqHeader);
        requestz.put("reqParam", reqParam);

        try {
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                requestz.put("reqBody", readInputString(request));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String strValue = new JSONObject(requestz).toString();
        // System.out.println(strValue);
        return strValue;
    }

    private static String readInputString(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        String requestBody;

        try {
            reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            requestBody = sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return requestBody;
    }
    // private static String readInputStreamInStringFormat(InputStream stream,
    // Charset charset) throws IOException {
    // final int MAX_BODY_SIZE = 1024;
    // final StringBuilder bodyStringBuilder = new StringBuilder();
    // if (!stream.markSupported()) {
    // stream = new BufferedInputStream(stream);
    // }
    //
    // stream.mark(MAX_BODY_SIZE + 1);
    // final byte[] entity = new byte[MAX_BODY_SIZE + 1];
    // final int bytesRead = stream.read(entity);
    //
    // if (bytesRead != -1) {
    // bodyStringBuilder.append(new String(entity, 0, Math.min(bytesRead,
    // MAX_BODY_SIZE), charset));
    // if (bytesRead > MAX_BODY_SIZE) {
    // bodyStringBuilder.append("...");
    // }
    // }
    // stream.reset();
    //
    // return bodyStringBuilder.toString();
    // }
}