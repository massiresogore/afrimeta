package com.msr.cg.afrimeta.website;

import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WebsiteService implements AfrimetaCrudInterface<Website> {
    private final WebsiteRepository repository;

    public WebsiteService(WebsiteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Website> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Website findById(Long id) {
        return this.repository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Website",id));
    }

    @Override
    public Website save(Website website) {
        return this.repository.save(website);
    }

    @Override
    public Website update(Website website, Long id) {
        return this.repository.findById(id)
                .map(oldWebsite-> {
                    oldWebsite.setWebsiteUrl(website.getWebsiteUrl());
                    oldWebsite.setMagasin(website.getMagasin());
                    return this.repository.save(oldWebsite);
                })
                .orElseThrow(()-> new ObjectNotFoundException("Website",id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.repository.deleteById(id);
    }

    @Override
    public void delete(Website website) {
        this.repository.delete(website);
    }

}
