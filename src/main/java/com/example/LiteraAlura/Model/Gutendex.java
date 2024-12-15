package com.example.LiteraAlura.Model;

import java.util.List;

public record Gutendex() {
    public record DadosGutendex(
            Integer count,
            String next,
            String previous,
            List<DadosLivro> results
    ) {
    }
}
