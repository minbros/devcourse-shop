package grepp.shop.common;

public record ResponseEntity<T>(
        int status,
        int count,
        T data
) {
}
