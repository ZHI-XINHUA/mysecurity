package org.zxh.qqfilter;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.zxh.pojo.QQToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xh.zhi on 2018-2-5.
 */
public class QQAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final static String CODE = "code";
    /**
     * 获取 Token 的 API
     */
    private final static String accessTokenUri = "https://graph.qq.com/oauth2.0/token";

    /**
     * grant_type 由腾讯提供
     * 授权类型，在本步骤中，此值为“authorization_code”
     */
    private final static String grantType = "authorization_code";

    /**
     * client_id 由腾讯提供
     * 申请QQ登录成功后，分配给网站的appid
     */
    static final String clientId = "101386962";

    /**
     * client_secret 由腾讯提供
     * 申请QQ登录成功后，分配给网站的appkey。
     */
    private final static String clientSecret = "2a0f820407df400b84a854d054be8b6a";

    /**
     * redirect_uri 腾讯回调地址
     *  与上一步获取Authorization Code的 redirect_uri成功授权后的回调地址一致
     */
    private final static String redirectUri = "http://www.ictgu.cn/login/qq";

    /**
     * 获取 token 的地址拼接
     */
    private final static String TOKEN_ACCESS_API = "%s?grant_type=%s&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

    /**
     * 获取 OpenID 的 API 地址
     */
    private final static String openIdUri = "https://graph.qq.com/oauth2.0/me?access_token=";


    public QQAuthenticationFilter(String defaultFilterProcessesUrl){
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl,"GET"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        String code = httpServletRequest.getParameter(CODE);//上一步返回的authorization code (用户授权码)
        System.out.println("用户授权码Code : " + code);
        String tokenAccessApi =  String.format(TOKEN_ACCESS_API,accessTokenUri,grantType,clientId,clientSecret,code,redirectUri);
        //1、获取Access Token
        QQToken qqToken = getToken(tokenAccessApi);
        System.out.println(JSON.toJSONString(qqToken));
        if(qqToken==null) return null;

        //2、获取用户OpenID
        String openId = getOpenId(qqToken.getAccessToken());
        if(openId==null) return  null;

        // 3、生成验证 authenticationToken
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(qqToken.getAccessToken(),openId);

        //4、返回验证结果
        return getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取Access Token
     * @param tokenAccessApi
     * @return
     * @throws Exception
     */
    private QQToken getToken(String tokenAccessApi) throws IOException{
        //获取Access Token 成功后，返回包中获取到Access Token。
        // 如：access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
        Document document = Jsoup.connect(tokenAccessApi).get();//通过Authorization Code获取Access Token
        String tokenResult = document.text();
        System.out.println("tokenResult="+tokenResult);
        String[] tokens = tokenResult.split("&");
        if(tokens.length==3){
            QQToken qqToken = new QQToken();
            String accessToken = tokens[0].replace("access_token=","");
            String expiresIn = tokens[1].replace("expires_in=","");
            String refreshToken = tokens[2].replace("refresh_token=","");
            qqToken.setAccessToken(accessToken);
            qqToken.setExpiresIn(Integer.parseInt(expiresIn));
            qqToken.setRefreshToken(refreshToken);

            return qqToken;
        }
        return null;
    }

    /**
     * 获取用户OpenID
     * @param accessToken
     * @return
     */
    private String getOpenId(String accessToken) throws IOException{
        Document document = Jsoup.connect(openIdUri+accessToken).get();
        String resultText = document.text();
        System.out.println("open id result="+resultText);
        //PC网站接入时，获取到用户OpenID，返回包如下：
       //callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        Matcher matcher = Pattern.compile("\"openid\":\"(.*?)\"").matcher(resultText);
        if (matcher.find()){
            return matcher.group(1);
        }
        return null;
    }
}
