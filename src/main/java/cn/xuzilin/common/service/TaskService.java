package cn.xuzilin.common.service;

import cn.xuzilin.common.dao.ApplicationEntityMapper;
import cn.xuzilin.common.dao.TaskScoreEntityMapper;
import cn.xuzilin.common.po.ApplicationEntity;
import cn.xuzilin.common.po.StudentEntity;
import cn.xuzilin.common.po.TaskScoreEntity;
import cn.xuzilin.core.shiro.token.TokenManager;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Starry on 2018/7/18.
 */
@Service
public class TaskService {
    @Resource
    TaskScoreEntityMapper taskScoreMapper;

    @Resource
    ApplicationEntityMapper applicationMapper;

    public ApplicationEntity getAppsBySid(String sid){
        return applicationMapper.selectBySid(sid);
    }
    public JSONArray getWork(){
        StudentEntity student = TokenManager.getStudentToken();
        JSONArray jsonArray = new JSONArray();
        if (student == null)return null;
        List<TaskScoreEntity> taskScoList = taskScoreMapper.getBySid(student.getId());
        if (taskScoList.size()==0)
            return null;
        else if (taskScoList.size()==1){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("intention",taskScoList.get(0).getGroup());
            jsonObject.put("works",transferScoreString(taskScoList.get(0)));
            jsonArray.add(jsonObject);
            return jsonArray;
        }
        else {
            TaskScoreEntity t1 = taskScoList.get(0);
            TaskScoreEntity t2 = taskScoList.get(1);
            if ((t1.getGroup()/10) == (t2.getGroup()/10)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("intention",t1.getGroup()+","+t2.getGroup());
                jsonObject.put("works",transferScoreString(t1));
                jsonArray.add(jsonObject);
                return jsonArray;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("intention",t1.getGroup());
            jsonObject.put("works",transferScoreString(t1));
            jsonArray.add(jsonObject);
            jsonObject = new JSONObject();
            jsonObject.put("intention",t2.getGroup());
            jsonObject.put("works",transferScoreString(t2));
            jsonArray.add(jsonObject);
            return jsonArray;


        }
    }
    public String transferScoreString(TaskScoreEntity taskScore){
        String res = "";
        if (taskScore.getScore1()!=-1)
            res+=taskScore.getScore1();
        res+=",";
        if (taskScore.getScore2()!=-1)
            res+=taskScore.getScore2();
        res+=",";
        if (taskScore.getScore3()!=-1)
            res+=taskScore.getScore3();
        res+=",";
        if (taskScore.getScore4()!=-1)
            res+=taskScore.getScore4();
        return res;

    }
    public List<TaskScoreEntity> getBySidGroup(int sid,int group){
        return taskScoreMapper.getBySidAndGroup(sid,group*10,group*10+9);
    }
    public List<TaskScoreEntity> getByGroup(int group){
        return taskScoreMapper.getByGroup(group*10,group*10+9);
    }
    public void create(int sid,int department){
        TaskScoreEntity t= new TaskScoreEntity();
        t.setGroup(department);
        t.setSid(sid);
        taskScoreMapper.insertSelective(t);
    }
    public void changeTaskStage(int sid ,int department,int type){
        if (type == 2){
            TaskScoreEntity taskScoreEntity = taskScoreMapper.getBySidAndDepartment(sid,department);
            if (taskScoreEntity == null)
                create(sid,department);
        }
        //如果面试挂了，删除数据库内的task记录
        else if (type == 3){
            TaskScoreEntity taskScoreEntity = taskScoreMapper.getBySidAndDepartment(sid,department);
            if (taskScoreEntity!=null){
                taskScoreMapper.deleteById(taskScoreEntity.getId());
            }
        }
    }

    public void updateTaskScore(TaskScoreEntity taskScore){
        taskScoreMapper.updateByPrimaryKeySelective(taskScore);
    }
}
