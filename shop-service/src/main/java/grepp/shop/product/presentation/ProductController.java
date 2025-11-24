package grepp.shop.product.presentation;

import grepp.shop.common.ResponseEntity;
import grepp.shop.product.application.ProductService;
import grepp.shop.product.application.dto.ProductCommand;
import grepp.shop.product.application.dto.ProductResponse;
import grepp.shop.product.presentation.dto.ProductRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/products")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 목록 조회", description = "등록된 상품을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @Operation(summary = "새로운 상품 생성", description = "요청을 바탕으로 새로운 상품을 생성합니다.")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(ProductCommand.from(request));
    }

    @Operation(summary = "상품 수정", description = "요청을 바탕으로 상품 정보를 수정합니다.")
    @PutMapping("{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest request, @PathVariable UUID id) {
        return productService.updateProduct(ProductCommand.from(request), id);
    }

    @Operation(summary = "상품 삭제", description = "ID에 맞는 상품을 삭제합니다.")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id);
    }
}
