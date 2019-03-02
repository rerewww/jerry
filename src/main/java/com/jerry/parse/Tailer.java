package com.jerry.parse;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class Tailer implements Runnable {
    // source file
    @Setter
    private File srcFile;

    // tail sleep time out
    private static final long SLEEP_TIME_OUT = 2000;

    // check run tail -f
    private boolean isRun = true;

    // file position
    private long pointer = 0;

    public void stop() {
        this.isRun = false;
    }

    @Override
    public void run() {
        while(isRun) {
            if (srcFile.length() <= pointer) {
                continue;
            }

            try (RandomAccessFile reader = new RandomAccessFile(srcFile, "r")) {
                reader.seek(pointer);
                String line = reader.readLine();
                while (line != null) {
                    // TODO 변수에 담아서 getter로 값을 가져올 수 있도록 하자.
                    log.info("Chagned line: " + line);
                    line = reader.readLine();
                }

                pointer = reader.getFilePointer();
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }

            try {
                Thread.sleep(SLEEP_TIME_OUT);
            } catch (InterruptedException e) {
                log.warn(e.getMessage(), e);
            }
        }
    }
}
