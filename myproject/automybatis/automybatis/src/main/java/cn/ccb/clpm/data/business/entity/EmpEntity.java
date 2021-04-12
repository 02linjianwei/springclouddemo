package cn.ccb.clpm.data.business.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author AutoGen
 * @since 2021-02-27
 */
@TableName("EMP")
public class EmpEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "EMPNO", type = IdType.INPUT)
    private Integer empno;

    @TableField("ENAME")
    private String ename;

    @TableField("JOB")
    private String job;

    @TableField("MGR")
    private Integer mgr;

    @TableField("HIREDATE")
    private LocalDateTime hiredate;

    @TableField("SAL")
    private BigDecimal sal;

    @TableField("COMM")
    private BigDecimal comm;

    @TableField("DEPTNO")
    private Integer deptno;


    public Integer getEmpno() {
        return empno;
    }

    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getMgr() {
        return mgr;
    }

    public void setMgr(Integer mgr) {
        this.mgr = mgr;
    }

    public LocalDateTime getHiredate() {
        return hiredate;
    }

    public void setHiredate(LocalDateTime hiredate) {
        this.hiredate = hiredate;
    }

    public BigDecimal getSal() {
        return sal;
    }

    public void setSal(BigDecimal sal) {
        this.sal = sal;
    }

    public BigDecimal getComm() {
        return comm;
    }

    public void setComm(BigDecimal comm) {
        this.comm = comm;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    @Override
    public String toString() {
        return "EmpEntity{" +
        "empno=" + empno +
        ", ename=" + ename +
        ", job=" + job +
        ", mgr=" + mgr +
        ", hiredate=" + hiredate +
        ", sal=" + sal +
        ", comm=" + comm +
        ", deptno=" + deptno +
        "}";
    }
}
