package com.gy.com.model;


import com.intellij.lang.Language;

public class FileInfo {
    /**
     * 类名
     */
    private String name;
    /**
     * 类名
     */
    private String className;

    private String path;

    private Long size;

    private Long lineCount;

    private String language;

    private String codeText;

    private Integer utCaseCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getLineCount() {
        return lineCount;
    }

    public void setLineCount(Long lineCount) {
        this.lineCount = lineCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCodeText() {
        return codeText;
    }

    public void setCodeText(String codeText) {
        this.codeText = codeText;
    }


    public Integer getUtCaseCount() {
        return utCaseCount;
    }

    public void setUtCaseCount(Integer utCaseCount) {
        this.utCaseCount = utCaseCount;
    }
}
