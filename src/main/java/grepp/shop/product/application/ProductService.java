package grepp.shop.product.application;

import grepp.shop.common.ResponseEntity;
import grepp.shop.product.application.dto.ProductCommand;
import grepp.shop.product.application.dto.ProductResponse;
import grepp.shop.product.domain.Product;
import grepp.shop.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ResponseEntity<List<ProductResponse>> getProducts(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        List<ProductResponse> response = page.stream()
                .map(ProductResponse::from)
                .toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), page.getNumberOfElements(), response);
    }

    public ResponseEntity<ProductResponse> createProduct(ProductCommand command) {
        Product product = Product.from(command);
        Product createdProduct = productRepository.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED.value(), 1, ProductResponse.from(createdProduct));
    }

    public ResponseEntity<ProductResponse> updateProduct(ProductCommand command, UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        product.update(command);
        Product updatedProduct = productRepository.save(product);
        return new ResponseEntity<>(HttpStatus.OK.value(), 1, ProductResponse.from(updatedProduct));
    }

    public ResponseEntity<Void> deleteProduct(UUID id) {
        productRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), 0, null);
    }
}
