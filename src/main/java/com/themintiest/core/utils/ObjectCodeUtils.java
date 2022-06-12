package com.themintiest.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectCodeUtils {
    public static String buildStoryTypeCode(String typeName) {
        String prepare = StringNormalizer.normalize(typeName).trim();
        return prepare.replaceAll(" ", "-");
    }

    public static String buildChapterCode(String comicCode, String chapterName) {
        String prepare = StringNormalizer.normalize(chapterName).trim();
        String code = prepare.replaceAll(" ", "-");
        return comicCode + "-" + code;
    }
}
