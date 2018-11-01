package com.ipaylinks.poss.integration.verifycenter.dto;

import com.ipaylinks.common.rpc.BaseRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件请求实体
 *
 * @author chaoyue
 * @date 20180620
 */
public class FileUploadRequest extends BaseRequest {

    private String originFileName;

    private String savePath;

    private String signedUrl;

    private String uploadType;

    private String batchNo;

    private MultipartFile file;

    private String suffix;

    private String orgCode;

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getSignedUrl() {
        return signedUrl;
    }

    public void setSignedUrl(String signedUrl) {
        this.signedUrl = signedUrl;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("originFileName", originFileName)
                .append("savePath", savePath)
                .append("signedUrl", signedUrl)
                .append("uploadType", uploadType)
                .append("batchNo", batchNo)
                .append("file", file)
                .append("suffix", suffix)
                .append("orgCode", orgCode)
                .toString();
    }
}
