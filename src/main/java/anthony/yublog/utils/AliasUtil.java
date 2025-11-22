package anthony.yublog.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.util.StringUtils;

public class AliasUtil {

    private AliasUtil() {
    }

    /**
     * 对外唯一方法：传入分类名称 + 用户填写的别名，返回最终应该保存的 alias
     * <p>
     * 规则：
     * 1. 如果 categoryAlias 有实际内容（非 null、非空、非纯空白）→ 直接使用（仅标准化）
     * 2. 否则根据 categoryName 自动生成
     * - 中文 → 拼音首字母小写（如 "测试分类" → "csfc"）
     * - 英文 → 第一个单词前3个字母小写（如 "travelNote" → "tra"）
     */
    public static String generateAlias(String categoryName, String categoryAlias) {

        // 1. 用户填了有意义的 alias → 直接标准化后返回
        if (StringUtils.hasText(categoryAlias)) {
            return normalize(categoryAlias.trim());
        }

        // 2. 用户没填 → 自动根据 name 生成
        return autoGenerate(categoryName.trim());
    }

    /**
     * 自动生成逻辑（已确保 name 不为空）
     */
    private static String autoGenerate(String name) {
        // 包含中文 → 拼音首字母
        if (containsChinese(name)) {
            return chineseToInitials(name);
        }

        // 纯英文 → 取第一个连续英文字母的前3位
        var m = java.util.regex.Pattern.compile("[a-zA-Z]+").matcher(name);
        if (m.find()) {
            String word = m.group().toLowerCase();
            return word.length() <= 3 ? word : word.substring(0, 3);
        }

        // 极端情况（理论上不会走到，因为 name 已保证不为空）
        return "cat";
    }

    /**
     * 中文转拼音首字母
     */
    private static String chineseToInitials(String chinese) {
        StringBuilder sb = new StringBuilder();
        for (char c : chinese.toCharArray()) {
            if (c >= '\u4e00' && c <= '\u9fa5') {
                String[] arr = PinyinHelper.toHanyuPinyinStringArray(c);
                if (arr != null && arr.length > 0) {
                    sb.append(Character.toLowerCase(arr[0].charAt(0)));
                }
            }
        }
        return !sb.isEmpty() ? sb.toString() : "cat";
    }

    private static boolean containsChinese(String s) {
        return s.codePoints().anyMatch(cp -> cp >= 0x4e00 && cp <= 0x9fa5);
    }

    /**
     * 标准化用户手动填写的 alias
     */
    private static String normalize(String alias) {
        String s = alias.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-+|-$+", "");

        return s.isEmpty() ? "cat" : s;
    }

    // 兼容旧调用（如果还有地方用单参数）
    public static String generateAlias(String name) {
        return generateAlias(name, null);
    }
}