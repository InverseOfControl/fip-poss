package com.ipaylinks.poss.dal.domain.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ipaylinks.poss.dal.common.AttachmentType;

import javax.persistence.*;
import java.io.File;
import java.util.Date;

/**
 * 
 * 系统附件信息
 * @author zhaoyang
 * @date 2018年8月14日
 */
@Entity
@SequenceGenerator(name="seq_crm_id",sequenceName="SEQ_CRM_ID")
@Table(name = "crm_attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_crm_id")
    private Long id;

    @Column(name = "att_type", length = 20)
    @Enumerated(EnumType.STRING)
    private AttachmentType type;

    /**
     * 上传人
     */
    @ManyToOne
    @JsonIgnore
    private Member member;

    /**
     * 文件原始名称
     */
    private String originalName;

    /**
     * 文件存储的相对路径
     */
    @Column(length = 100, unique = true)
    private String filePath;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件类型(后缀)
     */
    @Column(length = 20)
    private String suffix;

    @Column(length = 64)
    private String contentType;

    /**
     * 上传时间
     */
    @Column(updatable = false)
    private Date uploadTime = new Date();

    /**
     * 非持久化字段，不需要存储到数据库,也不做json的序列化
     */
    @Transient
    @JsonIgnore
    private File file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", type=" + type +
                ", member=" + member.getRealName() +
                ", originalName='" + originalName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", suffix='" + suffix + '\'' +
                ", contentType='" + contentType + '\'' +
                ", uploadTime=" + uploadTime +
                '}';
    }
}
