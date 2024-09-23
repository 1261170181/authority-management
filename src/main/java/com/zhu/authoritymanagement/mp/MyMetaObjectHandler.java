package com.zhu.authoritymanagement.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zhu.authoritymanagement.entity.Account;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;

/**
 * MP自动填充
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_TIME = "createTime";

    private static final String CREATE_ACCOUNT_ID = "createAccountId";

    private static final String MODIFIED_TIME = "modifiedTime";

    private static final String MODIFIED_ACCOUNT_ID = "modifiedAccountId";

    @Override
    public void insertFill(MetaObject metaObject) {

        if (metaObject.hasSetter(CREATE_TIME)) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        }

        if (metaObject.hasSetter(CREATE_ACCOUNT_ID)) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                Account account = (Account) requestAttributes.getAttribute("account", RequestAttributes.SCOPE_SESSION);
                if (account != null) {
                    Long accountId = account.getAccountId();
                    this.strictInsertFill(metaObject, "createAccountId", Long.class, accountId);
                }
            }
        }


    }

    @Override
    public void updateFill(MetaObject metaObject) {

        if (metaObject.hasSetter(MODIFIED_TIME)) {
            this.strictUpdateFill(metaObject, "modifiedTime", LocalDateTime.class, LocalDateTime.now());
        }

        if (metaObject.hasSetter(MODIFIED_ACCOUNT_ID)) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                Account account = (Account) requestAttributes.getAttribute("account", RequestAttributes.SCOPE_SESSION);
                if (account != null) {
                    Long accountId = account.getAccountId();
                    this.strictUpdateFill(metaObject, "modifiedAccountId", Long.class, accountId);
                }
            }
        }

    }
}
