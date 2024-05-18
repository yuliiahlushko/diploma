package com.example.excursionPlanning.services.implementations;

import com.example.excursionPlanning.dao.ExcursionRepository;
import com.example.excursionPlanning.dao.UserRepository;
import com.example.excursionPlanning.dto.ExcursionDTO;
import com.example.excursionPlanning.entity.Excursion;
import com.example.excursionPlanning.payload.web.ExcursionRequest;
import com.example.excursionPlanning.services.FileLoader;
import com.example.excursionPlanning.services.interfaces.ExcursionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExcursionServiceImpl implements ExcursionService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);


    private final UserRepository userRepository;

    private final ExcursionRepository excursionRepository;

    @Autowired
    public ExcursionServiceImpl(UserRepository userRepository, ExcursionRepository excursionRepository) {
        this.userRepository = userRepository;
        this.excursionRepository = excursionRepository;
    }


    @Override
    public Optional<Excursion> createExcursion(ExcursionRequest excursion, Principal principal) {
        Excursion newExcursion = new Excursion();

        newExcursion.setTitle(excursion.getTitle());
        newExcursion.setDescription(excursion.getDescription());
        newExcursion.setGuideId(userRepository.getUserByEmail(principal.getName()).get().getId());
        newExcursion.setPrice(excursion.getPrice());
        newExcursion.setNumberOfSeats(excursion.getNumberOfSeats());
        newExcursion.setStartTime(excursion.getStartTime());
        newExcursion.setEndTime(excursion.getEndTime());
        newExcursion.setLikes(0);

        newExcursion.setMonuments(excursion.getMonuments());

        Excursion savedExcursion = null;

        try {
            if (excursion.getImage() != null) newExcursion.setImage(excursion.getImage());
            else newExcursion.setImage(new FileLoader().loadFileAsBytes("image-icon.jpg"));
            savedExcursion = excursionRepository.save(newExcursion);


        } catch (Exception e) {
            LOG.error("Excursion {} cannot be created!", e.getMessage());
        }
        LOG.info("New excursion {} was added", savedExcursion);

        return Optional.of(savedExcursion);

    }

    @Override
    public void deleteExcursion(Long id, Principal principal) {

        Excursion excursion = excursionRepository.getExcursionById(id)
                .orElseThrow(() -> new RuntimeException("Excursion not exist"));
        try {
            excursionRepository.delete(excursion);
        } catch (Exception e) {
            LOG.error("Excursion {} cannot be deleted!", e.getMessage());
        }

    }

    @Override
    public void like(Long id, Long userId) {

        Excursion excursion = excursionRepository.getExcursionById(id)
                .orElseThrow(() -> new RuntimeException("Excursion not exist"));
        if (!excursion.getLikesUserId().contains(userId)) {
            excursion.setLikes(excursion.getLikes() + 1);
            excursion.getLikesUserId().add(userId);
        } else {
            excursion.setLikes(excursion.getLikes() - 1);
            excursion.getLikesUserId().remove(userId);
        }
    }


    @Override
    public Optional<Excursion> getExcursionById(Long id, Principal principal) {
        return excursionRepository.getExcursionById(id);
    }

    @Override
    public Optional<Excursion> putExcursion(ExcursionDTO excursion, Principal principal) {
        Optional<Excursion> newExcursion = excursionRepository.getExcursionById(excursion.getId());

        if (newExcursion.isPresent()) {

            newExcursion.get().setTitle(excursion.getTitle());
            newExcursion.get().setDescription(excursion.getDescription());
            newExcursion.get().setGuideId(userRepository.getUserByEmail(principal.getName()).get().getId());
            newExcursion.get().setPrice(excursion.getPrice());
            newExcursion.get().setNumberOfSeats(excursion.getNumberOfSeats());
            newExcursion.get().setStartTime(excursion.getStartTime());
            newExcursion.get().setEndTime(excursion.getEndTime());

            newExcursion.get().setImage(excursion.getImage());
            newExcursion.get().setMonuments(excursion.getMonuments());


            Excursion savedExcursion = null;
            try {
                savedExcursion = excursionRepository.save(newExcursion.get());
                return Optional.of(savedExcursion);

            } catch (Exception e) {
                LOG.error("Excursion {} cannot be updated!", e.getMessage());
            }

        }
        return Optional.empty();
    }

    @Override
    public Optional<Excursion> patchExcursion(ExcursionDTO excursion, Principal principal) {
        Optional<Excursion> newExcursion = excursionRepository.getExcursionById(excursion.getId());

        if (newExcursion.isPresent()) {

            if (excursion.getTitle() != null)
                newExcursion.get().setTitle(excursion.getTitle());

            if (excursion.getDescription() != null)
                newExcursion.get().setDescription(excursion.getDescription());

            if (excursion.getPrice() != null)
                newExcursion.get().setPrice(excursion.getPrice());

            if (excursion.getNumberOfSeats() != null)
                newExcursion.get().setNumberOfSeats(excursion.getNumberOfSeats());

            if (excursion.getStartTime() != null)
                newExcursion.get().setStartTime(excursion.getStartTime());

            if (excursion.getEndTime() != null)
                newExcursion.get().setEndTime(excursion.getEndTime());


            if (excursion.getImage() != null)
                newExcursion.get().setImage(excursion.getImage());
            if (excursion.getMonuments() != null)
                newExcursion.get().setMonuments(excursion.getMonuments());


            Excursion savedExcursion = null;
            try {
                savedExcursion = excursionRepository.save(newExcursion.get());
                return Optional.of(savedExcursion);

            } catch (Exception e) {
                LOG.error("Excursion {} cannot be updated!", e.getMessage());
            }

        }
        return Optional.empty();
    }

    @Override
    public List<Excursion> getAllConductedExcursionsByUser(Long guideId) {
        return excursionRepository.getAllConductedExcursionsByUser(guideId);
    }

    @Override
    public List<Excursion> getExcursionsByTitle(String title) {
        return excursionRepository.getExcursionsByTitle(title);
    }

    @Override
    public List<Excursion> getExcursionsByPrice(Long minPrice, Long maxPrice) {
        return null;
    }

    @Override
    public List<Excursion> getExcursionsByTime(LocalDateTime startTime, LocalDateTime endTime) {
        return excursionRepository.getExcursionsByTime(startTime, endTime);
    }

    @Override
    public List<Excursion> getExcursionsCreatedAfterTime(LocalDateTime time) {
        return excursionRepository.getExcursionsCreatedAfterTime(time);
    }

    @Override
    public List<Excursion> getExcursionsCreatedBeforeTime(LocalDateTime time) {
        return excursionRepository.getExcursionsCreatedAfterTime(time);
    }

    @Override
    public List<Excursion> getExcursionByMonumentId(Long monumentId) {
        return excursionRepository.getExcursionByMonumentId(monumentId);
    }

    @Override
    public Page<Excursion> getAllConductedExcursionsByUser(Long guideId, Pageable pageable) {
        return excursionRepository.getExcursionByMonumentId(guideId, pageable);
    }

    @Override
    public Page<Excursion> getExcursionsByTitle(String title, Pageable pageable) {
        return excursionRepository.getExcursionsByTitle(title, pageable);
    }

    @Override
    public Page<Excursion> getExcursionsByPrice(Long minPrice, Long maxPrice, Pageable pageable) {
        return excursionRepository.getExcursionsByPrice(minPrice, maxPrice, pageable);
    }

    @Override
    public Page<Excursion> getExcursionsByTime(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return excursionRepository.getExcursionsByTime(startTime, endTime, pageable);
    }

    @Override
    public Page<Excursion> getExcursionsCreatedAfterTime(LocalDateTime time, Pageable pageable) {
        return excursionRepository.getExcursionsCreatedAfterTime(time, pageable);
    }

    @Override
    public Page<Excursion> getExcursionsCreatedBeforeTime(LocalDateTime time, Pageable pageable) {
        return excursionRepository.getExcursionsCreatedAfterTime(time, pageable);
    }

    @Override
    public Page<Excursion> getExcursionByMonumentId(Long monumentId, Pageable pageable) {
        return excursionRepository.getExcursionByMonumentId(monumentId, pageable);
    }
}
