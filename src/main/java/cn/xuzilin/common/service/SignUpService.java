package cn.xuzilin.common.service;

import cn.xuzilin.common.dao.ApplicationEntityMapper;
import cn.xuzilin.common.po.ApplicationEntity;
import cn.xuzilin.common.po.StudentEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SignUpService {
    @Resource
    private ApplicationEntityMapper mapper;

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
}
