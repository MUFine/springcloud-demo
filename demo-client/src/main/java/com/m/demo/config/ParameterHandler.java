package com.m.demo.config;

import com.m.demo.annotation.LoginDate;
import com.m.demo.annotation.LoginName;
import com.m.demo.annotation.UserId;
import com.m.demo.annotation.UserType;
import com.m.demo.common.Code;
import com.m.demo.common.HttpResult;
import com.m.demo.common.Message;
import com.m.demo.entity.ResultData;
import com.m.demo.utils.ParameterUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * author:M
 * describe:
 * date:2020/9/20 13:43
 */
@Component
public class ParameterHandler implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(UserId.class)
                || methodParameter.hasParameterAnnotation(LoginName.class)
                || methodParameter.hasParameterAnnotation(LoginDate.class)
                || methodParameter.hasParameterAnnotation(UserType.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest( HttpServletRequest.class );
        HttpServletResponse httpServletResponse = nativeWebRequest.getNativeRequest( HttpServletResponse.class );
        if(null == httpServletRequest){
            HttpResult.result(httpServletResponse,new ResultData(Code.WITHOUT_TOKEN_CODE, Message.WITHOUT_TOKEN));
        }
        return ParameterUtil.getValue(methodParameter,httpServletRequest);
    }
}
