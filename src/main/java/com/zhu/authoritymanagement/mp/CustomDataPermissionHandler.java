package com.zhu.authoritymanagement.mp;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import java.util.Map;

/**
 * @author zhu
 * @since 2024-9-27
 */
@Slf4j
public class CustomDataPermissionHandler implements MultiDataPermissionHandler {
    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
        // 在此处编写自定义数据权限逻辑
        Map<String, Object> context = DataPermissionContextHandler.getContext();

        log.info("当前线程数据权限上下文: {}", context);
        log.info("{}", where);
        log.info("{}", mappedStatementId);
        log.info("是否忽略{}", InterceptorIgnoreHelper.willIgnoreDataPermission(mappedStatementId));

        Expression expression = null;
        if (context.containsKey("role")) {
            expression = new HexValue("username=" + context.get("role"));
        }
        if (where==null) {
            where = new EqualsTo(new Column("1"),new LongValue(1));
        }
        DataPermissionContextHandler.remove();
        return new AndExpression(where, expression);
    }
}
