package com.example.demo.Service;

import com.example.demo.Dao.*;
import com.example.demo.Entity.CourseInfo;

import com.example.demo.Entity.CourseStudent;
import com.example.demo.Entity.CourseTeacher;
import com.example.demo.Exception.CannotBeenFoundException;
import com.example.demo.Exception.HasBeenFoundException;
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
public class CourseService {
    @Autowired
    public UserDao userDao;
    @Autowired
    public CourseInfoDao courseInfoDao;

    @Autowired
    public CourseTeacherDao courseTeacherDao;

    @Autowired
    public CourseStudentDao courseStudentDao;

    @Autowired
    public ExperimentStudentDao experimentStudentDao;

    @Autowired
    public ExperimentDao experimentDao;
    public CourseInfo lookCourseInfo(Map<String,Object> map){
        Long courseId = Long.parseLong((String) map.get("courseId"));
        System.out.println(courseId);
        Optional<CourseInfo> optionalCourseInfo = courseInfoDao.findById(courseId);
        if(optionalCourseInfo.isPresent()){
            CourseInfo courseInfo = optionalCourseInfo.get();
            return courseInfo;
        }
        else {
            throw new CannotBeenFoundException("course");
        }
    }
    public List<CourseInfo> lookCourseInfoList(Map<String,Object> map){
        String message = (String) map.get("message");
        List<CourseInfo> courseInfoList = courseInfoDao.findAllByCourseNameLike("%" + message + "%");
        return courseInfoList;
    }
    public List<CourseInfo> lookCourseInfoRecommendList(Map<String,Object> map){
        List<CourseInfo> courseInfoList = courseInfoDao.findAllRecommend();
        return courseInfoList;
    }

    public List<CourseInfo> lookCourseInfoAllList(Map<String,Object> map){
        List<CourseInfo> courseInfoList = courseInfoDao.findAll();
        return courseInfoList;
    }
    public List<CourseInfo> lookCourseInfoChooseList(Map<String,Object> map){
        String chooseName = (String)map.get("chooseName");
        List<CourseInfo> courseInfoList = courseInfoDao.findAllByChooseName(chooseName);
        return courseInfoList;
    }

    public CourseInfo addCourse(Map<String,Object> map){
        String courseName = (String) map.get("courseName");
        String courseInstitution = (String)map.get("courseInstitution");
        String courseTime = (String)map.get("courseTime");
        String chooseName = (String)map.get("chooseName");
        String introduction = (String)map.get("introduction");
        String userId = (String)map.get("userId");
        CourseInfo courseInfoEntity = courseInfoDao.findByCourseName(courseName);
        if(courseInfoEntity != null){
            throw new HasBeenFoundException("course");
        }
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setCourseInstitution(courseInstitution);
        courseInfo.setCourseName(courseName);
        courseInfo.setCourseTime(courseTime);
        courseInfo.setChooseName(chooseName);
        courseInfo.setIntroduction(introduction);
        courseInfo.setExperimentNumber(0);
        courseInfo.setStudentNumber(0);
        courseInfo.setDisplayUrl("static/displayPic/1.jpg");
        courseInfo.setBackgroundUrl("static/backgroundPic/1.png");
        CourseInfo courseInfo1 = courseInfoDao.save(courseInfo);
        Long id = courseInfo1.getId();
        CourseTeacher courseTeacher = new CourseTeacher();
        courseTeacher.setCourseId(String.valueOf(id));
        courseTeacher.setUserId(userId);
        courseTeacherDao.save(courseTeacher);
        return courseInfo1;
    }

    public CourseInfo modifyDisplayUrl(MultipartFile display,String courseId){
        Optional<CourseInfo> courseInfoOptional = courseInfoDao.findById(Long.parseLong(courseId));
        if(courseInfoOptional.isPresent()){
            CourseInfo courseInfo = courseInfoOptional.get();
            String displayUrl = load("D:/Epithesis/epithesis/vue101/static/displayPic/","static/displayPic/",display);
            courseInfo.setDisplayUrl(displayUrl);
            courseInfoDao.save(courseInfo);
            return courseInfo;
        }
        else {
            throw new CannotBeenFoundException("course");
        }
    }
    public CourseInfo modifyBackgroundUrl(MultipartFile background,String courseId){
        Optional<CourseInfo> courseInfoOptional = courseInfoDao.findById(Long.parseLong(courseId));
        if(courseInfoOptional.isPresent()){
            CourseInfo courseInfo = courseInfoOptional.get();
            String backgroundUrl = load("D:/Epithesis/epithesis/vue101/static/backgroundPic/","static/backgroundPic/",background);
            courseInfo.setBackgroundUrl(backgroundUrl);
            courseInfoDao.save(courseInfo);
            return courseInfo;
        }
        else {
            throw new CannotBeenFoundException("course");
        }
    }
    public CourseInfo deleteCourse(Map<String,Object> map){
        String courseId = (String) map.get("courseId");
        Optional<CourseInfo> courseInfoOptional = courseInfoDao.findById(Long.parseLong(courseId));
        if(courseInfoOptional.isPresent()){
            CourseInfo courseInfo = courseInfoOptional.get();
            courseInfoDao.delete(courseInfo);
           List<CourseTeacher> courseTeacherList = courseTeacherDao.findAllByCourseId(courseId);
           for(int i = 0;i < courseTeacherList.size();i++){
               courseTeacherDao.delete(courseTeacherList.get(i));
           }
           List<CourseStudent> courseStudentList = courseStudentDao.findAllByCourseId(courseId);
           for(int i = 0;i < courseStudentList.size();i++){
               courseStudentDao.delete(courseStudentList.get(i));
           }
           experimentDao.deleteAllByCourseId(courseId);
           experimentStudentDao.deleteAllByCourseId(courseId);
            return courseInfo;
        }
        else {
            throw new CannotBeenFoundException("course");
        }
    }
    public CourseInfo modifyCourseInfo(Map<String,Object> map){
        String courseName = (String) map.get("courseName");
        String courseInstitution = (String)map.get("courseInstitution");
        String chooseName = (String)map.get("chooseName");
        String introduction = (String)map.get("introduction");
        String courseId = (String)map.get("courseId");
        Optional<CourseInfo> courseInfoOptional = courseInfoDao.findById(Long.parseLong(courseId));
        if(courseInfoOptional.isPresent()){
            CourseInfo courseInfo = courseInfoOptional.get();
            courseInfo.setCourseInstitution(courseInstitution);
            courseInfo.setCourseName(courseName);
            courseInfo.setChooseName(chooseName);
            courseInfo.setIntroduction(introduction);
           courseInfoDao.save(courseInfo);

            return courseInfo;
        }
        else {
            throw new CannotBeenFoundException("course");
        }

    }
    public String load(String address , String address1,MultipartFile file){
        String ware_name = file.getOriginalFilename();
        String filename = UUID.randomUUID() + ware_name;
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
        return address1 + filename;
    }
}
