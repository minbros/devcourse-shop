package grepp.shop.seller.presentation;

import grepp.shop.common.ResponseEntity;
import grepp.shop.seller.application.SellerService;
import grepp.shop.seller.application.dto.SellerCommand;
import grepp.shop.seller.application.dto.SellerResponse;
import grepp.shop.seller.presentation.dto.SellerRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/sellers")
public class SellerController {
    private final SellerService sellerService;

    @Operation(summary = "판매자 목록 조회", description = "등록된 판매자를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<SellerResponse>> getSellers(Pageable pageable) {
        return sellerService.getSellers(pageable);
    }

    @Operation(summary = "새로운 판매자 생성", description = "요청을 바탕으로 새로운 판매자를 생성합니다.")
    @PostMapping
    public ResponseEntity<SellerResponse> createSeller(@RequestBody SellerRequest request) {
        return sellerService.createSeller(SellerCommand.from(request));
    }

    @Operation(summary = "판매자 수정", description = "요청을 바탕으로 판매자 정보를 수정합니다.")
    @PutMapping("{id}")
    public ResponseEntity<SellerResponse> updateSeller(@RequestBody SellerRequest request, @PathVariable UUID id) {
        return sellerService.updateSeller(SellerCommand.from(request), id);
    }

    @Operation(summary = "판매자 삭제", description = "ID에 맞는 판매자를 삭제합니다.")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable UUID id) {
        return sellerService.deleteSeller(id);
    }
}
