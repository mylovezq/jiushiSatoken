package com.jiushi.core.common.utils.redis;

/**
 * @author dengmingyang
 * @since 2023/11/28 16:43
 */
@FunctionalInterface
public interface RunWithRedissonLockMethod {
    Object runMethod();
}
