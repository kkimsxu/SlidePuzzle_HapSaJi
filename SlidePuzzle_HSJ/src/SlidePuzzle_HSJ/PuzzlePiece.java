package SlidePuzzle_HSJ;

import java.awt.image.BufferedImage;

class PuzzlePiece {
    private int face;
    private BufferedImage image;

    public PuzzlePiece(int face, BufferedImage img) {
        this.face = face;
        this.image = img;
    }

    public int face() {
        return face;
    }

    public BufferedImage getImage() {
        return image;
    }
}
