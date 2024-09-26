package com.zhu.authoritymanagement.shiro;

import com.zhu.authoritymanagement.entity.Account;
import com.zhu.authoritymanagement.service.IAccountService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhu
 * @since 2024-9-24
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private IAccountService accountService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        Account account = accountService.lambdaQuery().eq(Account::getUsername, username).one();
        if (account != null) {
            ByteSource salt = ByteSource.Util.bytes(account.getSalt());
            return new SimpleAuthenticationInfo(username, account.getPassword(), salt, super.getName());
        }
        return null;
    }
}
