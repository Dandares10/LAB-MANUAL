class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] cn = new int[26];
        for (int i = 0; i < s.length(); i++) {
            cn[s.charAt(i) - 'a']++;
            cn[t.charAt(i) - 'a']--;
        }
        for (int i : cn) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }
}
