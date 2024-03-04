package com.alita.example.cloud_point.controller;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

public class SignUtil_setthreshold {

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    public static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    public static String getSignStr(Map<String, Object> bodyParams, Map<String, String[]> params, String secretKey, String timestamp){
        StringBuilder sb = new StringBuilder();
        if (bodyParams != null && !bodyParams.isEmpty()){
            bodyParams.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(paramEntry -> {
                        sb.append(paramEntry.getKey()).append("=").append(toString(paramEntry.getValue())).append('#');
                    });
        }

        if (!CollectionUtils.isEmpty(params)) {
            params.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(paramEntry -> {
                        String paramValue = String.join(",", Arrays.stream(paramEntry.getValue()).sorted().toArray(String[]::new));
                        sb.append(paramEntry.getKey()).append("=").append(paramValue).append('#');
                    });
        }
        return String.join("#", secretKey, timestamp, sb.toString());
    }

    private static String toString(Object object) {
        Class<?> type = object.getClass();
        if (BeanUtils.isSimpleProperty(type)) {
            return object.toString();
        }
        if (type.isArray()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Array.getLength(object); ++i) {
                sb.append(toSplice(Array.get(object, i)));
            }
            return sb.toString();
        }
        if (ClassUtils.isAssignable(Collection.class, type)) {
            StringBuilder sb = new StringBuilder();
            for (Iterator<?> iterator = ((Collection<?>) object).iterator(); iterator.hasNext(); ) {
                sb.append(toSplice(iterator.next()));
                if (iterator.hasNext()) {
                    sb.append("=");
                }
            }
            return sb.toString();
        }
        if (ClassUtils.isAssignable(Map.class, type)) {
            StringBuilder sb = new StringBuilder();
            for (Iterator<? extends Map.Entry<String, ?>> iterator = ((Map<String, ?>) object).entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<String, ?> entry = iterator.next();
                if (Objects.isNull(entry.getValue())) {
                    continue;
                }
                sb.append(entry.getKey()).append("=").append(toSplice(entry.getValue()));
                if (iterator.hasNext()) {
                    sb.append("=");
                }
            }
            return sb.toString();
        }
        return null;
    }

    public static String toSplice(Object object) {
        if (Objects.isNull(object)) {
            return StringUtils.EMPTY;
        }
        return toString(object);
    }

    //appid：ql320230911123015310a
    //secret：1e1ff785e92b972dfea23879bebe9c67611d3b7b
    public static void main(String[] args) throws IOException {
        //{
        //    "uids":["F59D3E873F5B"],
        //    "event":"1",
        //    "url":"http://localhost:9091/test"
        //}
        //
        /*{
            "uids":["AD8A61DE4033"],
            "event": 1,
                "url":"https://www.alita3x.com"
        }*/
        Map<String, Object> params = new HashMap<>();
        //List<String> uids = new ArrayList<>();
        //uids.add("AD8A61DE4033");
        //params.put("date","2024-02-22");
        params.put("uid", "AD8A61DE4033");
        params.put("lowerHeartRate",60);
        params.put("upperHeartRate",100);
        params.put("lowerBreathe",10);
        params.put("upperBreathe",25);
        //params.put("event", 1);
        //params.put("url", "https://87811fff-bb24-402c-9b78-fb9886a5e29e.mock.pstmn.io");

//        params.put("date", "2024-02-22");
        // params.put("url", "http://localhost:9091/test");
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        String secret = getSignStr(params, null, "ada09ec6a4c8315ca189f1ba5bbe60409d78a7ed", String.valueOf(currentTimeMillis));
        byte[] sha1Digest = getSHA1Digest(secret);
        System.out.println(JSON.toJSON(params));
        System.out.println(currentTimeMillis);
        System.out.println(secret);
        System.out.println(byte2hex(sha1Digest));
    }

}
