package cn.xuzilin.common.service;

import cn.xuzilin.common.dao.ApplicationEntityMapper;
import cn.xuzilin.common.dao.StudentEntityMapper;
import cn.xuzilin.common.po.ApplicationEntity;
import cn.xuzilin.common.po.StudentEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    private ApplicationService ApplicationService;


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
        StudentEntity studentEntity = mapper.StudentInfoById(id);
        mapper.ChangeStage(studentEntity.getStudent_id(),stage,intention);
        mapper.ChangeStage2(studentEntity.getStudent_id(),stage,intention);
    }

    public List ReturnByCampus(String campus){
        List<String> list = mapper.SelectByCampus(campus);
        return list;
    }

    public String ReturnId13(String id,String intention){
        String student_id = mapper.SelectBy13(id,intention);
        return student_id;
    }

    public String ReturnId14(String id,String stage){
        String student_id = mapper.SelectBy14(id,stage);
        return student_id;
    }

    public String ReturnId124(String id,String group,String stage){
        String student_id = mapper.SelectBy124(id,group,stage);
        return student_id;
    }

    public String ReturnId12(String id,String group){
        String student_id = mapper.SelectBy12(id,group);
        return student_id;
    }

    public List<String> ReturnId34(String intention,String stage){
        List<String> list = mapper.SelectBy34(intention,stage);
        return list;
    }

    public List<String> ReturnId3(String intention){
        List<String> list = mapper.SelectBy3(intention);
        return list;
    }

    public List<String> ReturnId24(String group,String stage){
        List<String> list = mapper.SelectBy24(group,stage);
        return list;
    }

    public List<String> ReturnId2(String group){
        List<String> list = mapper.SelectBy2(group);
        return list;
    }

    public List<String> ReturnId4(String stage){
        List<String> list = mapper.SelectBy4(stage);
        return list;
    }

    public String ReturnId134(String id,String intention,String stage){
        String student_id = mapper.SelectBy134(id,intention,stage);
        return student_id;
    }

    public List<String> SelectAll(){
        List<String> student_ids = mapper.SelectAll();
        return student_ids;
    }

    public String SelectByNone(String id){
        String student_id = mapper.SelectByNone(id);
        return student_id;
    }

    public JSONArray ReturnJSONArray(List<String> student_ids){
        JSONArray jsonArray = new JSONArray();
        for (String student_id : student_ids) {
            StudentEntity studentEntity = ApplicationService.StudentInfo(student_id);
            ApplicationEntity applicationEntity = ApplicationService.ApplicationInfo(student_id);
            if (studentEntity!=null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", studentEntity.getId());
                jsonObject.put("name", studentEntity.getStudent_name());
                jsonObject.put("sex", studentEntity.getSex());
                jsonObject.put("stu_no", studentEntity.getStudent_id());
                jsonObject.put("campus", studentEntity.getCampus());
                jsonObject.put("academy", studentEntity.getAcademy());
                jsonObject.put("from", studentEntity.getFrom());
                String[] intentions = new String[2];
                intentions[0] = applicationEntity.getIntention();
                intentions[1] = applicationEntity.getIntention2();
                jsonObject.put("department", intentions);
                jsonObject.put("tel", studentEntity.getPhone_number());
                jsonObject.put("qq", studentEntity.getQq());
                String[] stages = new String[2];
                stages[0] = applicationEntity.getStage();
                stages[1] = applicationEntity.getStage2();
                jsonObject.put("stage", stages);
                jsonObject.put("introduction", applicationEntity.getIntroduction());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
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
        StudentEntity studentEntity = mapper2.selectBySno(student_id);
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
    public ApplicationEntity getBySno(String sno){
        return mapper.selectBySid(sno);
    }
}
