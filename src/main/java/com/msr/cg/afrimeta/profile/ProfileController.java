package com.msr.cg.afrimeta.profile;


import com.msr.cg.afrimeta.profile.converter.ProfileDtoToProfileConverter;
import com.msr.cg.afrimeta.profile.converter.ProfileToProfileDtoConverter;
import com.msr.cg.afrimeta.profile.dto.ProfileDto;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;

@Controller
@RestController
@RequestMapping("${api.endpoint.base-url}/user/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileDtoToProfileConverter profileDtoToProfileConverter;
    private final ProfileToProfileDtoConverter profileToProfileDtoConverter;

    public ProfileController(ProfileService profileService,
                             ProfileDtoToProfileConverter profileDtoToProfileConverter,
                             ProfileToProfileDtoConverter profileToProfileDtoConverter
    ) {
        this.profileService = profileService;
        this.profileDtoToProfileConverter = profileDtoToProfileConverter;
        this.profileToProfileDtoConverter = profileToProfileDtoConverter;
    }



    @PostMapping
    public Result saveUser(@RequestBody ProfileDto profileDto){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "profile créé",
                this.profileToProfileDtoConverter
                        .convert(this.profileService
                                .save(this.profileDtoToProfileConverter
                                        .convert(profileDto)))
        );
    }

    @GetMapping
    public Result getAllProfiles()
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "tous les profiles",
                this.profileService
                        .findAll().stream().map(profile -> new ProfileDto(
                                profile.getProfileId(),
                                profile.getNom(),
                                profile.getPrenom(),
                                profile.getNumeroTelephone(),
                                profile.getDateNaissance().toString(),
                                profile.getGenre(),
                                profile.getAddresse(),
                                profile.getVille(),
                                profile.getCodePostal(),
                                profile.getPays(),
                                profile.getProfilePictureUrl(),
                                profile.getBio()
                        )).toList()
        );
    }

    @GetMapping("/{profileId}")
    public Result getProfileById(@PathVariable("profileId") String profileId){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "le profile trouvé",
                this.profileToProfileDtoConverter
                        .convert(this.profileService
                                .findById(Long.parseLong(profileId))));
    }

    @PatchMapping("/{profileId}")
    public Result updateProfile(@PathVariable("profileId") String profileId, @RequestBody ProfileDto profileDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "profile mis à jours",
                this.profileToProfileDtoConverter
                        .convert(this.profileService
                                .update(this.profileDtoToProfileConverter
                                        .convert(profileDto),Long.parseLong(profileId)
                                )
                        )
        );
    }

    @DeleteMapping("/{profileId}")
    public Result deleteProfile(@PathVariable("profileId") String profileId){
        this.profileService.deleteById(Long.parseLong(profileId));
        return new Result(true,StatusCode.SUCCESS,"profile supprimé");
    }

}
