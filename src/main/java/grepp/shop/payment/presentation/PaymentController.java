package grepp.shop.payment.presentation;

import grepp.shop.common.ResponseEntity;
import grepp.shop.payment.application.PaymentService;
import grepp.shop.payment.application.dto.PaymentCommand;
import grepp.shop.payment.application.dto.PaymentInfo;
import grepp.shop.payment.presentation.dto.PaymentRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "결제 목록 조회", description = "등록된 결제 내역을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<PaymentInfo>> getPayments(Pageable pageable) {
        return paymentService.getPayments(pageable);
    }

    @Operation(summary = "토스 결제 승인", description = "결제 완료 후 결제를 승인합니다.")
    @PostMapping("/confirm")
    public ResponseEntity<PaymentInfo> confirm(@RequestBody PaymentRequest request) {
        return paymentService.confirm(PaymentCommand.from(request));
    }
}
