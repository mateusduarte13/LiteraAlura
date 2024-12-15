package com.example.LiteraAlura.Api;

public interface IConverterDados {
    <T> T obterDados(String dados, Class<T> classe);

}
