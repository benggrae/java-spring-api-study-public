package com.project.springapistudy.beverages.application;

import com.project.springapistudy.beverages.application.dto.BeverageCreateRequest;
import com.project.springapistudy.beverages.application.dto.BeverageCreateResponse;
import com.project.springapistudy.beverages.application.dto.BeverageRetrieveResponse;
import com.project.springapistudy.beverages.application.dto.BeverageUpdateRequest;
import com.project.springapistudy.beverages.constants.BeverageMessages;
import com.project.springapistudy.beverages.domain.Beverage;
import com.project.springapistudy.beverages.domain.BeverageRepository;
import com.project.springapistudy.beverages.exception.BeverageBadRequestException;
import com.project.springapistudy.beverages.exception.BeverageNotFountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeverageService {

    private final BeverageRepository beverageRepository;

    @Transactional
    public BeverageCreateResponse create(BeverageCreateRequest request) {
        Beverage createItem;
        try {
            createItem = beverageRepository.save(BeverageCreateRequest.to(request));
        } catch (DataIntegrityViolationException e) {
            throw new BeverageBadRequestException(BeverageMessages.BEVERAGE_ALREADY_NAME);
        }

        return BeverageCreateResponse.of(createItem);
    }

    public BeverageRetrieveResponse retrieve(long id) {
        var findItem = beverageRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BeverageNotFountException(BeverageMessages.BEVERAGE_NOT_FOUND));

        return BeverageRetrieveResponse.of(findItem);
    }

    public List<BeverageRetrieveResponse> retrieveAll() {
        var findAll = beverageRepository.findByDeletedFalseOrderByUpdatedDesc();

        return findAll.stream()
                .map(BeverageRetrieveResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(long id, BeverageUpdateRequest request) {
        var findItem = beverageRepository.findById(id)
                .orElseThrow(() -> new BeverageNotFountException(BeverageMessages.BEVERAGE_NOT_FOUND));

        var requestItem = request.to();
        findItem.update(requestItem);
    }

    @Transactional
    public void delete(long id) {
        var findItem = beverageRepository.findById(id)
                .orElseThrow(() -> new BeverageNotFountException(BeverageMessages.BEVERAGE_NOT_FOUND));

        findItem.delete();
    }
}
