package br.com.danieleleao.todolist.user;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Modificador
 * Public
 * Private
 * Protected
 */


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;
    /**
     * String (texto)
     * Integer (int) numeros inteiros
     * Double numeros 0.000
     * Float (float) Números 0.000
     * Date (data)
     * void
     */
  @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
      var user = this.userRepository.findByUsername(userModel.getUsername());
      
      if(user != null)  {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
    }
      
   var passwordHahred = BCrypt.withDefaults()
   .hashToString(12, userModel.getPassword().toCharArray());
      

   userModel.setPassword(passwordHahred);
     
   
   var userCreated =  this.userRepository.save(userModel);
      return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);

    }
    
}


