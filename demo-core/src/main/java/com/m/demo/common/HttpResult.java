package com.m.demo.common;

import com.m.demo.entity.ResultData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * author:M
 * describe:
 * date:2020/9/7 16:40
 */
public class HttpResult {
    public static void result(HttpServletResponse response, ResultData result){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null){
                writer.flush();
                writer.close();
            }
        }
    }
}
