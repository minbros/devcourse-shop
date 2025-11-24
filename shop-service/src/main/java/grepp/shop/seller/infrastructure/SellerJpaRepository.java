package grepp.shop.seller.infrastructure;

import grepp.shop.seller.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface SellerJpaRepository extends JpaRepository<Seller, UUID> {
}
