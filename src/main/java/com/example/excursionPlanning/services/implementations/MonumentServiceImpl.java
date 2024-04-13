package com.example.excursionPlanning.services.implementations;

import com.example.excursionPlanning.dao.MonumentRepository;
import com.example.excursionPlanning.dao.UserRepository;
import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.entity.Monument;
import com.example.excursionPlanning.services.interfaces.MonumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MonumentServiceImpl implements MonumentService {

    private static final Logger LOG = LoggerFactory.getLogger(MonumentServiceImpl.class);

    private final MonumentRepository monumentRepository;

    @Autowired
    public MonumentServiceImpl(MonumentRepository monumentRepository, UserRepository userRepository) {
        this.monumentRepository = monumentRepository;
    }

    @Override
    public Monument createMonument(MonumentDTO monumentDTO, Principal principal) {
        Monument monument = new Monument();

        monument.setTitle(monumentDTO.getTitle());
        monument.setDescription(monumentDTO.getDescription());
        monument.setPrice(monumentDTO.getPrice());

        Monument savedMonument = null;

        try {
            savedMonument = monumentRepository.save(monument);
        } catch (Exception e) {
            LOG.error("Monument {} cannot be created!", e.getMessage());
        }
        LOG.info("New monument {} was added", savedMonument);
        return savedMonument;


    }

    @Override
    public void deleteMonument(Long id, Principal principal) {
        Monument monument = monumentRepository.getMonumentById(id)
                .orElseThrow(() -> new RuntimeException("Monument not exist"));
        try {
            monumentRepository.delete(monument);
        } catch (Exception e) {
            LOG.error("Monument {} cannot be deleted!", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Monument> getMonumentById(Long id, Principal principal) {
        return monumentRepository.getMonumentById(id);

    }

    @Override
    public Optional<Monument> putMonument(MonumentDTO monumentDTO, Principal principal) {

        Optional<Monument> monument = monumentRepository.getMonumentById(monumentDTO.getId());

        if (monument.isPresent()) {
            monument.get().setTitle(monumentDTO.getTitle());
            monument.get().setDescription(monumentDTO.getDescription());
            monument.get().setPrice(monumentDTO.getPrice());

            Monument savedMonument = null;
            try {
                savedMonument = monumentRepository.save(monument.get());
                return Optional.of(savedMonument);
            } catch (Exception e) {
                LOG.error("Monument {} cannot be updated!", e.getMessage());
            }

        }
        return Optional.empty();
    }

    @Override
    public Optional<Monument> patchMonument(MonumentDTO monumentDTO, Principal principal) {
        Optional<Monument> monument = monumentRepository.getMonumentById(monumentDTO.getId());

        if (monument.isPresent()) {
            if (monumentDTO.getTitle() != null)
                monument.get().setTitle(monumentDTO.getTitle());
            if (monumentDTO.getDescription() != null)
                monument.get().setDescription(monumentDTO.getDescription());
            if (monumentDTO.getPrice() != null)
                monument.get().setPrice(monumentDTO.getPrice());

            try {
                return Optional.of(monumentRepository.save(monument.get()));

            } catch (Exception e) {
                LOG.error("Monument {} cannot be updated!", e.getMessage());
            }

        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monument> getMonumentByTitle(String title) {
        return monumentRepository.getMonumentByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monument> getMonumentsByPrice(Long minPrice, Long maxPrice) {
        return monumentRepository.getMonumentsByPrice(minPrice, maxPrice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monument> getMonumentsByAvgGrade(Integer avgGrade) {
        return monumentRepository.getMonumentsByAvgGrade(avgGrade);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Monument> getMonumentsByExcursionId(Long excursionId) {
        return monumentRepository.getMonumentsByExcursionId(excursionId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Monument> getMonumentsByTitle(String title, Pageable pageable) {
        return monumentRepository.getMonumentByTitle(title, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Monument> getMonumentsByPrice(Long minPrice, Long maxPrice, Pageable pageable) {
        return monumentRepository.getMonumentsByPrice(minPrice, maxPrice, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Monument> getMonumentsByAvgGrade(Integer avgGrade, Pageable pageable) {
        return monumentRepository.getMonumentsByAvgGrade(avgGrade, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Monument> getMonumentsByExcursionId(Long excursionId, Pageable pageable) {
        return monumentRepository.getMonumentsByExcursionId(excursionId, pageable);
    }

}
