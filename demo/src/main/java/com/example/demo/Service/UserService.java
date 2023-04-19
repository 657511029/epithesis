package com.example.demo.Service;

import com.example.demo.Dao.CourseStudentDao;
import com.example.demo.Dao.CourseTeacherDao;
import com.example.demo.Dao.ExperimentStudentDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.User;
import com.example.demo.Exception.CannotBeenFoundException;
import com.example.demo.Exception.HasBeenFoundException;

import com.example.demo.Exception.IncorrectException;
import com.example.demo.Exception.OperationFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    public UserDao userDao;

    @Autowired
    public CourseTeacherDao courseTeacherDao;

    @Autowired
    public CourseStudentDao courseStudentDao;

    @Autowired
    public ExperimentStudentDao experimentStudentDao;
    public User register(Map<String,Object> map) {
        //获取信息
//        String phoneNumber = (String)map.get("phoneNumber");
        String username = (String)map.get("username");
        String password = (String) map.get("password");
        String email = (String) map.get("email");
        String registerTime = (String)map.get("registerTime");
        String authority = (String)map.get("authority");
        User userEntity = userDao.findByEmail(email);
        User userEntity1 = userDao.findByUsername(username);

        if (userEntity != null) {
            throw new HasBeenFoundException("email: " + email);
        }
        if(userEntity1 != null){
            throw new HasBeenFoundException("username: " + username);
        }
        User newUser = new User();
//        newUser.setPhoneNumber(phoneNumber);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setAuthority(authority);
        newUser.setRegisterTime(registerTime);
        newUser.setAvatarUrl("static/userPic/userPic.jpg");
        userDao.save(newUser);
        return newUser;
    }
    public User login(Map<String,Object> map){
        String username = (String)map.get("username");
        String password = (String) map.get("password");
        System.out.println(username);
        System.out.println(password);
        User userEntity = userDao.findByUsername(username);
        if (userEntity == null) {
            throw new CannotBeenFoundException("username: " + username);
        }
        System.out.println(userEntity.getPassword());
        if(!password.equals(userEntity.getPassword())){
            throw new IncorrectException("password");
        }
        return userEntity;
    }

    public User modifyUserInfo(Map<String,Object> map){
        Long id =  ((Integer)map.get("id")).longValue();
        String username = (String)map.get("username");
        String email = (String) map.get("email");
        String phoneNumber = (String)map.get("phoneNumber");
        String career = (String)map.get("career");
        String age = (String)map.get("age");
        System.out.println(map.get("sex"));
        String sex = (String)map.get("sex");
        String address = (String)map.get("address");
        String interest = (String)map.get("interest");
        List<User> userEntity = userDao.findAllByEmail(email);
        List<User> userEntity1 = userDao.findAllByUsername(username);
        if (userEntity.size() > 1) {
            throw new HasBeenFoundException("email: " + email);
        }
        if(userEntity1.size() > 1){
            throw new HasBeenFoundException("username: " + username);
        }

        Optional<User> userOptional = userDao.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setEmail(email);
            user.setUsername(username);
            user.setPhoneNumber(phoneNumber);
            user.setCareer(career);
            user.setAge(age);
            user.setSex(sex);
            user.setAddress(address);
            user.setInterest(interest);
            userDao.save(user);
            return user;
        }
        else {
            throw new CannotBeenFoundException("user");
        }
    }
    public User lookUserInfo(Map<String,Object> map){
        Long id = Long.parseLong((String) map.get("id"));
        Optional<User> userOptional = userDao.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return user;
        }
        else {
            throw new CannotBeenFoundException("user");
        }
    }

    public List<User> lookUserListByAuthority(Map<String,Object> map){
        String authority = (String) map.get("authority");
        List<User> users = userDao.findAllByAuthority(authority);
        return users;
    }
    public User modifyUserAvatar(MultipartFile file,String idStr) throws Exception {

        Long id = Long.parseLong(idStr);
        String ware_name = file.getOriginalFilename();
        String filename = UUID.randomUUID() + ware_name;
        String address = "D:/Epithesis/epithesis/vue101/static/userPic/";
        String address1 = "static/userPic/";
        File filex = new File(address);
        if (!(filex.exists() && filex.isDirectory())) {//若上传目录不存在，则创建目录
            filex.mkdirs();
        }
        String message = "";
        InputStream is = null;
        FileOutputStream fos = null;
        byte[] buffer = new byte[100 * 1024];//根据上传文件大小设定
        try {
            is = file.getInputStream();// 获得上传文件流
            //创建文件输出流  使用FileOutputStream打开服务器端的上传文件
            fos = new FileOutputStream(address + filename);
            int len = 0;
            //开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);//写入到指定的目录当中
            }
            fos.flush();
            is.close();
            fos.close();
            message = "上传成功！" + "<br>";
            message += "上传内容：" + filename + "<br>";
            System.out.println("上传内容：" + filename + "<br>");
        } catch (IOException e) {
            message = "文件上传失败！";
            throw new OperationFailException(message);
        }
        Optional<User> userOptional = userDao.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setAvatarUrl(address1 +filename);
            userDao.save(user);
            return user;
        }
        else {
            throw new CannotBeenFoundException("user");
        }
    }

    public User modifyPassword(Map<String,Object> map){
        Long id = Long.parseLong((String) map.get("userId"));
        String oldPassword = (String) map.get("oldPassword");
        String newPassword = (String) map.get("newPassword");
        Optional<User> userOptional = userDao.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(oldPassword.equals(user.getPassword())){
                user.setPassword(newPassword);
                userDao.save(user);
                return user;
            }
            else {
                throw new IncorrectException("oldPassword");
            }
        }
        else {
            throw new CannotBeenFoundException("user");
        }
    }
    public User deleteUser(Map<String ,Object> map){
        Long id = Long.parseLong((String) map.get("userId"));
        Optional<User> userOptional = userDao.findById(id);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            userDao.delete(user);
            courseTeacherDao.deleteAllByUserId((String) map.get("userId"));
            courseStudentDao.deleteAllByUserId((String) map.get("userId"));
            experimentStudentDao.deleteAllByUserId((String) map.get("userId"));
            return user;
        } else {
            throw new CannotBeenFoundException("user");
        }
    }
}
