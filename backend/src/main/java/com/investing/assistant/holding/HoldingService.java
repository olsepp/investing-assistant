package com.investing.assistant.holding;

import com.investing.assistant.holding.dto.CreateHoldingRequest;
import com.investing.assistant.holding.dto.HoldingResponse;
import com.investing.assistant.holding.dto.UpdateHoldingRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/** Business logic for holdings. Controllers (and later the AI assistant's tools) call this. */
@Service
public class HoldingService {

    private final HoldingRepository holdingRepository;

    public HoldingService(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }

    @Transactional(readOnly = true)
    public List<HoldingResponse> findAll() {
        return holdingRepository.findAllByOrderByTickerAsc().stream()
                .map(HoldingResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public HoldingResponse findById(Long id) {
        return HoldingResponse.from(getHolding(id));
    }

    @Transactional
    public HoldingResponse create(CreateHoldingRequest request) {
        String ticker = normalize(request.ticker());
        if (holdingRepository.existsByTicker(ticker)) {
            throw new DuplicateHoldingException(ticker);
        }
        Holding holding = new Holding(
                ticker,
                request.quantity(),
                request.totalCost(),
                normalize(request.currency()));
        return HoldingResponse.from(holdingRepository.save(holding));
    }

    @Transactional
    public HoldingResponse update(Long id, UpdateHoldingRequest request) {
        Holding holding = getHolding(id);
        holding.updatePosition(request.quantity(), request.totalCost(), normalize(request.currency()));
        // No save() needed: JPA notices the change and writes it when the transaction ends.
        // flush() writes it now, so the response already contains the new updatedAt.
        holdingRepository.flush();
        return HoldingResponse.from(holding);
    }

    @Transactional
    public void delete(Long id) {
        holdingRepository.delete(getHolding(id));
    }

    private Holding getHolding(Long id) {
        return holdingRepository.findById(id)
                .orElseThrow(() -> new HoldingNotFoundException(id));
    }

    /** "aapl " -> "AAPL", so the same ticker or currency is always stored the same way. */
    private static String normalize(String value) {
        return value == null ? null : value.trim().toUpperCase(Locale.ROOT);
    }
}
