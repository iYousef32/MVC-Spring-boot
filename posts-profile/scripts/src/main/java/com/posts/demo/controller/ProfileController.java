package com.posts.demo.controller;

import com.posts.demo.entity.*;
import com.posts.demo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    UserService userService;
    PostService postService;
    ReplyService replyService;
    FollowingService followingService;
    FollowerService followerService;
    LikePostService likePostService;
    LikeReplyService likeReplyService;


    @Autowired
    public ProfileController(UserService userService, PostService postService, ReplyService replyService, FollowingService followingService,FollowerService followerService, LikePostService likePostService,LikeReplyService likeReplyService){
        this.userService = userService;
        this.postService = postService;
        this.replyService = replyService;
        this.followingService = followingService;
        this.followerService = followerService;
        this.likePostService = likePostService;
        this.likeReplyService = likeReplyService;
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String getName(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }

    @GetMapping("/home")
    public String showProfile(Model theModel, HttpServletRequest request){
        User user = userService.findUserByUsername(getName(request));
        List<User> users = new ArrayList<>();
        users.add(user);
        List<Post> posts = postService.sortPostByIdDESC(users);


        theModel.addAttribute("posts", posts);
        theModel.addAttribute("user", user);
        theModel.addAttribute("currentUser", user);
        theModel.addAttribute("searchUser", new User());

        return "profile";
    }

    @GetMapping("/home2")
    public String showProfile2(@RequestParam("username") String username, Model theModel, HttpServletRequest request){
        User user = userService.findUserByUsername(username);
        User currentUser = userService.findUserByUsername(getName(request));
        List<User> users = new ArrayList<>();
        users.add(user);
        List<Post> posts = postService.sortPostByIdDESC(users);


        theModel.addAttribute("posts", posts);
        theModel.addAttribute("user", user);
        theModel.addAttribute("currentUser", currentUser);
        theModel.addAttribute("searchUser", new User());

        return "profile";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(@RequestParam("username") String username, Model theModel){
        User user = userService.findUserByUsername(username);

        theModel.addAttribute("newPost", new Post());
        theModel.addAttribute("user", user);
        theModel.addAttribute("searchUser", new User());

        return "save-form";
    }

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("newPost") Post post, @ModelAttribute("user") User user){

        if(post.getImage().equals("")){
            post.setImage(null);
        }

        User user1 = userService.findUserByUsername(user.getUsername());
        post.setAvatar(user1.getImage());
        post.setNickName(user1.getNickName());
        user1.addPost(post);
        postService.save(post);
        userService.save(user1);

        return "redirect:/profile/home";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("username") String username, @RequestParam("post_id") int theId, Model theModel){

        User user = userService.findUserByUsername(username);
        Post post = postService.findPostById(theId);

        theModel.addAttribute("newPost", post);
        theModel.addAttribute("user", user);
        theModel.addAttribute("searchUser", new User());

        return "save-form";
    }


    @GetMapping("/deletePost")
    public String deletePost(@RequestParam("post_id") int theId){
        postService.delete(theId);

        return "redirect:/profile/home";
    }

    @GetMapping("/showFormForAddReply")
    public String showFormForAddReply(@RequestParam("postId") int theId, Model theModel,HttpServletRequest request){
        Post post = postService.findPostById(theId);
        User user = userService.findUserByUsername(getName(request));

        theModel.addAttribute("user", user);
        theModel.addAttribute("newReply", new Reply());
        theModel.addAttribute("post", post);
        theModel.addAttribute("searchUser", new User());

        return "save-form-reply";

    }

    @PostMapping("/saveReply")
    public String saveReply(@ModelAttribute("post") Post post, @ModelAttribute("newReply") Reply reply,@ModelAttribute("user") User theUser1){

        if(reply.getImageReply().equals("")){
            reply.setImageReply(null);
        }
        User theUser = userService.findUserByUsername(theUser1.getUsername());
        Post thePost = postService.findPostById(post.getId());
        List<User> users = userService.findAllUsers();
        User user = userService.findUserByUsername(thePost.getUsername());


//            for(int j = 0; j < users.size();j++) {
//                for (int i = 0; i < users.get(j).getPosts().size(); i++) {
//                    if (users.get(j).getPosts().get(i).getId() == thePost.getId()) {
//                        user = users.get(j);
//                        break;
//                    }
//                }
//            }
        reply.setAvatar(theUser.getImage());
        reply.setNickName(theUser.getNickName());
        thePost.addReply(reply);
        replyService.save(reply);
        theUser.addReply(reply);
        userService.save(theUser);
        postService.save(thePost);

        //String referer = request.getHeader("Referer");
        return "redirect:/profile/postDetails?postId="+ thePost.getId() +"&username=" + user.getUsername();
    }

    @GetMapping("/postDetails")
    public String postDetails(@RequestParam("postId") int theId,@RequestParam("username") String username, Model theModel,HttpServletRequest request){
        Post post = postService.findPostById(theId);
        User user = userService.findUserByUsername(username);
        User theUser = userService.findUserByUsername(getName(request));

        theModel.addAttribute("post", post);
        theModel.addAttribute("user", user);
        theModel.addAttribute("currentUser", theUser);
        theModel.addAttribute("searchUser", new User());

        return "post-details";
    }

    @GetMapping("/addFollowing")
    public String addFollowing(@RequestParam("currUser") String user1, @RequestParam("folUser") String user2, HttpServletRequest request){

        User currentUser = userService.findUserByUsername(user1);
        User followUser = userService.findUserByUsername(user2);

        Following following = new Following(followUser.getUsername(),currentUser.getUsername());
        Follower follower = new Follower(currentUser.getUsername(), followUser.getUsername());

        currentUser.addFollowing(following);
        followUser.addFollower(follower);

        followingService.save(following);
        followerService.save(follower);
        userService.save(currentUser);
        userService.save(followUser);


        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/removeFollowing")
    public String removeFollowing(@RequestParam("currUser") String user1, @RequestParam("folUser") String user2, HttpServletRequest request){

        User currentUser = userService.findUserByUsername(user1);
        User followUser = userService.findUserByUsername(user2);

        Following following = new Following(followUser.getUsername(),currentUser.getUsername());
        Follower follower = new Follower(currentUser.getUsername(), followUser.getUsername());


        followingService.remove(following);
        followerService.remove(follower);

        userService.save(currentUser);
        userService.save(followUser);



        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("addLike")
    public String addLike(@RequestParam("postId") int postId, @RequestParam("username") String username, HttpServletRequest request){

        Post post = postService.findPostById(postId);

        LikePost likePost = new LikePost(username,postId,true);
        post.addLike(likePost);

        likePostService.save(likePost);
        postService.save(post);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("removeLike")
    public String removeLike(@RequestParam("postId") int postId, @RequestParam("username") String username, HttpServletRequest request){

        Post post = postService.findPostById(postId);
        int likeId = post.returnLikeId(username,postId);


        likePostService.deleteById(likeId);


        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("addReplyLike")
    public String addReplyLike(@RequestParam("replyId") int replyId, @RequestParam("username") String username, HttpServletRequest request){

        Reply reply = replyService.findReplyById(replyId);

        LikeReply likeReply = new LikeReply(username,replyId,true);
        reply.addLike(likeReply);

        likeReplyService.save(likeReply);
        replyService.save(reply);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("removeReplyLike")
    public String removeReplyLike(@RequestParam("replyId") int replyId, @RequestParam("username") String username, HttpServletRequest request){

        LikeReply likeReply = likeReplyService.findLikeReply(username,replyId);

        likeReplyService.delete(likeReply);


        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


    @GetMapping("showEditForm")
    public String showEditForm(@RequestParam("username") String username, Model theModel,HttpServletRequest request){
        User user = userService.findUserByUsername(username);
        User theUser = userService.findUserByUsername(getName(request));


        theModel.addAttribute("currentUser", theUser);
        theModel.addAttribute("searchUser", new User());
        theModel.addAttribute("user", user);

        return "edit-account";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user){
        User theUser = userService.findUserByUsername(user.getUsername());
        if(!user.getImage().equals("")) {
            theUser.setImage(user.getImage());
        }
        theUser.setAbout(user.getAbout());
        theUser.setNickName(user.getNickName());
        theUser.setEmail(user.getEmail());


        userService.save(theUser);

        return "redirect:/profile/home";
    }


}






















