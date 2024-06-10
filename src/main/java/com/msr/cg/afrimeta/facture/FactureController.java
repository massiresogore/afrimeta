package com.msr.cg.afrimeta.facture;

import com.msr.cg.afrimeta.facture.converter.FactureDtoToFactureConverter;
import com.msr.cg.afrimeta.facture.converter.FactureToFactureDtoConverter;
import com.msr.cg.afrimeta.facture.dto.FactureDto;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("${api.endpoint.base-url}/factures")
public class FactureController {

   private final FactureService factureService;
   private final FactureDtoToFactureConverter factureDtoToFactureConverter;
   private final FactureToFactureDtoConverter factureToFactureDtoConverter;

    public FactureController(FactureService factureService, FactureDtoToFactureConverter factureDtoToFactureConverter, FactureToFactureDtoConverter factureToFactureDtoConverter) {
        this.factureService = factureService;
        this.factureDtoToFactureConverter = factureDtoToFactureConverter;
        this.factureToFactureDtoConverter = factureToFactureDtoConverter;
    }

    @GetMapping
    public Result getAllFactures()
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "toutes les factures",
                this.factureService.findAll().stream()
                        .map(factureToFactureDtoConverter::convert)
        );
    }

    @GetMapping("/{factureId}")
    public Result getFactureById(@PathVariable("factureId") String factureId){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "la facture trouvée",
                this.factureToFactureDtoConverter
                        .convert(this.factureService
                                .findById(Long.parseLong(factureId))));
    }

    @PatchMapping("/{factureId}")
    public Result updateFacture(@PathVariable("factureId") String factureId, @RequestBody FactureDto factureDto){
       return new Result(
               true,
               StatusCode.SUCCESS,
               "facture mis à jours",
               this.factureToFactureDtoConverter
                       .convert(this.factureService
                               .update(this.factureDtoToFactureConverter
                                       .convert(factureDto),Long.parseLong(factureId)
                               )
                       )
       );
    }

    @DeleteMapping("/{factureId}")
    public Result deleteFacture(@PathVariable("factureId") String factureId){
        this.factureService.deleteById(Long.parseLong(factureId));
        return new Result(true,StatusCode.SUCCESS,"facture supprimée");
    }

    @PostMapping
    public Result saveFacture(@RequestBody FactureDto factureDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "user créé",
                this.factureToFactureDtoConverter
                        .convert(this.factureService
                                .save(this.factureDtoToFactureConverter
                                        .convert(factureDto)
                                )
                        )
        );

    }
}
