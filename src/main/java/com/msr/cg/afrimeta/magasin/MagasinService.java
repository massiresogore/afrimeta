package com.msr.cg.afrimeta.magasin;

import com.msr.cg.afrimeta.image.Image;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.ProduitService;
import com.msr.cg.afrimeta.storage.StorageService;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
public class MagasinService implements AfrimetaCrudInterface<Magasin> {
    private final MagasinRepository magasinRepository;

    private final ProduitService produitService;


    public MagasinService(MagasinRepository magasinRepository, ProduitService produitService) {
        this.magasinRepository = magasinRepository;
        this.produitService = produitService;
    }

    @Override
    public List<Magasin> findAll() {
        return this.magasinRepository.findAll();
    }


    public Page<Magasin> findAll(Pageable pageable) {
        return this.magasinRepository.findAll(pageable);
    }

    @Override
    public Magasin findById(Long id) {

        return this.magasinRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException("Magasin",id));
    }

    @Override
    public Magasin save(Magasin magasin) {
        return this.magasinRepository.save(magasin);
    }

    @Override
    public Magasin update(Magasin magasin, Long id) {
        return this.magasinRepository.findById(id)
                .map(oldWebsite ->{
                    oldWebsite.setLibele(magasin.getLibele());
                    oldWebsite.setDescription(magasin.getDescription());
                    //ne doit pas etre mis à jour pour linstant
//                    oldWebsite.setLogo(magasin.getLogo());
                   // oldWebsite.setClientUser(magasin.getClientUser());
                    return this.magasinRepository.save(oldWebsite);
                })
                .orElseThrow(()-> new ObjectNotFoundException("Magasin",id));

    }

    @Override
    public void deleteById(Long id) {
        this.magasinRepository.deleteById(id);
    }

    @Override
    public void delete(Magasin magasin) {
        this.magasinRepository.delete(magasin);
    }

    public void deleteMagasinAndHisLogoByIdMagasin(String magasinId, StorageService storageService) {
        Magasin magasin = this.findById(Long.parseLong(magasinId));

        if (this.deleteLogoMagasin(storageService, magasin.getLogo())){
            this.removeAllProductImagesBelongeToMagasinWebsite(magasinId, storageService);
            this.deleteById(Long.parseLong(magasinId));
        }
    }

    private void removeAllProductImagesBelongeToMagasinWebsite(String magasinId, StorageService storageService) {
        //Récupère l'id des produit appartenant à un magasin
        List<Integer> produitId = this.findAllProduitsByIdMagasin(magasinId);

        for(Integer produit : produitId) {
            //Récupère produit
            Produit produitFound = this.produitService.findById(Long.valueOf(produit));

            //Récupère la liste d'image limage
            List<Image> images = produitFound.getImages();
            for (Image image : images) {
                //Récupère le path
                Path path = storageService.load(image.getFilePath());
                System.out.println(path);

                //Supprimer ces images
                storageService.deleteOne(image.getFilePath());
            }
        }
    }

    private boolean deleteLogoMagasin(StorageService storageService, Map<String,String >  logo) {

        for (Map.Entry<String,String> entry : logo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //Verifie si l'image existe
            Path path = storageService.load(value);
            if(path != null) {
                storageService.deleteOne(key);
                return true;
            }
        }
        return false;
    }

    public List<Integer> findAllProduitsByIdMagasin(String idMagasin) {
        return this.magasinRepository.findAllProduitsByIdMagasin(idMagasin);
    }

}
