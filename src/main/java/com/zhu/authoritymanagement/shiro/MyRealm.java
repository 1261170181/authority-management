package com.zhu.authoritymanagement.shiro;

import com.zhu.authoritymanagement.entity.Account;
import com.zhu.authoritymanagement.entity.Resource;
import com.zhu.authoritymanagement.service.IAccountService;
import com.zhu.authoritymanagement.service.IResourceService;
import com.zhu.authoritymanagement.vo.RoleVO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhu
 * @since 2024-9-24
 */
@Component
public class MyRealm extends AuthorizingRealm {

    private IAccountService accountService;

    private IResourceService resourceService;

    @Autowired
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        List<RoleVO> roles = accountService.findRolesByUsername(primaryPrincipal);
        if (!CollectionUtils.isEmpty(roles)) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            roles.forEach(roleVO -> {
                simpleAuthorizationInfo.addRole(roleVO.getRoleName());
                // Permission information
                List<Resource> resources = resourceService.listResourceByRoleId(roleVO.getRoleId());
                if (!CollectionUtils.isEmpty(resources)) {
                    resources.forEach(resource -> simpleAuthorizationInfo.addStringPermission(resource.getUrl()));
                }
            });
            return simpleAuthorizationInfo;
        }
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
