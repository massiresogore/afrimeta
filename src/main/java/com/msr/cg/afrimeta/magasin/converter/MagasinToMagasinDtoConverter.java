package com.msr.cg.afrimeta.magasin.converter;

import com.msr.cg.afrimeta.magasin.Magasin;
import com.msr.cg.afrimeta.magasin.dto.LogoDto;
import com.msr.cg.afrimeta.magasin.dto.MagasinResponse;
import com.msr.cg.afrimeta.magasin.dto.MagasinDto;
import com.msr.cg.afrimeta.storage.FileUploadController;
import com.msr.cg.afrimeta.storage.StorageService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.util.Map;

@Component
public class MagasinToMagasinDtoConverter implements Converter<Magasin, MagasinDto> {

    private final StorageService storageService;

    public MagasinToMagasinDtoConverter(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public MagasinDto convert(Magasin source) {
        return new MagasinDto(
                source.getMagasinId(),
                source.getLibele(),
                source.getDescription(),
                source.getLogo(),
                source.getClientUser()
        );
    }

    public MagasinResponse convertToResponse(Magasin source) {

        return new MagasinResponse(
                source.getMagasinId(),
                source.getLibele(),
                source.getDescription(),
                this.convertToLogoDto(source.getLogo()),
                source.getClientUser().getUser_id()
        );
    }

    public Page<MagasinResponse> convert(Page<Magasin> source) {

      return source.map(magasin
              -> new MagasinResponse(magasin.getMagasinId(),
                                      magasin.getLibele(),
                                      magasin.getDescription(),
                                      this.convertToLogoDto(magasin.getLogo()),
                                      magasin.getClientUser().getUser_id()
              )
      );

    }

    private LogoDto convertToLogoDto(Map<String,String> logoMap) {
        LogoDto logoDto = null;

        //Récupère la value
        for (Map.Entry<String, String> entry : logoMap.entrySet()) {
//            System.out.println("la clé est :"+entry.getKey() + ", et la valeur est " + entry.getValue());
            Path path = this.storageService.load(entry.getValue());
            logoDto = new LogoDto(
                    entry.getValue(), MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                    "serveFile",path.getFileName().toString()).build().toUri().toString()
            );
        }
        return logoDto;
    }


}
