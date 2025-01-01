package tests;

import com.angrybirds.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class lessd {
    @Test
    public void structureDef() {
        wood a = new wood();
        float a_hp = a.getHp();
        Assertions.assertEquals(2, a_hp, "Wood should be initialized with correct HP.");
    }


    @Test
    public void enemyDef() {
        enemy0 d = new enemy0();

        int d_hp = d.getHp();
        double d_damage = d.getDamage();

        Assertions.assertEquals(1, d_hp, "Enemy should be initialized with correct HP.");
        Assertions.assertEquals(1, d_damage, "Enemy should be initialized with correct damage."); // Change 0 to the correct value for enemy damage
    }

    @Test
    public void birdDef() {
        Prop1 p1 = new Prop1();

        int hp = p1.getHp();
        double damage = p1.getDamage();

        Assertions.assertEquals(20, hp, "Prop1 (bird) should be initialized with correct HP.");
        Assertions.assertEquals(4, damage, "Prop1 (bird) should be initialized with correct damage.");
    }

    @Test
    public void isStruct() {
       boolean a = HelperFunc.isStructure("woodblock");
       boolean b = HelperFunc.isStructure("stoneblock");
       boolean c = HelperFunc.isStructure("rockblock");

       Assertions.assertEquals(true , a , "Structure is woodblock");
       Assertions.assertEquals(true, b, "Structure is stoneblock");
       Assertions.assertEquals(false, c, "Structure is rockblock");
    }

    @Test void isPigg(){
        boolean a = HelperFunc.isPig("pig0");
        boolean b = HelperFunc.isPig("pig1");
        boolean c = HelperFunc.isPig("pig6");

        Assertions.assertEquals(true , a , "Structure is Pig0");
        Assertions.assertEquals(true, b, "Structure is Pig1");
        Assertions.assertEquals(false, c, "Structure is not Pig");

    }


    

}
