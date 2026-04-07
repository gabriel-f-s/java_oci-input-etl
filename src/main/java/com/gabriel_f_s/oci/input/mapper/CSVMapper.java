package com.gabriel_f_s.oci.input.mapper;

public interface CSVMapper<T, K> {
    T mapTo(K k);
    K unmapFrom(T t);
}
