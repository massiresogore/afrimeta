package com.msr.cg.afrimeta.profile;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService implements AfrimetaCrudInterface<Profile> {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public List<Profile> findAll() {
        return this.profileRepository.findAll();
    }

    @Override
    public Profile findById(Long id) {
           return   this.profileRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("profile", id));
    }

    @Override
    public Profile save(Profile profile) {
        return this.profileRepository.save(profile);
    }

    @Override
    public Profile update(Profile profile, Long id) {
      return this.profileRepository.findById(id)
              .map(oldProfile ->{
                  oldProfile.setNom(profile.getNom());
                  oldProfile.setPrenom(profile.getPrenom());
                  oldProfile.setNumeroTelephone(profile.getNumeroTelephone());
                  oldProfile.setDateNaissance(profile.getDateNaissance());
                  oldProfile.setGenre(profile.getGenre());
                  oldProfile.setAddresse(profile.getAddresse());
                  oldProfile.setVille(profile.getVille());
                  oldProfile.setCodePostal(profile.getCodePostal());
                  oldProfile.setPays(profile.getPays());
                  oldProfile.setProfilePictureUrl(profile.getProfilePictureUrl());
                  oldProfile.setBio(profile.getBio());
                  return this.profileRepository.save(oldProfile);
              })
              .orElseThrow(()->new ObjectNotFoundException("profile", id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.profileRepository.deleteById(id);
    }

    @Override
    public void delete(Profile profile) {
        this.profileRepository.delete(profile);
    }
}
