package com.example.demo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@Controller
@RestController
public class ApiController {
    private final List<User> users = new ArrayList<>();

    // curl -v -X POST -H "Content-Type: application/json" localhost:8080/users/darksouls1thebest -d "{\"username\":\"Manus\",\"password\":\"darksouls1thebest\",\"id\":\"1\",\"age\":\"100\"}"
    @PostMapping("users/{repeatPassword}")
    public ResponseEntity<Void> addUser(@RequestBody User user, @PathVariable("repeatPassword") String repeatPassword) {
        String userID = user.getId();
        String password = user.getPassword();
        if (repeatPassword.equals(password)) {
            for (int i = 0; i < users.size(); i++) {
                User userCheck = users.get(i);
                String IDCheck = userCheck.getId();
                if (IDCheck.equals(userID)) {
                    return status(409).build();
                }
            }
            users.add(user);
            return ResponseEntity.accepted().build();
        } else {
            return status(400).build();
        }
    }

    //curl -v -X GET -H "Content-Type: application/json" "localhost:8080/users/0/1?age=100&sortBy=id&direction=up"
    @GetMapping("users/{page}/{quantity}")
    public ResponseEntity<List<UserInfo>> getUsers(@RequestParam(value = "age", defaultValue = "-1") int age, @RequestParam(value = "sortBy", defaultValue = "no") String sortBy, @RequestParam(value = "direction", defaultValue = "no") String direction, @PathVariable("page") int page, @PathVariable ("quantity") int quantity) {
        if (age <= -1) {
            List<UserInfo> userInfos = new ArrayList<>();
            for (int i = 0; i < users.size(); i++) {
                UserInfo userInfo = new UserInfo(users.get(i).getUsername(), users.get(i).getId(), users.get(i).getAge());
                userInfos.add(userInfo);
            }
            if (sortBy.equals("id")){
                List<String>IDs=new ArrayList<>();
                for (int j = 0; j < userInfos.size(); j++){
                    IDs.add(userInfos.get(j).getId());
                }
                if (direction.equals("up")){
                    Collections.sort(IDs);
                }
                if (direction.equals("down")){
                    Collections.sort(IDs);
                    Collections.reverse(IDs);
                }
                List<UserInfo>sorted = new ArrayList<>();
                for (int k = 0; k < IDs.size(); k++){
                    String ID = IDs.get(k);
                    for (int n = 0; n < userInfos.size(); n++){
                        if (userInfos.get(n).getId().equals(ID)){
                            sorted.add(userInfos.get(n));
                        }
                    }
                }
                if (page >= 0 && quantity > 0){
                    List<UserInfo>answer = new ArrayList<>();
                    int index = page * quantity;
                    while (index < page * quantity + quantity && index < sorted.size()){
                        answer.add(sorted.get(index));
                        index++;
                    }
                    return ResponseEntity.ok(answer);
                }
                return ResponseEntity.ok(sorted);
            }
            if (page >= 0 && quantity > 0){
                List<UserInfo> answer = new ArrayList<>();
                int index = page * quantity;
                while (index < page * quantity + quantity && index < userInfos.size()){
                    answer.add(userInfos.get(index));
                    index++;
                }
                return ResponseEntity.ok(answer);
            }
            return ResponseEntity.ok(userInfos);
        } else {
            List<UserInfo> userInfos = new ArrayList<>();
            for (int i = 0; i < users.size(); i++) {
                int userAge = users.get(i).getAge();
                if (age - 5 <= userAge && age + 5 >= userAge) {
                    UserInfo userInfo = new UserInfo(users.get(i).getUsername(), users.get(i).getId(), users.get(i).getAge());
                    userInfos.add(userInfo);
                }
            }
            if (sortBy.equals("id")){
                List<String>IDs = new ArrayList<>();
                for (int j = 0; j < userInfos.size(); j++){
                    IDs.add(userInfos.get(j).getId());
                }
                if (direction.equals("up")){
                    Collections.sort(IDs);
                }
                if (direction.equals("down")){
                    Collections.sort(IDs);
                    Collections.reverse(IDs);
                }
                List<UserInfo>sorted = new ArrayList<>();
                for (int k = 0; k < IDs.size(); k++){
                    String ID = IDs.get(k);
                    for (int n = 0; n<userInfos.size(); n++){
                        if (userInfos.get(n).getId().equals(ID)){
                            sorted.add(userInfos.get(n));
                        }
                    }
                }
                if (page >=0 && quantity > 0){
                    List<UserInfo>answer = new ArrayList<>();
                    int index = page*quantity;
                    while (index < page * quantity + quantity && index < sorted.size()){
                        answer.add(sorted.get(index));
                        index++;
                    }
                    return ResponseEntity.ok(answer);
                }
                return ResponseEntity.ok(sorted);
            }
            if (page >= 0 && quantity > 0){
                List<UserInfo> answer = new ArrayList<>();
                int index = page * quantity;
                while (index < page*quantity + quantity && index < userInfos.size()){
                    answer.add(userInfos.get(index));
                    index++;
                }
                return ResponseEntity.ok(answer);
            }
            return ResponseEntity.ok(userInfos);
        }
    }

    //curl -v -X GET -H "Content-Type: application/json" localhost:8080/users/1
    @GetMapping("users/{id}")
    public ResponseEntity<UserInfo> getUser(@PathVariable("id") String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                UserInfo userInfo = new UserInfo(users.get(i).getUsername(), users.get(i).getId(), users.get(i).getAge());
                return ResponseEntity.ok(userInfo);
            }
        }
        return status(404).build();
    }

    //curl -v -X DELETE -H "Content-Type: application/json" localhost:8080/users/2
    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                return ResponseEntity.noContent().build();
            }
        }
        return status(404).build();
    }

    //curl -v -X PUT -H "Content-Type: application/json" localhost:8080/users/1/darksouls1thebest -d "Artorias"
    @PutMapping("users/{id}/{repeatPassword}")
    public ResponseEntity<Void> updateContact(@PathVariable("id") String id, @PathVariable("repeatPassword") String repeatPassword, @RequestBody String name) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                if (users.get(i).getPassword().equals(repeatPassword)) {
                    User userNew = new User(name, users.get(i).getPassword(), users.get(i).getId(), users.get(i).getAge());
                    users.remove(i);
                    users.add(i, userNew);
                    return ResponseEntity.noContent().build();
                } else {
                    return status(400).build();
                }
            }
        }
        return status(404).build();
    }
}
