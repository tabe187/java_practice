package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.data.repository.UserRepository;
import java.util.List;
import com.example.demo.data.entity.User;
import org.springframework.ui.Model;
import com.example.demo.form.UserForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class UserController {

    //@Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
    @Autowired
    private UserRepository userRepository;
    // getUsersメソッド
    @GetMapping("/users")
    // 引数にorg.springframework.ui.Modelを追加
    public String getUsers(Model model) {
        List<User> users = userRepository.findAll();
        // Modelにusersを追加
        model.addAttribute("users", users);
        return "users";
    }
    // getNewUserメソッド
    @GetMapping("/newuser")
    public String getNewUser(Model model) {
        // テンプレートは src/main/resources/templates/newuser.html とします。
        // Modelに空のUserFormを追加
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "newuser";
    }
    // registerUserメソッド
    @PostMapping("/newuser")
    // 引数にUserFormを追加
    public String registerUser(@Validated UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // エラーがある場合はユーザー登録画面を返す
            return "newuser";
        }
    	
        // UserFormの値をUserクラス（Entity）にセットする
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());

        // データベースに保存
        userRepository.save(user);

        return "redirect:/users";
    }
    
    // deleteUserメソッドを追加
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        // 指定したIDのユーザーを削除
        userRepository.deleteById(id);

        return "redirect:/users";
    }
    
}
