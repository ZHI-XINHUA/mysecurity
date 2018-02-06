package org.zxh.qqfilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.zxh.pojo.QQUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.zxh.qqfilter.QQAuthenticationFilter.clientId;

/**
 * Created by xh.zhi on 2018-2-6.
 */
public class QQAuthenticationManager implements AuthenticationManager {

    private static  final List<GrantedAuthority>  AUTHORITIES= new ArrayList<GrantedAuthority>();

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));//添加角色
    }

    /**
     * 获取 QQ 登录信息的 API 地址
     */
    private final static String userInfoUri = "https://graph.qq.com/user/get_user_info";

    /**
     * 获取 QQ 用户信息的地址拼接
     */
    private final static String USER_INFO_API = "%s?access_token=%s&oauth_consumer_key=%s&openid=%s";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication.getPrincipal()!=null  && authentication.getName()!=null){
            QQUser qqUser = getUserInfo(authentication.getName(),(String)authentication.getCredentials());
            return new UsernamePasswordAuthenticationToken(qqUser,null,AUTHORITIES);
        }
        throw  new BadCredentialsException("Bad Credentials");
    }

    /**
     * 调用OpenAPI接口 获取qq用户信息
     * @param accessToken
     * @param openId
     * @return
     */
    private QQUser getUserInfo(String accessToken, String openId) {
        QQUser user = new QQUser();
        String url = String.format(USER_INFO_API,userInfoUri,accessToken,clientId,openId);
        try {
            Document document = Jsoup.connect(url).get();
            String result = document.text();
            JSONObject json = JSON.parseObject(result);
            user.setNickname(json.getString("nickname"));
            user.setGender(json.getString("gender"));
            user.setProvince(json.getString("province"));
            user.setYear(json.getString("year"));
            user.setAvatar(json.getString("figureurl_qq_2"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("Bad Credentials!");
        }

        return user;

    }
}
