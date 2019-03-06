package com.jerry.handler;

import com.jerry.config.ServerConfig;
import com.jerry.project.ProjectFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by son on 2019-02-27.
 */
@Slf4j
@Service
public class ProjectFileArgumentResolver implements HandlerMethodArgumentResolver {
    private boolean isFind = false;
    private File srcFile = null;

    //TODO properties파일로 빼자
    final String projectDirPath = "D:/backup/weboffice/src";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return ProjectFile.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, @Nullable WebDataBinderFactory webDataBinderFactory) throws Exception {
        final HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        final String fileName = request.getParameter("fileName");

        srcFile = null;
        if (StringUtils.isEmpty(fileName)) {
            log.warn("fileName is empty.");
            return null;
        }

        File projectDir = new File(projectDirPath);

        File[] files = projectDir.listFiles();
        if (files == null || files.length == 0) {
            log.warn("project dir is empty.");
            return null;
        }

        File srcFile = this.getJavaFileFromFileName(files, fileName);
        if (srcFile == null || !srcFile.exists()) {
            return null;
        }
        return new ProjectFile(srcFile.getName(), srcFile.getAbsolutePath());
    }

    private File getJavaFileFromFileName(final File[] files, final String fileName) {
        for (File file : files) {
            if (isFind) {
                break;
            }
            if (file.isDirectory()) {
                this.getJavaFileFromFileName(file.listFiles(), fileName);
            }
            if (file.isFile() && file.getName().equals(fileName + ".java")) {
                srcFile = file;
                isFind = true;
                break;
            }
        }
        isFind = false;
        return srcFile;
    }
}
