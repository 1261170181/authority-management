package com.zhu.authoritymanagement.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

public interface MyBaseMapper<T> extends BaseMapper<T> {

    Integer insertBatchSomeColumn(Collection<T> entityList);
}

