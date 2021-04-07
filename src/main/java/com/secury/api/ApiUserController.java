package com.secury.api;

import com.secury.model.User;
import com.secury.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@CrossOrigin("*")
public class ApiUserController {
    @Autowired
    UserRepository userRepository;



    @GetMapping("")
    public Object get() {

        List<User> users = userRepository.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping("search")
    public Object search(@RequestParam String keyword) {
        List<User> users = userRepository.findByDes(keyword);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);

    }

    @CrossOrigin
    @GetMapping("email")
    public Object findByEmail(@RequestParam String email) {
        User users = userRepository.findByEmail(email);
        return new ResponseEntity<User>(users, HttpStatus.OK);

    }
    @PostMapping("")
    public Object post(@RequestBody User user) {

        User users = userRepository.findByEmail(user.getEmail());
        if(users !=null) {
            return new ResponseEntity<String>("Chọn email khác!", HttpStatus.BAD_REQUEST);
        }
        else if(user.getPassword() != user.getPassword()) {
            return new ResponseEntity<String>("Confirmpassword không trùng với password!", HttpStatus.BAD_REQUEST);
        }
        else {
            String hashsed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashsed);
            user.setConfirmPassword(hashsed);
            User userss = userRepository.save(user);
            return new ResponseEntity<User>(userss, HttpStatus.CREATED);
        }

    }
    @PutMapping("/{id}")
    public Object put(@PathVariable int id, @RequestBody User user) {
        try {
            if (userRepository.findById(id)==null||id!=user.getId()) {
                return new ResponseEntity<String>("ID không hợp lệ!", HttpStatus.BAD_REQUEST);
            }
            else {
                userRepository.save(user);
                return new ResponseEntity<String>("Cập nhật thành công!", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Cập nhật thất bại!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("password/{id}")
    public Object putPassword(@PathVariable int id, @RequestBody User user) {


        try {
            if (userRepository.findById(id)==null||id!=user.getId()) {
                return new ResponseEntity<String>("ID không hợp lệ!", HttpStatus.BAD_REQUEST);
            }
            else {
                String hashsed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
                user.setPassword(hashsed);
                userRepository.updatepassword(user.getPassword(),user.getId());
                return new ResponseEntity<String>("Cập nhật thành công!", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Cập nhật thất bại!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public Object findById(@PathVariable int id) {
        Optional<User> users = userRepository.findById(id);
        User user = users.get();
        return new ResponseEntity<User>(user, HttpStatus.OK);

    }
    @DeleteMapping("delete/{id}")
    public Object delete(@PathVariable int id) {
        userRepository.deleteById(id);
        return new ResponseEntity<String>("Xóa thành công", HttpStatus.OK);
    }
}
