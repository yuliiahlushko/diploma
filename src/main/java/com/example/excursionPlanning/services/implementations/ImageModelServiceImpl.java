package com.example.excursionPlanning.services.implementations;

import com.example.excursionPlanning.dao.ImageModelRepository;
import com.example.excursionPlanning.dto.ImageModelDTO;
import com.example.excursionPlanning.entity.ImageModel;
import com.example.excursionPlanning.services.interfaces.ImageModelService;
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
public class ImageModelServiceImpl implements ImageModelService {
    private static final Logger LOG = LoggerFactory.getLogger(ImageModelServiceImpl.class);

    private final ImageModelRepository imageModelRepository;

    @Autowired
    public ImageModelServiceImpl(ImageModelRepository imageModelRepository) {
        this.imageModelRepository = imageModelRepository;
    }

    @Override
    public ImageModel createImageModel(ImageModelDTO imageModelDTO, Principal principal) {
        ImageModel imageModel = new ImageModel();

        imageModel.setMonumentId(imageModelDTO.getMonumentId());
        imageModel.setImageBytes(imageModelDTO.getImageBytes());

        ImageModel savedImageModel = null;

        try {
            savedImageModel = imageModelRepository.save(imageModel);
        } catch (Exception e) {
            LOG.error("image model {} cannot be created!", e.getMessage());
        }
        LOG.info("New image model {} was added", savedImageModel);
        return savedImageModel;
    }

    @Override
    public void deleteImageModel(Long id, Principal principal) {
        ImageModel imageModel = imageModelRepository.getImageModelById(id)
                .orElseThrow(() -> new RuntimeException("Image model not exist"));
        try {
            imageModelRepository.delete(imageModel);
        } catch (Exception e) {
            LOG.error("Image model {} cannot be deleted!", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImageModel> getImageModelById(Long id, Principal principal) {
        return imageModelRepository.getImageModelById(id);
    }

    @Override
    public Optional<ImageModel> putImageModel(ImageModelDTO imageModelDTO, Principal principal) {
        Optional<ImageModel> imageModel = imageModelRepository.getImageModelById(imageModelDTO.getId());

        if (imageModel.isPresent()) {

            imageModel.get().setMonumentId(imageModelDTO.getMonumentId());
            imageModel.get().setImageBytes(imageModelDTO.getImageBytes());

            ImageModel savedImageModel = null;
            try {
                savedImageModel = imageModelRepository.save(imageModel.get());
                return Optional.of(savedImageModel);
            } catch (Exception e) {
                LOG.error("Image Model {} cannot be updated!", e.getMessage());
            }

        }
        return Optional.empty();
    }

    @Override
    public Optional<ImageModel> patchImageModel(ImageModelDTO imageModelDTO, Principal principal) {
        Optional<ImageModel> imageModel = imageModelRepository.getImageModelById(imageModelDTO.getId());

        if (imageModel.isPresent()) {

            if (imageModelDTO.getMonumentId() != null)
                imageModel.get().setMonumentId(imageModelDTO.getMonumentId());
            if (imageModelDTO.getImageBytes() != null)
                imageModel.get().setImageBytes(imageModelDTO.getImageBytes());

            ImageModel savedImageModel = null;
            try {
                savedImageModel = imageModelRepository.save(imageModel.get());
                return Optional.of(savedImageModel);
            } catch (Exception e) {
                LOG.error("Image Model {} cannot be updated!", e.getMessage());
            }

        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageModel> getAllImageModelsByMonumentId(Long monumentId) {
        return imageModelRepository.getAllImageModelsByMonumentId(monumentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImageModel> getAllImageModelsByMonumentId(Long monumentId, Pageable pageable) {
        return imageModelRepository.getAllImageModelsByMonumentId(monumentId, pageable);
    }
}
