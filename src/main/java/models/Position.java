package models;

public record Position(int row, int col) {
    public Position add(int row, int col) {
        return new Position(this.row + row, this.col + col);
    }

    @Override
    public String toString() {
        return "(%2d,%2d)".formatted(row, col);
    }
}
