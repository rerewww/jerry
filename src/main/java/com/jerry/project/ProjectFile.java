package com.jerry.project;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by son on 2019-02-27.
 */
public class ProjectFile {
    @Getter
    public String filePath;
    @Getter
    public  String fileName;
    @Getter @Setter
    public String contents;

    public ProjectFile(final String fileName, final String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
