package grepp.shop.common;

import org.springframework.http.HttpStatus;

import java.util.Collection;

public record ResponseEntity<T>(
        int status,
        int count,
        T data
) {
    public ResponseEntity(HttpStatus status, T data) {
        this(status.value(), calculateCount(data), data);
    }

    private static int calculateCount(Object data) {
        if (data == null) {
            return 0;
        }
        if (data instanceof Collection<?> collection) {
            return collection.size();
        }
        return 1;
    }
}
