package cn.xuzilin.common.service;

import cn.xuzilin.common.dao.StudentEntityMapper;
import cn.xuzilin.common.dto.Msg;
import cn.xuzilin.common.po.StudentEntity;
import cn.xuzilin.common.utils.Spider;
import cn.xuzilin.core.shiro.token.TokenManager;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Starry on 2018/7/16.
 */
@Service
public class StudentService {
    @Resource
    private StudentEntityMapper studentMapper;

    private final String[] prop = {"sex","campus","academy","from","tel","qq"};
    /**
     * 登录
     * @param student
     */
    public void login(StudentEntity student){
        StudentEntity found = studentMapper.selectBySno(student.getStudent_id());
        if (found == null)
            studentMapper.insert(student);
        else
            student = found;

        TokenManager.studentLogin(student);
    }

    /**
     * 登入教务系统
     * @param sid
     * @param pass
     * @return
     */
    private String[]  loginEduSys(String sid , String pass){
        Spider spider = new Spider();
        String [] list;
        Msg<String[]> msg;
        try {
            msg= spider.getStudentInfo(sid,pass);
            list = msg.getObj();
        }catch (Exception e){
            return null;
        }
        return list;
    }

    /**
     * 检测账号密码是否正确
     * @param sid
     * @param pass
     * @return studentName
     */
    public String checkAccount(String sid , String pass){
        String []res = this.loginEduSys(sid,pass);
        if (res != null&&res.length >= 1){
            return res[0];
        }
        return null;
    }

    /**
     * 更新
     * @param record
     */
    public void Update(StudentEntity record){
        studentMapper.updateByPrimaryKeySelective(record);
    }

    public JSONObject toLphReqJSONData(StudentEntity student){
        JSONObject data = new JSONObject();
        data.put("id",student.getId());
        data.put("name",student.getStudent_name());
        data.put("sex",student.getSex());
        data.put("stu_no",student.getStudent_id());
        data.put("campus",student.getCampus());
        data.put("academy",student.getAcademy());
        data.put("from",student.getFrom());
        data.put("tel",student.getPhone_number());
        data.put("qq",student.getQq());
        return data;
    }

    /**
     * 通过map和id更新学生信息
     * @param map
     * @param id
     * @return
     */
    public int updateStudentByMap(Map<String,String> map,int id){
        StudentEntity student = new StudentEntity();
        student.setId(id);
        //下面是段很丑的实现
        String sex = map.get("sex");
        if (sex != null)
            student.setSex(sex);
        String campus = map.get("campus");
        if (campus != null)
            student.setCampus(campus);
        String academy = map.get("academy");
        if (academy != null)
            student.setAcademy(academy);
        String from = map.get("from");
        if (from != null)
            student.setFrom(from);
        String tel = map.get("tel");
        if (tel != null)
            student.setPhone_number(tel);
        String qq = map.get("qq");
        if (qq != null)
            student.setSex(qq);
        return studentMapper.updateByPrimaryKeySelective(student);
    }
    public StudentEntity getById(int sid){
        return studentMapper.selectByPrimaryKey(sid);
    }
    public StudentEntity getBySidCompus(int sid , int compus){
        return studentMapper.selectBySidCampus(sid,compus+"");
    }
}
