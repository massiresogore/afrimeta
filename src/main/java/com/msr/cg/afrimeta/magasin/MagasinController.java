package com.msr.cg.afrimeta.magasin;

import com.msr.cg.afrimeta.clientUser.ClientUserService;
import com.msr.cg.afrimeta.magasin.converter.MagasinDtoToMagasinConverter;
import com.msr.cg.afrimeta.magasin.converter.MagasinToMagasinDtoConverter;
import com.msr.cg.afrimeta.magasin.dto.MagasinDto;
import com.msr.cg.afrimeta.magasin.dto.MagasinRequest;
import com.msr.cg.afrimeta.magasin.dto.MagasinResponse;
import com.msr.cg.afrimeta.storage.StorageService;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RestController
@RequestMapping("${api.endpoint.base-url}/magasins")
public class MagasinController {
    private final MagasinService magasinService;
    private final MagasinDtoToMagasinConverter dtoToMagasin;
    private final MagasinToMagasinDtoConverter magasinToDto;
    private final StorageService storageService;

    public MagasinController(MagasinService magasinService,
                             MagasinDtoToMagasinConverter dtoToMagasin,
                             MagasinToMagasinDtoConverter magasinToDto, StorageService storageService
    ) {
        this.magasinService = magasinService;
        this.dtoToMagasin = dtoToMagasin;
        this.magasinToDto = magasinToDto;
        this.storageService = storageService;
    }


    @GetMapping
    public Result getAllMagasins(Pageable pageable)
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "tous les magasins",
                this.magasinToDto.convert(this.magasinService.findAll(pageable))
        );
    }

    @GetMapping("/{magasinId}")
    public Result getMagasinById(@PathVariable("magasinId") String magasinId){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "magasin trouvé",
                this.magasinToDto
                        .convertToResponse(this.magasinService
                                .findById(Long.parseLong(magasinId))));
    }

    @PatchMapping("/{magasinId}")
    public Result updateMagasin(@PathVariable("magasinId") String magasinId, @RequestBody MagasinDto magasinDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "magasin mis à jours",
                this.magasinToDto
                        .convertToResponse(this.magasinService
                                .update(this.dtoToMagasin
                                        .convert(magasinDto),Long.parseLong(magasinId)
                                )
                        )
        );
    }

    @DeleteMapping("/{magasinId}")
    public Result deleteMagasin(@PathVariable("magasinId") String magasinId){
        this.magasinService.deleteById(Long.parseLong(magasinId));
        return new Result(true,StatusCode.SUCCESS,"magasin supprimé");
    }

    @PostMapping(value = "/{clientId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Result saveMagasin(MagasinRequest magasinRequest) {
            this.storageService.storeMagasinAndLogoImage(magasinRequest);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "magasin cré"
        );
    }
}
