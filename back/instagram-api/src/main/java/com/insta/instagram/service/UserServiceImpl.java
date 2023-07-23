package com.insta.instagram.service;

import com.insta.instagram.dto.UserDto;
import com.insta.instagram.exceptions.UserException;
import com.insta.instagram.modal.User;
import com.insta.instagram.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;


    @Override
    public User registerUser(User user) throws UserException {

        Optional<User> isEmailExist = userRepo.findByEmail(user.getEmail());
        if(isEmailExist.isPresent()){
            throw  new UserException("Email already exist");
        }


        Optional<User> isUsernameExist = userRepo.findByUsername(user.getUsername());
        if(isUsernameExist.isPresent()){
            throw new UserException("Username already exist");
        }

        if(user.getEmail()==null || user.getPassword()==null ||user.getName()==null){
            throw  new UserException("All field are required");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setName(user.getName());
        newUser.setUsername(user.getUsername());


        return userRepo.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> opt = userRepo.findById(userId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("user not exist with id:" +userId);
    }

    @Override
    public User findUserByProfile(String token) throws UserException {
        return null;
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> opt  = userRepo.findByUsername(username);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("User not exist with username:" + username );
    }

    @Override
    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {

        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();

        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUsername(reqUser.getUsername());
        follower.setUserImage(reqUser.getImage());

        UserDto following = new UserDto();
        following.setEmail(follower.getEmail());
        following.setId(follower.getId());
        following.setName(follower.getName());
        following.setUserImage(follower.getUserImage());
        following.setUsername(follower.getUsername());

        reqUser.getFollowing().add(following);
        followUser.getFollower().add(follower);

        userRepo.save(followUser);
        userRepo.save(reqUser);
        return "you are following" + followUser.getUsername();
    }

    @Override
    public String unFollowUser(Integer reqUserId, Integer followUserId) throws UserException {

        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();

        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUsername(reqUser.getUsername());
        follower.setUserImage(reqUser.getImage());

        UserDto following = new UserDto();
        following.setEmail(follower.getEmail());
        following.setId(follower.getId());
        following.setName(follower.getName());
        following.setUserImage(follower.getUserImage());
        following.setUsername(follower.getUsername());

        reqUser.getFollowing().remove(following);
        followUser.getFollower().remove(follower);

        userRepo.save(followUser);
        userRepo.save(reqUser);
        return "you are following" + followUser.getUsername();
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) throws UserException {
        List<User> users = userRepo.findAllUsersByUserIds(userIds);
        return users;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        List<User> users = userRepo.findByQuery(query);
        if(users.size()==0){
            throw new UserException("User not found");
        }
        return users;
    }

    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {
        if(updatedUser.getEmail() !=null){
            existingUser.setEmail(updatedUser.getEmail());
        }
        if(updatedUser.getBio() !=null){
            existingUser.setBio(updatedUser.getBio());
        }
        if(updatedUser.getName() !=null){
            existingUser.setName(updatedUser.getName());
        }
        if(updatedUser.getUsername() !=null){
            existingUser.setUsername(updatedUser.getUsername());
        }
        if(updatedUser.getMobile() !=null){
            existingUser.setMobile(updatedUser.getMobile());
        }
        if(updatedUser.getGender() != null){

            existingUser.setGender(updatedUser.getGender());
        }
        if(updatedUser.getWebsite() !=null){
            existingUser.setWebsite(updatedUser.getWebsite());
        }
        if(updatedUser.getImage() !=null){
            existingUser.setImage(updatedUser.getImage());
        }
        if(updatedUser.getId().equals(existingUser.getId())){
            userRepo.save(existingUser);
        }

        throw  new UserException("you cannot update this user");
    }
}
