package com.angrybirds;
public class HelperFunc {
    public static int levelSelected (int SelectedLevel){
        if(SelectedLevel == 1){
            return 1;
        }
        else if(SelectedLevel == 2){
            return 2;
        }
        else if(SelectedLevel == 3){
            return 3;
        }
        else{
            return 0;
        }
    }

}
