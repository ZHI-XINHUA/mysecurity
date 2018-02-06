package src.main.java.org.zxh.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

/**
 * Created by xh.zhi on 2018-2-5.
 */
//模仿的UsernamePasswordAuthenticationToken
public class IpAuthenticationToken  extends AbstractAuthenticationToken {
    private String ip;

    public IpAuthenticationToken(String ip){
        super(null);
        this.ip = ip;
        super.setAuthenticated(false); //用于认证之前，传递给认证器使用的，所以只有IP地址，自然是未认证
    }

    public IpAuthenticationToken(String ip, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.ip = ip;
        super.setAuthenticated(true);//用于认证成功之后，封装认证用户的信息，此时需要将权限也设置到其中，并且setAuthenticated(true)
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
