package org.geekhub.example.entity;

import java.util.Arrays;
import java.util.Objects;

public record FileContent(
    FileInfo fileInfo,
    byte[] content
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileContent that = (FileContent) o;
        return Objects.equals(fileInfo, that.fileInfo) && Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fileInfo);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }

    @Override
    public String toString() {
        return "FileContent{" +
            "fileInfo=" + fileInfo +
            ", content=" + Arrays.toString(content) +
            '}';
    }
}
