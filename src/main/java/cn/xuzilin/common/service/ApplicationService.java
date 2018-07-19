package cn.xuzilin.common.service;

import cn.xuzilin.common.dao.ApplicationEntityMapper;
import cn.xuzilin.common.dao.StudentEntityMapper;
import cn.xuzilin.common.po.ApplicationEntity;
import cn.xuzilin.common.po.StudentEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService {
    @Resource
    private ApplicationEntityMapper mapper;
    @Resource
    private StudentEntityMapper mapper2;

    @Resource
    private TaskService taskService;

    public void SignUp(ApplicationEntity application){

        ApplicationEntity applicationEntity = mapper.SelectId(application.getStudent_id());

        if (applicationEntity==null){
            mapper.insert(application);
        }
        else {
            mapper.UpdateId(application.getStudent_id(),application.getIntention(),application.getIntention2(),application.getIntroduction());
        }
    }

    public ApplicationEntity ReturnData(StudentEntity student){
        ApplicationEntity applicationEntity = mapper.SelectById(student.getStudent_id());
        return applicationEntity;

    }

    public void ChangeStage(String id,String stage,String intention){
        if (stage.equals("2")){//过面试

        }
        mapper.ChangeStage(id,stage,intention);
        mapper.ChangeStage2(id,stage,intention);
    }

    public List ReturnByCampus(String campus){
        List<String> list = mapper.SelectByCampus(campus);
        return list;
    }

    public String ReturnId(String id,String intention,String stage){
        String student_id = mapper.Select(id,intention,stage);
        return student_id;
    }

    public StudentEntity StudentInfo(String studen_id){
        StudentEntity studentEntity = mapper.StudentInfo(studen_id);
        return studentEntity;
    }

    public ApplicationEntity ApplicationInfo(String studen_id){
        ApplicationEntity applicationEntity = mapper.ApplicationInfo(studen_id);
        return applicationEntity;
    }

    public int IsStudentExist(StudentEntity student,String student_id){
        StudentEntity studentEntity = mapper2.selectBySid(student_id);
        if (studentEntity!=null)
            return 0;
        else {
            mapper2.insert(student);
            return 1;
        }
    }

    public int IsSignUp(ApplicationEntity application,String student_id){
        ApplicationEntity applicationEntity = mapper.ApplicationInfo(student_id);
        if (applicationEntity!=null)
            return 0;
        else {
            mapper.insert(application);
            return 1;
        }
    }
}
