package net.avh4.math.geometry;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PointTest {
    private Point subject = new Point(3, 4);

    @Test
    public void shouldHaveX() {
        assertThat(subject.x()).isEqualTo(3);
    }

    @Test
    public void shouldHaveY() {
        assertThat(subject.y()).isEqualTo(4);
    }

    @Test
    public void translate_shouldShift() {
        assertThat(subject.translate(
                Rect.fromTopLeft(0, 0, 10, 10),
                Rect.fromTopLeft(5, 6, 10, 10)))
                .isEqualTo(new Point(3 + 5, 4 + 6));
    }

    @Test
    public void translate_shouldScale() {
        assertThat(subject.translate(
                Rect.ofSize(10, 10),
                Rect.ofSize(100, 100))
        ).isEqualTo(new Point(30, 40));
    }
}
