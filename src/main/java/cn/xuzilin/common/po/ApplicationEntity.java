package cn.xuzilin.common.po;

import java.util.Date;

public class ApplicationEntity {
    private Integer id;

    private Integer intention;

    private Date sign_time;

    private Integer intention2;

    private Integer sid;

    private Integer stage;

    private Integer stage2;

    private String introduction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIntention() {
        return intention;
    }

    public void setIntention(Integer intention) {
        this.intention = intention;
    }

    public Date getSign_time() {
        return sign_time;
    }

    public void setSign_time(Date sign_time) {
        this.sign_time = sign_time;
    }

    public Integer getIntention2() {
        return intention2;
    }

    public void setIntention2(Integer intention2) {
        this.intention2 = intention2;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getStage2() {
        return stage2;
    }

    public void setStage2(Integer stage2) {
        this.stage2 = stage2;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }
}