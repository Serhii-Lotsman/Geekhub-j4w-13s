package org.geekhub.example.entity;

import java.util.Arrays;
import java.util.Objects;

public record FileContent(FileInfo fileInfo, byte[] content) {
    @Override
    public int hashCode() {
        int result = Objects.hash(fileInfo);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
