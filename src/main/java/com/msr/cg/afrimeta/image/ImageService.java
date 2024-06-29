package com.msr.cg.afrimeta.image;

import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService implements AfrimetaCrudInterface<Image> {
    @Override
    public List<Image> findAll() {
        return List.of();
    }

    @Override
    public Image findById(Long id) {
        return null;
    }

    @Override
    public Image save(Image image) {
        return null;
    }

    @Override
    public Image update(Image image, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(Image image) {

    }
}
