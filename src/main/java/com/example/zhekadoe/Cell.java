package com.example.zhekadoe;

public record Cell(int x, int y) {
    @Override
    public String toString() {
        return "(%2d,%2d)".formatted(x, y);
    }
}
