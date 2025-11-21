package grepp.shop.settlement.infrastructure;

import grepp.shop.settlement.domain.SellerSettlement;
import grepp.shop.settlement.domain.SellerSettlementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface SellerSettlementJpaRepository extends JpaRepository<SellerSettlement, UUID> {
    List<SellerSettlement> findByStatus(SellerSettlementStatus status);

    List<SellerSettlement> findByStatusAndSellerId(SellerSettlementStatus status, UUID sellerId);
}
