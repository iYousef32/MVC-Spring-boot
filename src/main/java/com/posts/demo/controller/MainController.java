package com.posts.demo.controller;

import com.posts.demo.entity.Post;
import com.posts.demo.entity.Reply;
import com.posts.demo.entity.Role;
import com.posts.demo.entity.User;
import com.posts.demo.service.PostService;
import com.posts.demo.service.ReplyService;
import com.posts.demo.service.RoleService;
import com.posts.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {

    UserService userService;
    PostService postService;
    ReplyService replyService;

    RoleService roleService;

    @Autowired
    public MainController(UserService userService, PostService postService, ReplyService replyService,RoleService roleService){
        this.userService = userService;
        this.postService = postService;
        this.replyService = replyService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String getName(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }

    @GetMapping("/showRegisterForm")
    public String showRegisterForm(Model theModel){

        theModel.addAttribute("newUser", new User());

        return "register-form";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("newUser") User user){


        User theUser = new User(user.getUsername(),"{noop}"+user.getPassword(),1,user.getNickName(),user.getEmail());
        theUser.setImage("https://cdn.analyticsvidhya.com/wp-content/uploads/2023/04/ai-generated-gba2dce9e3_1920_xMPNobD.jpg");
        userService.save(theUser);
        Role role = new Role( "ROLE_USER",user.getUsername());
        theUser.addRole(role);
        roleService.save(role);
        userService.save(theUser);

        return "redirect:/showLoginForm";
    }

    @GetMapping("/home")
    public String showMainPage(Model theModel, HttpServletRequest request){
        User user = userService.findUserByUsername(getName(request));

        List<User> followings = new ArrayList<>();
        for(int i = 0; i<user.getFollowings().size(); i++){
            User theUser = userService.findUserAndPostsByUsername(user.getFollowings().get(i).getFollowing_id());
            followings.add(theUser);
        }
        followings.add(user);
        List<Post> posts = postService.sortPostByIdDESC(followings);

        theModel.addAttribute("currentUser", user);
        theModel.addAttribute("searchUser", new User());
        theModel.addAttribute("followings", followings);
        theModel.addAttribute("sortPost", posts);

        return "main-page";
    }

    @PostMapping("/searchForUser")
    public String searchForUser(@ModelAttribute("searchUser") User user, Model theModel,HttpServletRequest request){
        List<User> theUsers = userService.searchForUserByUsername(user.getUsername());
        User theUser = userService.findUserByUsername(getName(request));

        theModel.addAttribute("users", theUsers);
        theModel.addAttribute("searchUser", new User());
        theModel.addAttribute("currentUser", theUser);

        return "result-user";
    }
    @GetMapping("/searchForUser")
    public String searchForUser2(Model theModel,HttpServletRequest request){


        return showMainPage(theModel, request);
    }

    @GetMapping("/showLikes")
    public String showLikes(@RequestParam("postId") int theId, Model theModel, HttpServletRequest request){
        Post post = postService.findPostById(theId);
        User user = userService.findUserByUsername(getName(request));
        List<User> users = new ArrayList<>();

        for(int i = 0; i < post.likedUsers().size(); i++){
            users.add(userService.findUserByUsername(post.likedUsers().get(i)));
        }

        theModel.addAttribute("users", users);
        theModel.addAttribute("searchUser", new User());
        theModel.addAttribute("currentUser", user);

        return "result-user";
    }
    @GetMapping("/showReplyLikes")
    public String showReplyLikes(@RequestParam("replyId") int theId, Model theModel, HttpServletRequest request){
        Reply reply = replyService.findReplyById(theId);
        User user = userService.findUserByUsername(getName(request));
        List<User> users = new ArrayList<>();

        for(int i = 0; i < reply.likedUsers().size(); i++){
            users.add(userService.findUserByUsername(reply.likedUsers().get(i)));
        }

        theModel.addAttribute("users", users);
        theModel.addAttribute("searchUser", new User());
        theModel.addAttribute("currentUser", user);

        return "result-user";
    }

    @GetMapping("/showFollowings")
    public String showFollowings(@RequestParam("followings") String username, Model theModel, HttpServletRequest request){
        User user = userService.findUserByUsername(getName(request));
        User theUser = userService.findUserByUsername(username);

        List<User> users = new ArrayList<>();

       for(int i = 0; i < theUser.getFollowings().size(); i++){
          users.add(userService.findUserByUsername(theUser.getFollowings().get(i).getFollowing_id()));
        }

        theModel.addAttribute("users", users);
        theModel.addAttribute("searchUser", new User());
        theModel.addAttribute("currentUser", user);

        return "result-user";
    }

    @GetMapping("/showFollowers")
    public String showFollowers(@RequestParam("followers") String username, Model theModel, HttpServletRequest request){
        User user = userService.findUserByUsername(getName(request));
        User theUser = userService.findUserByUsername(username);

        List<User> users = new ArrayList<>();

        for(int i = 0; i < theUser.getFollowers().size(); i++){
            users.add(userService.findUserByUsername(theUser.getFollowers().get(i).getFollower_id()));
        }

        theModel.addAttribute("users", users);
        theModel.addAttribute("searchUser", new User());
        theModel.addAttribute("currentUser", user);

        return "result-user";
    }
}






















