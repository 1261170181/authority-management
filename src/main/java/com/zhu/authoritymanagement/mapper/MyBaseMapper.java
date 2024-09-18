package com.zhu.authoritymanagement.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;
/**
 *
 * 批量插入接口
 *
 * @author Zhu
 * @since 2024-9-18
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    Integer insertBatchSomeColumn(Collection<T> entityList);
}

