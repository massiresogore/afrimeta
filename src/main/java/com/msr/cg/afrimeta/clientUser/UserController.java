package com.msr.cg.afrimeta.clientUser;

import com.msr.cg.afrimeta.clientUser.converter.ClientUserDtoToClientUserConverter;
import com.msr.cg.afrimeta.clientUser.converter.ClientUserRequestToClientUserConverter;
import com.msr.cg.afrimeta.clientUser.converter.ClientUserToClientUserDtoConverter;
import com.msr.cg.afrimeta.clientUser.dto.ClientUserDto;
import com.msr.cg.afrimeta.clientUser.dto.ClientUserRequest;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {


    private final ClientUserService clientUserService;
    private final ClientUserDtoToClientUserConverter clientUserDtoToClientUserConverter;
    private final ClientUserToClientUserDtoConverter clientUserToClientUserDtoConverter;
    private final ClientUserRequestToClientUserConverter clientUserRequestToClientUserConverter;


    public UserController(ClientUserService clientUserService,
                          ClientUserDtoToClientUserConverter clientUserDtoToClientUserConverter,
                          ClientUserToClientUserDtoConverter clientUserToClientUserDtoConverter, ClientUserRequestToClientUserConverter clientUserRequestToClientUserConverter ) {
        this.clientUserService = clientUserService;
        this.clientUserDtoToClientUserConverter = clientUserDtoToClientUserConverter;
        this.clientUserToClientUserDtoConverter = clientUserToClientUserDtoConverter;
        this.clientUserRequestToClientUserConverter = clientUserRequestToClientUserConverter;
    }

    @GetMapping
    public Result getAllUsers()
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "tous les users",
                this.clientUserService
                        .findAll().stream().map(client -> new ClientUserDto(
                        client.getUser_id(),
                        client.getUsername(),
                        client.getEmail(),
                        client.getPassword(),
                        client.isEnable(),
                        client.getRole(),
                        client.getProfile()
                )).toList()
        );
    }

    @GetMapping("/{userId}")
    public Result getUserById(@PathVariable("userId") String userId){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "l'utilisateur trouvé",
                this.clientUserToClientUserDtoConverter
                        .convert(this.clientUserService
                                .findById(Long.parseLong(userId))));
    }

    @PatchMapping("/{userId}")
    public Result updateUser(@PathVariable("userId") String userId, @RequestBody ClientUserDto clientUserDto){
       return new Result(
               true,
               StatusCode.SUCCESS,
               "user mis à jours",
               this.clientUserToClientUserDtoConverter
                       .convert(this.clientUserService
                               .update(this.clientUserDtoToClientUserConverter
                                       .convert(clientUserDto),Long.parseLong(userId)
                               )
                       )
       );
    }

    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable("userId") String userId){
        this.clientUserService.deleteById(Long.parseLong(userId));
        return new Result(true,StatusCode.SUCCESS,"l'utilisateur supprimé");
    }

    @PostMapping
    public Result saveUser(@RequestBody ClientUserRequest clientUserRequest){
        //System.out.print(clientUserRequest);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "user créé",
                this.clientUserToClientUserDtoConverter
                        .convert(this.clientUserService
                                .save(this.clientUserRequestToClientUserConverter
                                        .convert(clientUserRequest)
                                )
                        )
        );

    }
}
