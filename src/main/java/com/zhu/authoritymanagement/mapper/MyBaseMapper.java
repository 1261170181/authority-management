package com.zhu.authoritymanagement.mapper;


import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * 批量插入接口
 *
 * @author Zhu
 * @since 2024-9-18
 */
@InterceptorIgnore(dataPermission = "true")
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入
     *
     * @param entityList 实体列表
     * @return 插入数量
     */
    Integer insertBatchSomeColumn(Collection<T> entityList);
}

