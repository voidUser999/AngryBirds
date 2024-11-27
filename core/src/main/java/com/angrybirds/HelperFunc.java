package com.angrybirds;
public class HelperFunc {
    public static boolean isStructure(Object userData) {
        return "woodblock".equals(userData) || "stoneblock".equals(userData) || "glassblock".equals(userData);
    }
   public static boolean isPig(Object userData) {
        return "pig0".equals(userData) || "pig1".equals(userData) || "pig2".equals(userData);
    }

}
