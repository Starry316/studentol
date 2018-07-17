package cn.xuzilin.common.service;

import cn.xuzilin.common.dao.StudentEntityMapper;
import cn.xuzilin.common.dto.Msg;
import cn.xuzilin.common.po.StudentEntity;
import cn.xuzilin.common.utils.Spider;
import cn.xuzilin.core.shiro.token.TokenManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Starry on 2018/7/16.
 */
@Service
public class StudentService {
    @Resource
    private StudentEntityMapper studentMapper;

    public void login(StudentEntity student){
        StudentEntity found = studentMapper.selectBySid(student.getStudent_id());
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

}
