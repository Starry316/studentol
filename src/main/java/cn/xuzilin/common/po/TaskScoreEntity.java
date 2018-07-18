package cn.xuzilin.common.po;

public class TaskScoreEntity {
    private Integer id;

    private Integer local_sid;

    private Integer task_id;

    private Byte status;

    private Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocal_sid() {
        return local_sid;
    }

    public void setLocal_sid(Integer local_sid) {
        this.local_sid = local_sid;
    }

    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}